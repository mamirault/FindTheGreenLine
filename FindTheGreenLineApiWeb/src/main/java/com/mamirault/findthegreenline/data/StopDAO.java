package com.mamirault.findthegreenline.data;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mamirault.findthegreenline.core.Direction;
import com.mamirault.findthegreenline.core.Stop;
import com.mamirault.findthegreenline.jdbc.DataSourceCreator;
import com.mamirault.findthegreenline.resources.StopResource;
import com.mamirault.findthegreenline.utils.TimeUtils;

public class StopDAO {
  private static final String UPDATE_SQL = "UPDATE STOPS SET name=? direction=? time=? timeReadable=? where name=? direction=? time=?;";
  private static final String INSERT_SQL = "INSERT INTO STOPS VALUES(?, ?, ?, ?, ?);";
  private static final String SELECT_ALL_SQL = "SELECT name, direction, time, weekPortion FROM Stops order by time asc limit ?, ?;";
  private static final String SELECT_ALL_WHERE_SQL = "SELECT name, direction, time, weekPortion FROM Stops where name like ? and direction like ? order by time asc limit ?, ?;";
  private static final String SELECT_ALL_WITHIN_TIME_SQL = "SELECT name, direction, time, weekPortion FROM Stops where name like ? and direction like ? and time>=? and time<=? order by time asc limit ?, ?;";

  public static String DIRECTION_COLUMN_TITLE = "direction";
  public static String NAME_COLUMN_TITLE = "name";
  public static String WEEK_PORTION_COLUMN_TITLE = "weekPortion";
  public static String TIME_COLUMN_TITLE = "time";

  private final QueryRunner queryRunner;
  private final ResultSetHandler<List<Stop>> stopRSH;

  public StopDAO() {
    queryRunner = new QueryRunner(DataSourceCreator.createDataSource());
    stopRSH = new StopRSH();
  }

  public int update(Stop originalStop, Stop updatedStop) {
    return updateOrInsert(UPDATE_SQL, Lists.<Object> newArrayList(updatedStop.getName(), updatedStop.getDirection(), updatedStop.getTime(), new Date(TimeUnit.SECONDS.toMillis(updatedStop.getTime())).toString(),originalStop.getName(), originalStop.getDirection(), originalStop.getTime()));
  }

  public int insert(Stop stop) {
    try {
      return updateOrInsert(INSERT_SQL, Lists.<Object> newArrayList(stop.getName(), stop.getDirection().toString(), TimeUtils.getRelative(stop.getTime()), new Date(TimeUtils.getRelative(stop.getTime())).toString(), stop.getWeekPortion().toString()));
    } catch (ParseException e) {
      e.printStackTrace();
      return 0;
    }
  }

  private int updateOrInsert(String sqlQuery, List<Object> parameters) {
    try {
      int rowsUpdated = queryRunner.update(sqlQuery, parameters.toArray());

      System.out.println(rowsUpdated + " rows updated.");
      return rowsUpdated;
    } catch (SQLException e) {
      if (!e.getMessage().contains("Duplicate")) {
        e.printStackTrace();
      } else {
        System.out.println("Duplicate");
      }
    }

    return 0;
  }
  
  public List<Stop> getAllStops(String name, String direction, long offset, long count){
    return query(SELECT_ALL_WHERE_SQL, stopRSH, Lists.<Object> newArrayList(name, direction, offset, count));
  }

  public List<Stop> getStopsWithinTimeframe(String name, String direction, long timeframe, long offset, long count) throws ParseException{
    long time = TimeUtils.getRelative(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(5));

    return query(SELECT_ALL_WITHIN_TIME_SQL, stopRSH, Lists.<Object> newArrayList(name, direction, time, time + timeframe, offset, count));
  }

  private <T> T query(String sqlQuery, ResultSetHandler<T> resultHandler, List<Object> queryParameters) {
    System.out.println(sqlQuery + queryParameters);
    try {
      return queryRunner.query(sqlQuery, resultHandler, queryParameters.toArray());
    } catch (SQLException e) {
      System.out.println("Failed to query Stops Table using " + resultHandler.getClass().getName() + ".");
      e.printStackTrace();
    }

    return null;
  }
  
  @Test
  public void testGetTimeFrame() throws ParseException{
      String name = "Northeastern University";
      Direction direction = Direction.West;
      long timeframe = TimeUnit.HOURS.toMillis(1);
      long offset = 0;
      long count = 1234567;
  
      List<Stop> stops = getStopsWithinTimeframe(name, direction.toString(), timeframe, offset, count);
      System.out.println(stops);
  }
}
