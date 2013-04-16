package com.mamirault.findthegreenline.core;

import java.util.UUID;

public class CheckIn {
  private Station station;
  private Direction direction;
  private long time;
  private double latitude;
  private double longitude;
  private String id;

  public CheckIn(Station station, Direction direction, long time, double longitude, double latitude) {
    this.station = station;
    this.direction = direction;
    this.time = time;
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = UUID.randomUUID().toString();
  }
  
  public CheckIn(Station station, Direction direction, long time, double longitude, double latitude, String id) {
    this.station = station;
    this.direction = direction;
    this.time = time;
    this.latitude = latitude;
    this.longitude = longitude;
    this.id = id;
  }

  public Station getStation() {
    return station;
  }

  public String getName() {
    return station.getName();
  }

  public Direction getDirection() {
    return direction;
  }

  public long getTime() {
    return time;
  }
  
  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
  
  public String getId(){
    return id;
  }
}
