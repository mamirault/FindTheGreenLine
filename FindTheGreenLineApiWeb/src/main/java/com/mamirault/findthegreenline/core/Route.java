package com.mamirault.findthegreenline.core;

import java.util.List;

import com.google.common.collect.Lists;

public class Route {
  private final Direction direction;
  private List<Stop> stops;

  public Route(Direction direction) {
    this.direction = direction;
    this.stops = Lists.newArrayList();
  }

  public Direction getDirection() {
    return direction;
  }

  public List<Stop> getStops() {
    return stops;
  }

  public Route addStop(Stop stop) {
    stops.add(stop);

    return this;
  }
}
