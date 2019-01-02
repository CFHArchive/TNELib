package com.github.tnerevival.core.db;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.DataManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public abstract class SQLDatabase implements DatabaseConnector {
  protected DataManager manager;
  private HikariConfig config;
  private HikariDataSource dataSource;

  public SQLDatabase(DataManager manager) {
    this.manager = manager;
  }

  @Override
  public void initialize(DataManager manager) {

    config = new HikariConfig();

    try {
      if(TNELib.useDataSource() && manager.getProviders().get(manager.getFormat()).connector().dataSource()) {
        config.setDataSourceClassName(manager.getProviders().get(manager.getFormat()).connector().dataSourceURL());
      } else {
        config.setDriverClassName(manager.getProviders().get(manager.getFormat()).connector().getDriver());
        config.setJdbcUrl(manager.getProviders().get(manager.getFormat()).connector().getURL(manager.getFile(), manager.getHost(), manager.getPort(), manager.getDatabase()));
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }

    config.setUsername(manager.getUser());
    config.setPassword(manager.getPassword());

    config.setMaximumPoolSize(15);
    config.setConnectionTimeout(30000);
    config.setMinimumIdle(2);
    //config.setLeakDetectionThreshold(30000);

    for(Map.Entry<String, Object> entry : hikariProperties().entrySet()) {
      config.addDataSourceProperty(entry.getKey(), entry.getValue());
    }

    dataSource = new HikariDataSource(config);
  }

  public Boolean connected(DataManager manager) {
    return true;
  }

  public Connection connection(DataManager manager) throws SQLException {
    return dataSource.getConnection();
  }

  public ResultSet executeQuery(Statement statement, String query) {
    ResultSet results = null;
    try {
      results = statement.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return results;
  }

  public ResultSet executePreparedQuery(PreparedStatement statement, Object[] variables) {
    try {
      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      return statement.executeQuery();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void executeUpdate(String query) {
    try(Connection con = dataSource.getConnection();
        Statement statement = con.createStatement()) {
      statement.executeUpdate(query);
    } catch (SQLException e) {
      TNELib.debug(e);
    }
  }

  public void executePreparedUpdate(String query, Object[] variables) {
    try(Connection con = dataSource.getConnection();
        PreparedStatement statement = con.prepareStatement(query)) {

      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void close(Connection connection, Statement statement, ResultSet results) {
    if(results != null) {
      try {
        results.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }

    if(statement != null) {
      try {
        statement.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }

    if(connection != null) {
      try {
        connection.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public HikariDataSource getDataSource() {
    return dataSource;
  }

  public static Integer boolToDB(boolean value) {
    return (value)? 1 : 0;
  }

  public static Boolean boolFromDB(int value) {
    return (value == 1);
  }
}