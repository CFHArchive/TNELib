package com.github.tnerevival.core.db;

import com.github.tnerevival.core.db.flat.FlatFileConnection;

import java.io.File;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class FlatFile extends Database {

  private String file;
  private boolean gzip = true;
  private FlatFileConnection connection;

  public FlatFile(String directory, String file, boolean gzip) {
    this(directory + File.separator + file, gzip);
  }

  public FlatFile(String file, boolean gzip) {
    this.file = file;
    this.gzip = gzip;
    connection = new FlatFileConnection(file, gzip);
  }

  @Override
  public Boolean connected() {
    return true;
  }

  @Override
  public void connect() {
    if(connection == null) {
      connection = new FlatFileConnection(file, gzip);
    }
  }

  @Override
  public FlatFileConnection connection() {
    if(connection == null || !connected()) {
      connect();
    }
    return connection;
  }

  @Override
  public void close() {
    connection.close();
  }

  public File getFile() {
    return new File(this.file);
  }
}