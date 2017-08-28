package com.github.tnerevival.core.version;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.SQLManager;
import com.github.tnerevival.core.db.*;
import com.github.tnerevival.core.db.sql.H2;
import com.github.tnerevival.core.db.sql.MySQL;
import com.github.tnerevival.core.db.sql.SQLite;

import java.io.File;

public abstract class Version {

  protected Database db;
  private SQLManager sqlManager;

  public Version(SQLManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  private void createDB() {
    createDB(TNELib.instance().saveFormat.toLowerCase());
  }

  private void createDB(String type) {
    switch(type) {
      case "mysql":
        db = new MySQL(sqlManager.getMysqlHost(), sqlManager.getMysqlPort(), sqlManager.getMysqlDatabase(), sqlManager.getMysqlUser(), sqlManager.getMysqlPassword());
        break;
      case "h2":
        db = new H2(sqlManager.getH2File(), sqlManager.getMysqlUser(), sqlManager.getMysqlPassword());
        break;
      case "sqlite":
        db = new SQLite(sqlManager.getSqliteFile());
        break;
      default:
        db = new FlatFile(TNELib.instance().getDataFolder() + File.separator + sqlManager.getFlatfile(), sqlManager.isGzip());
    }
  }

  //Helper methods to automatically cast db to proper database class
  public SQLDatabase sql() {
    if(db == null) createDB();
    return (SQLDatabase)db;
  }

  public MySQL mysql() {
    if(db == null) createDB();
    return (MySQL)db;
  }

  public SQLite sqlite() {
    if(db == null) createDB();
    return (SQLite)db;
  }

  public H2 h2() {
    if(db == null) createDB();
    return (H2)db;
  }


  public FlatFile flatfile() {
    if(db == null) createDB();
    return (FlatFile)db;
  }

  public SQLManager getSqlManager() {
    return sqlManager;
  }

  //abstract methods to be implemented by each child class
  public abstract boolean firstRun();
  public abstract double getSaveVersion();
  public abstract double versionNumber();
  public abstract void update(double version, String type);
  public abstract void loadFlat(File file);
  public abstract void saveFlat(File file);
  public abstract void loadMySQL();
  public abstract void saveMySQL();
  public abstract void loadSQLite();
  public abstract void saveSQLite();
  public abstract void loadH2();
  public abstract void saveH2();
  public abstract void createTables(String type);
}