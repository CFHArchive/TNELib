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
      if(manager.getProviders().get(manager.getFormat()).connector().dataSource()) {
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

    config.setMaximumPoolSize(10);
    config.setConnectionTimeout(30000);
    config.setMinimumIdle(2);
    config.addDataSourceProperty("autoReconnect", true);
    config.addDataSourceProperty("cachePrepStmts", true);
    config.addDataSourceProperty("prepStmtCacheSize", 250);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    config.addDataSourceProperty("rewriteBatchedStatements", true);
    config.addDataSourceProperty("useServerPrepStmts", true);
    config.addDataSourceProperty("cacheResultSetMetadata", true);
    config.addDataSourceProperty("useSSL", false);

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
    Connection con = null;
    Statement statement = null;
    try {
      con = dataSource.getConnection();
      statement = con.createStatement();
      statement.executeUpdate(query);
      statement.close();
      con.close();
    } catch (SQLException e) {
      TNELib.debug(e);
    } finally {
      try {
        if(statement != null) statement.close();
        if(con != null) con.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public void executePreparedUpdate(String query, Object[] variables) {
    Connection con = null;
    PreparedStatement statement = null;
    try {
      con = dataSource.getConnection();
      statement =  con.prepareStatement(query);

      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      try {
        if(statement != null) statement.close();
        if(con != null) con.close();
      } catch(SQLException e) {
        e.printStackTrace();
      }
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