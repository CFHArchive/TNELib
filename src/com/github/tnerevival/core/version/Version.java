package com.github.tnerevival.core.version;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.db.*;

import java.io.File;

public abstract class Version {
  protected Database db;

  protected void createDB() {
    createDB(TNELib.instance.saveFormat.toLowerCase());
  }

  protected void createDB(String type) {
    switch(type) {
      case "mysql":
        db = new MySQL(mysqlHost(), mysqlPort(), mysqlDatabase(), mysqlUser(), mysqlPassword());
        break;
      case "h2":
        db = new H2(h2File(), mysqlUser(), mysqlPassword());
        break;
      case "sqlite":
        db = new H2(h2File(), mysqlUser(), mysqlPassword());
        break;
      default:
        db = new FlatFile(TNELib.instance.getDataFolder() + File.separator + ffFile());
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

  //abstract methods to be implemented by each child class
  public abstract double versionNumber();
  public abstract String mysqlHost();
  public abstract Integer mysqlPort();
  public abstract String mysqlDatabase();
  public abstract String mysqlUser();
  public abstract String mysqlPassword();
  public abstract String prefix();
  public abstract String h2File();
  public abstract String sqliteFile();
  public abstract String ffFile();
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