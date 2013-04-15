package com.mamirault.findthegreenline.resources;

import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mamirault.findthegreenline.core.Station;
import com.mamirault.findthegreenline.data.StopDAO;
import com.yammer.metrics.annotation.Timed;

@Path("/stations")
@Produces(MediaType.APPLICATION_JSON)
@SuppressWarnings("unused")
public class StationResource {
  private final static String TIMEFRAME_DEFAULT = "3600";

  private final String template;
  private final String defaultName;
  private final AtomicLong counter;
  private final StopDAO stopDAO;

  public StationResource(String template, String defaultName) {
    this.template = template;
    this.defaultName = defaultName;
    this.counter = new AtomicLong();
    this.stopDAO = new StopDAO();
  }

  @GET
  @Timed
  @Path("/all")
  public String getAllStations() {
    return Station.getAllJson().asString();
  }
}