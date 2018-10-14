package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
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
    return null;
  }
}