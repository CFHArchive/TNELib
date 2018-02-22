package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.flat.FlatFileConnection;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class FlatFile implements DatabaseConnector {

  private FlatFileConnection connection;

  public Boolean connected(int id, DataManager manager) {
    return true;
  }

  public Connection connect(int id, DataManager manager) {
    if(connection == null) {
      connection = new FlatFileConnection(manager.getFile(), manager.isCompress());
    }
    return new Connection(0, connection);
  }

  public FlatFileConnection connection(int id, DataManager manager) {
    if(connection == null || !connected(id, manager)) {
      connect(id, manager);
    }
    return connection;
  }

  public void close(int id, DataManager manager) {
    connection.close();
  }
}