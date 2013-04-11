package com.mamirault.findthegreenline.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.common.base.Preconditions;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceCreator {
  
  private static final Log LOG = LogFactory.getLog(DataSourceCreator.class);

  private static final String JDBC_MYSQL_STRING_FORMAT = "jdbc:mysql://%s:%s/%s?autoReconnect=true&characterEncoding=UTF8";
  
  private static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
  private static final int MYSQL_PORT = 3306;
  
  private static String getJdbcUrl(String host, int port, String database) {
    return String.format(JDBC_MYSQL_STRING_FORMAT, host, port, database);
  }
  
  public static Connection createSingleConnection(final String host, @Nullable String database, final String user, final String password) {
    Preconditions.checkNotNull(host);
    Preconditions.checkNotNull(user);
    Preconditions.checkNotNull(password);
    
    if (database == null) {
      database = "";
    }
    
    final ComboPooledDataSource pool = createConnectionPool(host, database, user, password);
    
   // final ZkConfig zkConfig = ZkUtils.getDefaultZkConfig();
    
    pool.setCheckoutTimeout(5000);
    pool.setTestConnectionOnCheckout(true);
    pool.setMaxPoolSize(1);
    pool.setMinPoolSize(0);
    pool.setMaxIdleTime(1);
    pool.setAcquireRetryAttempts(3);
    
    try {
      return pool.getConnection();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
  private static ComboPooledDataSource createConnectionPool(String host, String database, String user, String password) {
    LOG.info(String.format("Creating connection pool (MySQL) to %s:%s - database: %s", host, MYSQL_PORT, database));
    
    final ComboPooledDataSource pool = new ComboPooledDataSource();
    
    try {
      pool.setDriverClass(MYSQL_DRIVER_CLASS_NAME);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
    pool.setJdbcUrl(getJdbcUrl(host, MYSQL_PORT, database));
    
    pool.setUser(user);
    pool.setPassword(password);
    
    return pool;
  }
  
  public static DataSource createDataSource() {    
    final String host = "localhost";
    final String database = "FindTheGreenLine";
  
    final String user = "root";
    final String password = "";
    
    final ComboPooledDataSource pool = createConnectionPool(host, database, user, password);
    
    pool.setMinPoolSize(1);
    pool.setInitialPoolSize(1);
    pool.setMaxPoolSize(5);
    pool.setCheckoutTimeout(5000);
    
    pool.setTestConnectionOnCheckout(true);
  
    return pool;
  }
}
