package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
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
  public String getDriver() {
    return "com.mysql.jdbc.Driver";
  }

  @Override
  public Boolean dataSource() {
    return false;
  }

  @Override
  public String dataSourceURL() {
    return "com.mysql.jdbc.jdbc2.optional.MysqlDataSource";
  }

  @Override
  public String getURL(String file, String host, int port, String database) {
    return "jdbc:mysql://" + host + ":" + port + "/" +
        database + "?useSSL=false&rewriteBatchedStatements=true&useServerPrepStmts=true";
  }
}