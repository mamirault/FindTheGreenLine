package com.mamirault.findthegreenline.core;

public class Stop {
  private final Station station;
  private final Direction direction;
  private final long time;

  public Stop(Station station, Direction direction, long time) {
    this.station = station;
    this.direction = direction;
    this.time = time;
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
}