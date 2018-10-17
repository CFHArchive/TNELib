package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

import java.math.BigDecimal;
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
  public abstract BigDecimal version() throws SQLException;
  public abstract void initialize() throws SQLException;
  public abstract void update(BigDecimal version) throws SQLException;
  public abstract void save(BigDecimal version) throws SQLException;
  public abstract void load(BigDecimal version) throws SQLException;
  public abstract void delete(BigDecimal version) throws SQLException;
  public abstract DatabaseConnector connector() throws SQLException;
}