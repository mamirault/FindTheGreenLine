package com.mamirault.findthegreenline.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.mamirault.findthegreenline.core.CheckIn;
import com.mamirault.findthegreenline.core.Direction;
import com.mamirault.findthegreenline.core.Station;
import com.mamirault.findthegreenline.data.CheckInDAO;
import com.mamirault.findthegreenline.json.JsonGeneratorWrapper;
import com.yammer.metrics.annotation.Timed;

@Path("/checkin")
@Produces(MediaType.APPLICATION_JSON)
public class CheckInResource {
  
  private final CheckInDAO checkInDAO;
  
  public CheckInResource() {
    this.checkInDAO = new CheckInDAO();
  }

  @GET
  @Timed
  @Path("/all")
  public List<CheckIn> getCheckins() {
    return checkInDAO.getAllCheckIns();
  }

  @GET
  @Timed
  @Path("/new")
  public String checkin(@QueryParam("name") String name, @QueryParam("direction") String direction, @QueryParam("time") long time) {
    Preconditions.checkArgument(direction.equalsIgnoreCase("East") || direction.equalsIgnoreCase("West"), "Direction must be either 'East' or 'West.'");
    Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "Name cannot be empty");
    Preconditions.checkArgument(time >= 0, "Time cannot be negative");
  //  Preconditions.checkArgument(latitude == 0, "Latitude cannot be 0");
  //  Preconditions.checkArgument(longitude == 0, "Longitude cannot be 0");

    Optional<Station> station = Station.getStationFromName(name);
    CheckIn checkIn = new CheckIn(station.get(), Direction.valueOf(direction), time, 0, 0);
    checkInDAO.insert(checkIn);

    return new JsonGeneratorWrapper().startObject().string("id", checkIn.getId()).endObject().asString();
  }

  @POST
  @Timed
  @Path("update")
  public String checkin(@QueryParam("time") long time, @QueryParam("latitude") double latitude, @QueryParam("longitude") double longitude, @QueryParam("id") String id) {
    Preconditions.checkArgument(!Strings.isNullOrEmpty(id), "id cannot be empty");
    Preconditions.checkArgument(time >= 0, "Time cannot be negative");
    Preconditions.checkArgument(latitude == 0, "Latitude cannot be 0");
    Preconditions.checkArgument(longitude == 0, "Longitude cannot be 0");

    CheckIn checkIn = new CheckIn(null, null, time, longitude, latitude, id);
    
    checkInDAO.update(checkIn, checkIn);
    
    return new JsonGeneratorWrapper().startObject().string("id", checkIn.getId()).endObject().asString();
  }
}
