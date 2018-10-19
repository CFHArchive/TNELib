package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

import java.sql.SQLException;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public abstract class DataProvider {

  protected DataManager manager;

  public DataProvider(DataManager manager) {
    this.manager = manager;
  }

  public abstract String identifier();
  public abstract boolean supportUpdate();
  public abstract Boolean first() throws SQLException;
  public abstract Double version() throws SQLException;
  public abstract void initialize() throws SQLException;
  public abstract void update(Double version) throws SQLException;
  public abstract void save(Double version) throws SQLException;
  public abstract void load(Double version) throws SQLException;
  public abstract void delete(Double version) throws SQLException;
  public abstract DatabaseConnector connector() throws SQLException;
}