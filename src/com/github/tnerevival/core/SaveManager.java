package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public class SaveManager {

  protected List<Double> versions = new ArrayList<>();

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

  public void initialize() {
    if(manager.getDb().first()) {
      manager.getDb().initialize();
      return;
    }
    saveVersion = manager.getDb().version();
    TNELib.instance().getLogger().info("Save file of version: " + saveVersion + " detected.");
    load();
  }

  public void load() {
    if(saveVersion < TNELib.instance().currentSaveVersion && saveVersion != 0) {
      manager.getDb().update(saveVersion);
      TNELib.instance().getLogger().info("Saved data has been updated!");
    }
    Double version = (saveVersion != 0.0) ? saveVersion : TNELib.instance().currentSaveVersion;
    manager.getDb().load(version);
    TNELib.instance().getLogger().info("Finished loading data!");
  }

  public void save() {
    manager.getDb().save(TNELib.instance().currentSaveVersion);
    TNELib.instance().getLogger().info("Finished saving data!");
  }

  public DataManager getDataManager() {
    return manager;
  }
}