package com.mamirault.findthegreenline.core;

import java.util.List;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

public class StationRoute {
  public static final List<Station> E = Lists.newArrayList(Station.LECHMERE, Station.SCIENCE_PARK, Station.NORTH, Station.HAYMARKET, Station.GOVERNMENT_CENTER, Station.PARK_ST, Station.BOYLSTON, Station.ARLINGTON, Station.COPLEY,
      Station.PRUDENTIAL, Station.SYMPHONY, Station.NORTHEASTERN_UNIVERSITY, Station.MUSEUM_OF_FINE_ARTS, Station.LONGWOOD_MEDICAL_AREA, Station.BRIGHAM_CIRCLE, Station.FENWOOD_RD, Station.MISSION_PARK, Station.RIVERWAY,
      Station.BACK_OF_THE_HILL, Station.HEATH_ST);
  public static final List<Station> E_LECHMERE_TO_HEATH = Lists.newArrayList(Station.LECHMERE, Station.HEATH_ST);
  public static final List<Station> E_HEATH_TO_LECHMERE = Lists.newArrayList(Station.HEATH_ST, Station.LECHMERE);

  public static Optional<Station> getStationFromAddress(String address) {
    for (Station station : E) {
      if (station.getAddress().equalsIgnoreCase(address)) {
        return Optional.of(station);
      }
    }

    return Optional.<Station> absent();
  }

  public static Optional<Station> getStationFromName(String name) {
    for (Station station : E) {
      if (station.getName().equalsIgnoreCase(name.substring(0, name.indexOf("Station")).trim())) {
        return Optional.of(station);
      }
    }

    return Optional.<Station> absent();
  }
}
