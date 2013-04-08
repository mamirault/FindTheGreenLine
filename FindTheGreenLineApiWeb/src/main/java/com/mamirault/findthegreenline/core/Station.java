package com.mamirault.findthegreenline.core;

public class Station {
  private final String address;
  private final String name;
  private final double latitude;
  private final double longitude;

  Station(final String address, final String name, final double latitude, final double longitude) {
    this.address = address;
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public String getAddress() {
    return address;
  }

  public String getName() {
    return name;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
