package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.sql.SQLResult;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.*;

public abstract class SQLDatabase implements DatabaseConnector {

  private TreeMap<Integer, SQLResult> results = new TreeMap<>();
  protected Connection connection;
  protected DataManager manager;
  private HikariConfig config;
  private HikariDataSource dataSource;

  public SQLDatabase(DataManager manager) {
    this.manager = manager;

    config = new HikariConfig();

    try {
      if(manager.getProviders().get(manager.getFormat()).connector().dataSource()) {
        config.setDataSourceClassName(manager.getProviders().get(manager.getFormat()).connector().dataSourceURL());
      } else {
        config.setJdbcUrl(manager.getProviders().get(manager.getFormat()).connector().getURL(manager.getFile(), manager.getHost(), manager.getPort(), manager.getDatabase()));
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }

    config.setUsername(manager.getUser());
    config.setPassword(manager.getPassword());

    if(manager.getFormat().equalsIgnoreCase("mysql")) {
      config.addDataSourceProperty("cachePrepStmts", "true");
      config.addDataSourceProperty("prepStmtCacheSize", "250");
      config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    dataSource = new HikariDataSource(config);
  }

  @Override
  public void connect(DataManager manager) throws Exception {

  }

  public Boolean connected(DataManager manager) {
    return connection != null;
  }

  public Connection connection(DataManager manager)  {
    try {
      return dataSource.getConnection();
    } catch(SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int executeQuery(String query) {
    try {
      Statement statement = connection(manager).createStatement();
      return addResult(statement, statement.executeQuery(query));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public int executePreparedQuery(String query, Object[] variables) {
    try {
      PreparedStatement statement = connection(manager).prepareStatement(query);
      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      return addResult(statement, statement.executeQuery());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public void executeUpdate(String query) {
    try {
      Statement statement = connection(manager).createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void executePreparedUpdate(String query, Object[] variables) {
    try {
      PreparedStatement prepared = connection(manager).prepareStatement(query);

      for(int i = 0; i < variables.length; i++) {
        prepared.setObject((i + 1), variables[i]);
      }
      prepared.executeUpdate();
      prepared.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public int addResult(Statement statement, ResultSet resultSet) {
    int key = (results.isEmpty())? 1 : results.lastKey() + 1;
    SQLResult result = new SQLResult(key, statement, resultSet);
    results.put(result.getId(), result);
    return result.getId();
  }

  public ResultSet results(int id) {
    return results.get(id).getResult();
  }

  public void closeResult(int id) {
    results.get(id).close();
    results.remove(id);
  }

  public static Integer boolToDB(boolean value) {
    return (value)? 1 : 0;
  }

  public static Boolean boolFromDB(int value) {
    return (value == 1);
  }
}