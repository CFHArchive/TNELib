package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.Connection;
import com.github.tnerevival.core.db.SQLDatabase;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class MySQL extends SQLDatabase {

  public MySQL(DataManager manager) {
    super(manager);
  }

  @Override
  public Connection connect(int id, DataManager manager) {
    Connection connection;
    java.sql.Connection sqlConnection = null;
    if(connection(id, manager) != null) {
      close(id, manager);
    }
    try {
      Class.forName("com.mysql.jdbc.Driver");
      sqlConnection = DriverManager.getConnection("jdbc:mysql://" + manager.getHost() + ":" + manager.getPort() + "/"  + manager.getDatabase() + "?useSSL=false", manager.getUser(), manager.getPassword());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    int key = connections.lastKey();
    connection = new Connection(key, sqlConnection);
    connections.put(key, connection);
    return connection;
  }
}