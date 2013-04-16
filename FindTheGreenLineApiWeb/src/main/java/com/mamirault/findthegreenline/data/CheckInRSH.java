package com.mamirault.findthegreenline.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.mamirault.findthegreenline.core.CheckIn;
import com.mamirault.findthegreenline.core.Direction;
import com.mamirault.findthegreenline.core.Station;

public class CheckInRSH implements ResultSetHandler<List<CheckIn>> {

  @Override
  public List<CheckIn> handle(ResultSet rs) throws SQLException {
    List<CheckIn> checkIns = Lists.newArrayList();

    while (rs.next()) {
      Optional<Station> maybeStation = Station.getStationFromName(rs.getString(CheckInDAO.NAME_COLUMN_TITLE) + " Station");

      if (maybeStation.isPresent()) {
        Direction direction = Direction.valueOf(rs.getString(CheckInDAO.DIRECTION_COLUMN_TITLE));
        long time = rs.getLong(CheckInDAO.TIME_COLUMN_TITLE);
        double latitude = rs.getDouble(CheckInDAO.LATITUDE_COLUMN_TITLE);
        double longitude = rs.getDouble(CheckInDAO.LONGITUDE_COLUMN_TITLE);
        String id = rs.getString(CheckInDAO.ID_COLUMN_TITLE);
        

        checkIns.add(new CheckIn(maybeStation.get(), direction, time, longitude, latitude,id));
      } else {
        System.out.println("Could not generate a station from the name: " + rs.getString(CheckInDAO.NAME_COLUMN_TITLE));
      }
    }

    return checkIns;
  }
}