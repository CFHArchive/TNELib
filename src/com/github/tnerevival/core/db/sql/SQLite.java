package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.SQLDatabase;

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
  public String getDriver() {
    return null;
  }

  @Override
  public Boolean dataSource() {
    return null;
  }

  @Override
  public String dataSourceURL() {
    return null;
  }

  @Override
  public String getURL(String file, String host, int port, String database) {
    file = (file.contains(".db"))? file : file + ".db";
    return "jdbc:sqlite:" + file;
  }
}