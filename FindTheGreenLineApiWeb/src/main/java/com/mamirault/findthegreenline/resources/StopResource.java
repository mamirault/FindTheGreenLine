package com.mamirault.findthegreenline.resources;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.mamirault.findthegreenline.core.Direction;
import com.mamirault.findthegreenline.core.Station;
import com.mamirault.findthegreenline.core.Stop;
import com.mamirault.findthegreenline.data.StopDAO;
import com.yammer.metrics.annotation.Timed;

@Path("/stops")
@Produces(MediaType.APPLICATION_JSON)
public class StopResource {
  private final static String TIMEFRAME_DEFAULT = "3600";

  private final String template;
  private final String defaultName;
  private final AtomicLong counter;
  private final StopDAO stopDAO;

  public StopResource(String template, String defaultName) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
    this.stopDAO = new StopDAO();
  }

  @GET
  @Timed
  @Path("/all")
  public List<Stop> getAllStops(@DefaultValue("0") @QueryParam("offset") long offset, @DefaultValue("2500") @QueryParam("count") long count, @DefaultValue("%") @QueryParam("name") String name,
      @DefaultValue("%") @QueryParam("direction") String direction) {
    Preconditions.checkArgument(count >= 0, "Count cannot be negative.");
    Preconditions.checkArgument(offset >= 0, "Offset cannot be negative.");
    Preconditions.checkArgument(direction.equals("%") || direction.equalsIgnoreCase("East") || direction.equalsIgnoreCase("West"));

    Optional<Station> maybeStation = Station.getStationFromName(name + " Station");
    Preconditions.checkArgument(name.equals("%") || maybeStation.isPresent());

    return stopDAO.getAllStops(name, direction, offset, count);
  }

  @GET
  @Timed
  @Path("/timeframe")
  public List<Stop> getStopsWithinTimeframe(@DefaultValue("0") @QueryParam("offset") long offset, @DefaultValue("2500") @QueryParam("count") long count, @DefaultValue("%") @QueryParam("name") String name,
      @DefaultValue("%") @QueryParam("direction") String direction, @DefaultValue(TIMEFRAME_DEFAULT) @QueryParam("timeframe") long timeframe) {
    Preconditions.checkArgument(count >= 0, "Count cannot be negative.");
    Preconditions.checkArgument(offset >= 0, "Offset cannot be negative.");
    Preconditions.checkArgument(direction.equals("%") || direction.equalsIgnoreCase("East") || direction.equalsIgnoreCase("West"));
    Preconditions.checkArgument(timeframe >= 0, "Timeframe cannot be negative");

    Optional<Station> maybeStation = Station.getStationFromName(name + " Station");
    Preconditions.checkArgument(name.equals("%") || maybeStation.isPresent());
    return stopDAO.getStopsWithinTimeframe(name, direction, timeframe, offset, count);
  }
}