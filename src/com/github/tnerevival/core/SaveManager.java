package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;

import java.io.File;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public class SaveManager {

  protected LinkedList<Double> versions = new LinkedList<>();

  protected DataManager manager;
  protected Double saveVersion = 0.0;
  protected File file;

  public SaveManager(DataManager manager) {
    this.manager = manager;
  }

  public void addVersion(Double version) {
    addVersion(version, false);
  }

  public void addVersion(Double version, boolean current) {
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
    if(saveVersion < TNELib.instance().currentSaveVersion && saveVersion != 0) {
      manager.getDb().update(saveVersion);
      TNELib.instance().getLogger().info("Saved data has been updated!");
    }
    Double version = (saveVersion != 0.0) ? saveVersion : TNELib.instance().currentSaveVersion;
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