package com.mamirault.findthegreenline;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.management.RuntimeErrorException;

import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.mamirault.findthegreenline.core.Direction;
import com.mamirault.findthegreenline.core.Headsign;
import com.mamirault.findthegreenline.core.Line;
import com.mamirault.findthegreenline.core.Station;
import com.mamirault.findthegreenline.core.StationRoute;
import com.mamirault.findthegreenline.core.Stop;
import com.mamirault.findthegreenline.data.StopDAO;

public class GMapScheduleManager {
  private final static String ARRIVAL = "arrival";
  private final static String DEPARTURE = "departure";
  private final static String STOP = "stop";
  private final static String TIME = "time";
  private final static String HEADSIGN = "headsign";

  private final DefaultHttpClient httpClient;
  private final StopDAO stopDAO;
  //private long departureTime = 1365499800;
  private long lastApiCallMillis;
  private final long apiCallDelayMillis = 2500; 
  
  public static long AM_500 = 1365670800;
  public static long AM_830 = 1365683100;
  public static long AM_1230 = 1365741000;
  public static long ONE_MINUTE = 60;
  public static long FIVE_MINUTES = 300;

  public GMapScheduleManager() {
    this.httpClient = new DefaultHttpClient();
    this.stopDAO = new StopDAO();
  }

  public void getData(Line line) {
    switch (line) {
    case B: {
    }
    case C: {
    }
    case D: {
    }
    case E: {
    }
    default:
      throw new IllegalStateException("Line must be either: B, C, D, or E.");
    }

  }

  @Test
  public void getELineDataTest() {
    getLineData(StationRoute.E, true, true, AM_500);
  }

  public void getLineData(List<Station> stationRoute, boolean saveDepartureInfo, boolean saveArrivalInfo, long departureTime) {
    for (int i = 0; i < stationRoute.size() - 1; i++) {
      Optional<Stop> maybeArrivalStop = getData(stationRoute.get(i), stationRoute.get(i + 1), departureTime, saveDepartureInfo, saveArrivalInfo, false);
      if(maybeArrivalStop.isPresent()){
        departureTime = maybeArrivalStop.get().getTime();
      } else {
        throw new RuntimeException("No stop present");
      }
    }
  }

  public Optional<Stop> getData(Station originStation, Station destinationStation, long departureTime, boolean saveDepartureInfo, boolean saveArrivalInfo, boolean returnDeparture) {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode actualObj = null;
    
    
    try {
      String url = String.format("http://maps.googleapis.com/maps/api/directions/json?alternatives=true&sensor=false&mode=transit&departure_time=%d&origin=%s&destination=%s", departureTime, originStation.getAddress(), destinationStation.getAddress(), "UTF-8")
          .replace(" ", "+");
      System.out.println(url);
      if(lastApiCallMillis + apiCallDelayMillis >= System.currentTimeMillis()){
        try {
          Thread.sleep((lastApiCallMillis + apiCallDelayMillis) - System.currentTimeMillis());
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

      actualObj = mapper.readTree(new URL(url));
    } catch (JsonProcessingException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      lastApiCallMillis = System.currentTimeMillis();
    }

    if (actualObj != null) {
      if (actualObj.get("status").asText().equals("OK")) {
        return getStationDataFromJSONRoute(actualObj, saveDepartureInfo, saveArrivalInfo, returnDeparture);
      } else {
        System.out.println("Exception hitting GMaps API: " + actualObj.toString());
      }
    }

    return Optional.<Stop> absent();
  }

  public Optional<Stop> getStationDataFromJSONRoute(JsonNode jNode, boolean saveDepartureInfo, boolean saveArrivalInfo, boolean returnDeparture) {
    Iterator<JsonNode> elements = jNode.findValues("routes").get(0).elements();
    List<JsonNode> routes = Lists.newArrayList(elements);
    for (JsonNode route : routes) {
      if (isLightRail(route)) {
        Optional<JsonNode> maybeTransitDetails = getTransitDetails(route);
        if (maybeTransitDetails.isPresent()) {
            Optional<Stop> maybeDepartureStop = getDepartureStop(maybeTransitDetails.get());
            Optional<Stop> maybeArrivalStop = getArrivalStop(maybeTransitDetails.get());

          saveIfPresent(maybeDepartureStop);
          saveIfPresent(maybeArrivalStop);
          
          if(returnDeparture){
            return maybeDepartureStop;
          } else {
            return maybeArrivalStop;
          }
        }
        break;
      }
    }
    
    return Optional.<Stop> absent();
  }
  
  @Test
  public void getAllTimes() {
    long time = AM_500;
    while (time <= AM_1230 + 600) {
      System.out.println(new Date(TimeUnit.SECONDS.toMillis(time)));
      Optional<Stop> maybeDepartureStop = getData(Station.PARK_ST, Station.BRIGHAM_CIRCLE, time, true, false, true);
      if (maybeDepartureStop.isPresent()) {
        time = maybeDepartureStop.get().getTime() + ONE_MINUTE;
      } else {
        time += FIVE_MINUTES;
      }
    }
  }

  private boolean saveIfPresent(Optional<Stop> maybeStop) {
    if (maybeStop.isPresent()) {
      return stopDAO.insert(maybeStop.get()) > 0;
    } else {
      return false;
    }
  }

  private Optional<Stop> getDepartureStop(JsonNode transitDetails) {
    return getStop(DEPARTURE, transitDetails);
  }

  private Optional<Stop> getArrivalStop(JsonNode transitDetails) {
    return getStop(ARRIVAL, transitDetails);
  }

  private Optional<Stop> getStop(String stopType, JsonNode transitDetails) {
    Optional<Stop> maybeStop = Optional.<Stop> absent();

    JsonNode stop = transitDetails.get(stopType + "_" + STOP);
    String name = stop.get("name").asText();

    Optional<Station> maybeStation = Station.getStationFromName(name);
    if (maybeStation.isPresent()) {
      JsonNode timeNode = transitDetails.get(stopType + "_" + TIME);

      long time = timeNode.get("value").asLong();
      Headsign headsign = Headsign.parseHeadsign(transitDetails.get(HEADSIGN).asText());

      maybeStop = Optional.fromNullable(new Stop(maybeStation.get(), headsign.getDirection(), time));
    }

    return maybeStop;
  }

  private Optional<JsonNode> getTransitDetails(JsonNode route) {
    Optional<JsonNode> transitDetails = Optional.<JsonNode> absent();
    try {
      List<JsonNode> legs = route.findValues("legs");
      JsonNode leg = legs.get(0);
      JsonNode otherLeg = leg.get(0);
      List<JsonNode> steps = otherLeg.findValues("steps");

      JsonNode step = steps.get(0).get(0);
      transitDetails = Optional.fromNullable(step.get("transit_details"));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return transitDetails;
  }

  private boolean isLightRail(JsonNode route) {
    try {
      List<JsonNode> legs = route.findValues("legs");
      JsonNode leg = legs.get(0);
      JsonNode otherLeg = leg.get(0);
      List<JsonNode> steps = otherLeg.findValues("steps");

      JsonNode step = steps.get(0).get(0);
      JsonNode html = step.get("html_instructions");
      return html.asText().contains("Light rail towards E - Heath Street");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
