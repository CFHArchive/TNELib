package com.github.tnerevival.core.db;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.DataManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.javalite.activejdbc.DB;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public abstract class SQLDatabase implements DatabaseConnector {
  private static DB db;
  protected DataManager manager;
  private static HikariConfig config;
  private static HikariDataSource dataSource;
  private static boolean initialized = false;

  public SQLDatabase(DataManager manager) {
    this.manager = manager;
  }

  @Override
  public void initialize(DataManager manager) {
    db = new DB("TNE");

    initialized = true;
    if(initialized) {
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

      for(Map.Entry<String, Object> entry : hikariProperties().entrySet()) {
        config.addDataSourceProperty(entry.getKey(), entry.getValue());
      }

      dataSource = new HikariDataSource(config);
    }
  }

  public static DB getDb() {
    return db;
  }

  public static void open() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    StackTraceElement element = stackTrace[2];
    TNELib.debug("SQLDatabase.open called by: [" + "Class: " + element.getClassName() + " via Method: " + element.getMethodName() + " at Line: " + element.getLineNumber());
    if(db.hasConnection()) return;
    TNELib.debug("Connection doesn't exist, opening now");
    db.open(dataSource);
  }

  public static void close() {
    if(!db.hasConnection()) return;
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    StackTraceElement element = stackTrace[2];
    TNELib.debug("SQLDatabase.close called by: [" + "Class: " + element.getClassName() + " via Method: " + element.getMethodName() + " at Line: " + element.getLineNumber());
    db.close();
  }

  public static void open(DataSource datasource) {
    if(db.hasConnection()) return;
    db.open(datasource);
  }

  public Boolean connected(DataManager manager) {
    return true;
  }

  public static Connection connection(DataManager manager) throws SQLException {
    return db.open(dataSource).getConnection();
  }

  public static ResultSet executeQuery(Statement statement, String query) {
    try(ResultSet results = db.getConnection().createStatement().executeQuery(query)) {
      return results;
    } catch(SQLException ignore) {}

    return null;
  }

  public static ResultSet executePreparedQuery(PreparedStatement statement, Object[] variables) {
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

  public static void executeUpdate(String query) {
    db.open(dataSource);
    try {
      db.getConnection().createStatement().executeUpdate(query);
    } catch (SQLException ignore) {}
    db.close();
  }

  public static void executePreparedUpdate(String query, Object[] variables) {
    db.open(dataSource);
    try(PreparedStatement statement = db.getConnection().prepareStatement(query)) {

      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      statement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db.close();
  }

  public static void close(Connection connection, Statement statement, ResultSet results) {
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

  public static HikariDataSource getDataSource() {
    return dataSource;
  }

  public static Integer boolToDB(boolean value) {
    return (value)? 1 : 0;
  }

  public static Boolean boolFromDB(int value) {
    return (value == 1);
  }
}