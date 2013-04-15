package com.mamirault.findthegreenline.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.mamirault.findthegreenline.core.Direction;
import com.mamirault.findthegreenline.core.Station;
import com.mamirault.findthegreenline.core.Stop;
import com.mamirault.findthegreenline.core.WeekPortion;

public class StopRSH implements ResultSetHandler<List<Stop>> {

  @Override
  public List<Stop> handle(ResultSet rs) throws SQLException {
    List<Stop> stops = Lists.newArrayList();

    while (rs.next()) {
      Optional<Station> maybeStation = Station.getStationFromName(rs.getString(StopDAO.NAME_COLUMN_TITLE) + " Station");

      if (maybeStation.isPresent()) {
        Direction direction = Direction.valueOf(rs.getString(StopDAO.DIRECTION_COLUMN_TITLE));
        WeekPortion weekPortion = WeekPortion.valueOf(rs.getString(StopDAO.WEEK_PORTION_COLUMN_TITLE));
        long time = rs.getLong(StopDAO.TIME_COLUMN_TITLE);

        stops.add(new Stop(maybeStation.get(),direction, time, weekPortion));
      } else {
        System.out.println("Could not generate a station from the name: " + rs.getString(StopDAO.NAME_COLUMN_TITLE));
      }
    }

    return stops;
  }
}