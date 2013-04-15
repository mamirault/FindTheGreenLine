package com.mamirault.findthegreenline.core;

public class Stop {
  private final Station station;
  private final Direction direction;
  private final WeekPortion weekPortion;
  private final long time;

  public Stop(Station station, Direction direction, long time, WeekPortion weekPortion) {
    this.station = station;
    this.direction = direction;
    this.time = time;
    this.weekPortion = weekPortion;
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

  public WeekPortion getWeekPortion() {
    return weekPortion;
  }

  public long getTime() {
    return time;
  }
}
