package com.mamirault.findthegreenline.data;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.google.common.collect.Lists;
import com.mamirault.findthegreenline.core.Stop;
import com.mamirault.findthegreenline.jdbc.DataSourceCreator;

public class StopDAO {
  public static final String UPDATE_SQL = "UPDATE STOPS SET name=? direction=? time=? timeReadable=? where name=? direction=? time=?;";
  public static final String INSERT_SQL = "INSERT INTO STOPS VALUES(?, ?, ?, ?);";
  public static final String SELECT_ALL_SQL = "SELECT * FROM Stops where name=? direction=? time=? limit ?, ?;";

  public static String DIRECTION_COLUMN_TITLE = "direction";
  public static String NAME_COLUMN_TITLE = "name";
  public static String TIME_COLUMN_TITLE = "time";

  private final QueryRunner queryRunner;

  public StopDAO() {
    queryRunner = new QueryRunner(DataSourceCreator.createDataSource());
  }

  public int update(Stop originalStop, Stop updatedStop) {
    return updateOrInsert(UPDATE_SQL, Lists.<Object> newArrayList(updatedStop.getName(), updatedStop.getDirection(), updatedStop.getTime(), new Date(TimeUnit.SECONDS.toMillis(updatedStop.getTime())).toString(),originalStop.getName(), originalStop.getDirection(), originalStop.getTime()));
  }

  public int insert(Stop stop) {
    return updateOrInsert(INSERT_SQL, Lists.<Object> newArrayList(stop.getName(), stop.getDirection().toString(), stop.getTime(), new Date(TimeUnit.SECONDS.toMillis(stop.getTime())).toString()));
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

  private <T> T query(String sqlQuery, ResultSetHandler<T> resultHandler, List<Object> queryParameters) {
    try {
      return queryRunner.query(sqlQuery, resultHandler, queryParameters.toArray());
    } catch (SQLException e) {
      System.out.println("Failed to query Stops Table using " + resultHandler.getClass().getName() + ".");
      e.printStackTrace();
    }

    return null;
  }
}
