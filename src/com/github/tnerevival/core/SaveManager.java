package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public class SaveManager {

  protected LinkedList<BigDecimal> versions = new LinkedList<>();

  protected DataManager manager;
  protected BigDecimal saveVersion = BigDecimal.ZERO;
  protected File file;

  public SaveManager(DataManager manager) {
    this.manager = manager;
  }

  public void addVersion(BigDecimal version) {
    addVersion(version, false);
  }

  public void addVersion(BigDecimal version, boolean current) {
    if(current) TNELib.instance().currentSaveVersion = version;
    versions.add(version);
  }

  public void initialize() throws SQLException {
    if(manager.getDb().first()) {
      manager.getDb().initialize();
      return;
    }
    saveVersion = manager.getDb().version();
    TNELib.instance().getLogger().info("Save file of version: " + saveVersion + " detected.");
    load();
  }

  public void load() throws SQLException {
    if(saveVersion.compareTo(BigDecimal.ZERO) > 0 && saveVersion.compareTo(TNELib.instance().currentSaveVersion) < 0) {
      manager.getDb().update(saveVersion);
      TNELib.instance().getLogger().info("Saved data has been updated!");
    }
    BigDecimal version = (saveVersion.compareTo(BigDecimal.ZERO) > 0) ? saveVersion : TNELib.instance().currentSaveVersion;
    manager.getDb().load(version);
    TNELib.instance().getLogger().info("Finished loading data!");
  }

  public void save() throws SQLException {
    manager.getDb().save(TNELib.instance().currentSaveVersion);
    TNELib.instance().getLogger().info("Finished saving data!");
  }

  public DataManager getDataManager() {
    return manager;
  }
}