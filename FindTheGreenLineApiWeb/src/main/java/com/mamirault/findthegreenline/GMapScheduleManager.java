package com.mamirault.findthegreenline;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamirault.findthegreenline.core.Line;

public class GMapScheduleManager {
  private final DefaultHttpClient httpClient;

  public GMapScheduleManager() {
    this.httpClient = new DefaultHttpClient();
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
      getELineDataTest();
    }
    default:
      throw new IllegalStateException("Line must be either: B, C, D, or E.");
    }

  }

  @Test
  public void getELineDataTest() {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode actualObj = null;

      try {
        actualObj = mapper.readTree(new URL("http://maps.googleapis.com/maps/api/directions/json?&sensor=false&mode=transit&departure_time=1364895000&origin=lechmere%20station&destination=science%20park%20station"));
      } catch (JsonProcessingException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (MalformedURLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      
      List<JsonNode> routes = actualObj.findValues("routes");
      System.out.println(routes.get(0).findValue("start_address").asText());
  }
}
