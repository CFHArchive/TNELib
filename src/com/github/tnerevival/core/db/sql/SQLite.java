package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.Connection;
import com.github.tnerevival.core.db.SQLDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class SQLite extends SQLDatabase {

  public SQLite(DataManager manager) {
    super(manager);
  }

  @Override
  public Connection connect(int id, DataManager manager) {
    File db = new File(manager.getFile());
    if(!db.exists()) {
      try {
        db.createNewFile();
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    Connection connection;
    java.sql.Connection sqlConnection = null;
    if(connection(id, manager) != null) {
      close(id, manager);
    }
    try {
      Class.forName("org.sqlite.JDBC");
      sqlConnection = DriverManager.getConnection("jdbc:sqlite:" + manager.getFile());
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