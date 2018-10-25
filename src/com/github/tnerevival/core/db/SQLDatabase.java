package com.github.tnerevival.core.db;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.sql.SQLResult;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.javalite.activejdbc.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.TreeMap;

public abstract class SQLDatabase implements DatabaseConnector {

  private TreeMap<Integer, SQLResult> results = new TreeMap<>();
  protected Connection connection;
  protected DataManager manager;
  private HikariConfig config;
  private HikariDataSource dataSource;

  private DB db;

  public SQLDatabase(DataManager manager) {
    this.manager = manager;
  }

  @Override
  public void initialize(DataManager manager) {

    db = new DB(TNELib.getDBName());

    config = new HikariConfig();

    try {
      if(manager.getProviders().get(manager.getFormat()).connector().dataSource()) {
        config.setDataSourceClassName(manager.getProviders().get(manager.getFormat()).connector().dataSourceURL());
      } else {
        config.setDriverClassName(manager.getProviders().get(manager.getFormat()).connector().getDriver());
        config.setJdbcUrl(manager.getProviders().get(manager.getFormat()).connector().getURL(manager.getFile(), manager.getHost(), manager.getPort(), manager.getDatabase()));
      }
    } catch(SQLException e) {
      e.printStackTrace();
    }

    config.setUsername(manager.getUser());
    config.setPassword(manager.getPassword());

    config.setMaximumPoolSize(10);
    config.setConnectionTimeout(30000);
    config.setMinimumIdle(2);
    config.addDataSourceProperty("autoReconnect", true);
    config.addDataSourceProperty("cachePrepStmts", true);
    config.addDataSourceProperty("prepStmtCacheSize", 250);
    config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
    config.addDataSourceProperty("rewriteBatchedStatements", true);
    config.addDataSourceProperty("useServerPrepStmts", true);
    config.addDataSourceProperty("cacheResultSetMetadata", true);
    config.addDataSourceProperty("useSSL", false);

    dataSource = new HikariDataSource(config);
  }

  @Override
  public void open() {
    try {
      db.open(dataSource);
    } catch(Exception e) {
      //TODO: Nothing really
    }
  }
  @Override
  public void close() {
    db.close();
  }

  public static Integer boolToDB(boolean value) {
    return (value)? 1 : 0;
  }

  public static Boolean boolFromDB(int value) {
    return (value == 1);
  }
}