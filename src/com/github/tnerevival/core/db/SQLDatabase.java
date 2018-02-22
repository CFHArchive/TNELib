package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.sql.SQLResult;

import java.sql.*;
import java.util.*;

public abstract class SQLDatabase implements DatabaseConnector {

  private TreeMap<Integer, SQLResult> results = new TreeMap<>();
  protected TreeMap<Integer, Connection> connections = new TreeMap<>();
  protected DataManager manager;

  public SQLDatabase(DataManager manager) {
    this.manager = manager;
  }

  public abstract Connection connect(int id, DataManager manager);

  @Override
  public Boolean connected(int id, DataManager manager) {
    return connections.get(id) != null;
  }

  public Connection connection(int id, DataManager manager) {
    if(!connected(id, manager)) {
      connections.put(id, connect(id, manager));
    }
    return connections.get(id);
  }

  public java.sql.Connection sqlConnection(int id, DataManager manager) {
    return ((java.sql.Connection)connection(id, manager));
  }

  public int executeQuery(int connection, String query) {
    if(!connected(connection, manager)) {
      connections.put(connection, connect(connection, manager));
    }
    try {
      Statement statement = sqlConnection(connection, manager).createStatement();
      return addResult(statement, statement.executeQuery(query));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public int executePreparedQuery(int connection, String query, Object[] variables) {
    if(!connected(connection, manager)) {
      connections.put(connection, connect(connection, manager));
    }
    try {
      PreparedStatement statement = sqlConnection(connection, manager).prepareStatement(query);
      for(int i = 0; i < variables.length; i++) {
        statement.setObject((i + 1), variables[i]);
      }
      return addResult(statement, statement.executeQuery());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  public void executeUpdate(int connection, String query) {
    if(!connected(connection, manager)) {
      connections.put(connection, connect(connection, manager));
    }
    try {
      Statement statement = sqlConnection(connection, manager).createStatement();
      statement.executeUpdate(query);
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void executePreparedUpdate(int connection, String query, Object[] variables) {
    if(!connected(connection, manager)) {
      connections.put(connection, connect(connection, manager));
    }
    try {
      PreparedStatement prepared = sqlConnection(connection, manager).prepareStatement(query);

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
  }

  public void close(int connection, DataManager manager) {
    if(connected(connection, manager)) {
      try {
        for(SQLResult result : results.values()) {
          result.close();
        }
        results.clear();
        sqlConnection(connection, manager).close();
        connections.remove(connection);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public static Integer boolToDB(boolean value) {
    return (value)? 1 : 0;
  }

  public static Boolean boolFromDB(int value) {
    return (value == 1);
  }
}