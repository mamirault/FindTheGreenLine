package com.mamirault.findthegreenline.core;

public enum Headsign {
  LECHMERE(Direction.East),
  NORTH_STATION(Direction.East),
  GOVERNMENT_CENTER(Direction.East),
  PARK_ST(Direction.East),
  B___BOSTON_COLLEGE(Direction.West),
  C___CLEVELAND_CIRCLE(Direction.West),
  D___RIVERSIDE(Direction.West),
  E___HEATH_STREET(Direction.West);

  private Headsign(Direction direction) {
    this.direction = direction;
  }

  private final Direction direction;

  public static Headsign parseHeadsign(String headsign) {
    headsign = headsign.replace(" ", "_");
    headsign = headsign.replace("-", "_");
    headsign = headsign.toUpperCase();

    return valueOf(headsign);
  }

  public Direction getDirection() {
    return direction;
  }
}