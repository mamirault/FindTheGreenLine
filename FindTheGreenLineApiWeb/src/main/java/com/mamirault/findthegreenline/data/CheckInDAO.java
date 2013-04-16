package com.mamirault.findthegreenline.data;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.google.common.collect.Lists;
import com.mamirault.findthegreenline.core.CheckIn;
import com.mamirault.findthegreenline.jdbc.DataSourceCreator;
import com.mamirault.findthegreenline.utils.TimeUtils;

public class CheckInDAO {
  private static final String UPDATE_SQL = "UPDATE CheckIns SET name=? time=? latitude=? longitude= where id=?;";

  private static final String INSERT_SQL = "INSERT INTO CheckIns VALUES(?, ?, ?, ?, ?, ?);";
  private static final String SELECT_ONE_SQL = "SELECT * FROM CheckIns where id=?;";
  private static final String SELECT_ALL_SQL = "SELECT * FROM CheckIns order by time asc;";
  private static final String SELECT_ALL_WITHIN_TIME_SQL = "SELECT * FROM CheckIns where time>=? and time<=? order by time asc;";

  public static String ID_COLUMN_TITLE = "id";
  public static String DIRECTION_COLUMN_TITLE = "direction";
  public static String NAME_COLUMN_TITLE = "name";
  public static String TIME_COLUMN_TITLE = "time";
  public static String LATITUDE_COLUMN_TITLE = "latitude";
  public static String LONGITUDE_COLUMN_TITLE = "longitude";

  private final QueryRunner queryRunner;
  private final ResultSetHandler<List<CheckIn>> checkInRSH;

  public CheckInDAO() {
    queryRunner = new QueryRunner(DataSourceCreator.createDataSource());
    checkInRSH = new CheckInRSH();
  }

  public int update(CheckIn originalCheckIn, CheckIn updatedCheckIn) {
    return updateOrInsert(UPDATE_SQL, Lists.<Object> newArrayList(updatedCheckIn.getTime(), updatedCheckIn.getLatitude(), updatedCheckIn.getLongitude(), originalCheckIn.getId()));
  }

  public int insert(CheckIn checkIn) {
    try {
      return updateOrInsert(INSERT_SQL, Lists.<Object> newArrayList(checkIn.getId(), checkIn.getName(), checkIn.getDirection().toString(), TimeUtils.getRelative(checkIn.getTime()), checkIn.getLatitude(), checkIn.getLongitude()));
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
  
  public List<CheckIn> getCheckIn(String id){
    return query(SELECT_ONE_SQL, checkInRSH, Lists.<Object> newArrayList(id));
  }
  public List<CheckIn> getCheckInsWithinTimeframe(long timeframe) throws ParseException{
    long time = TimeUtils.getRelative(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(5));

    return query(SELECT_ALL_WITHIN_TIME_SQL, checkInRSH, Lists.<Object> newArrayList(time, time + timeframe));
  }
  
  public List<CheckIn> getAllCheckIns(){
    return query(SELECT_ALL_SQL, checkInRSH, Lists.<Object> newArrayList());
  }

  private <T> T query(String sqlQuery, ResultSetHandler<T> resultHandler, List<Object> queryParameters) {
    System.out.println(sqlQuery + queryParameters);
    try {
      return queryRunner.query(sqlQuery, resultHandler, queryParameters.toArray());
    } catch (SQLException e) {
      System.out.println("Failed to query CheckIns Table using " + resultHandler.getClass().getName() + ".");
      e.printStackTrace();
    }

    return null;
  }
}
