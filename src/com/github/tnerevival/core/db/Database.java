package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public abstract class Database {

  protected DataManager manager;

  public Database(DataManager manager) {
    this.manager = manager;
  }

  public abstract boolean supportUpdate();
  public abstract Boolean first();
  public abstract Double version();
  public abstract void initialize();
  public abstract void update(Double version);
  public abstract void save(Double version);
  public abstract void load(Double version);
  public abstract DatabaseConnector connector();
}