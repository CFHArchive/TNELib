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

  public Boolean connected(DataManager manager) {
    return true;
  }

  public void connect(DataManager manager) {
    if(connection == null) {
      connection = new FlatFileConnection(manager.getFile(), manager.isCompress());
    }
  }

  public FlatFileConnection connection(DataManager manager) {
    if(connection == null || !connected(manager)) {
      connect(manager);
    }
    return connection;
  }

  public void close(DataManager manager) {
    connection.close();
  }
}