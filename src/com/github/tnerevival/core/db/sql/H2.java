package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.SQLDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2 extends SQLDatabase {

  public H2(DataManager manager) {
    super(manager);
  }

  @Override
  public String getDriver() {
    return "org.h2.Driver";
  }

  @Override
  public Boolean dataSource() {
    return true;
  }

  @Override
  public String dataSourceURL() {
    return "org.h2.jdbcx.JdbcDataSource";
  }

  @Override
  public String getURL(String file, String host, int port, String database) {
    return "jdbc:h2:" + file + ";mode=MySQL&DB_CLOSE_DELAY=-1";
  }
}