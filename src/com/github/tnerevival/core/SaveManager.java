package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.version.Version;

import java.io.File;
import java.util.TreeMap;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public class SaveManager {

  private TreeMap<Double, Version> versions = new TreeMap<>();

  private Version versionInstance;
  private DataManager manager;
  private Double saveVersion = 0.0;
  private File file;

  public SaveManager(DataManager manager) {
    this.manager = manager;
  }

  public void addVersion(Version version) {
    addVersion(version, false);
  }

  public void addVersion(Version version, boolean current) {
    if(current) TNELib.instance().currentSaveVersion = version.versionNumber();
    versions.put(version.versionNumber(), version);
  }

  public void initialize() {
    versionInstance = versions.get(TNELib.instance().currentSaveVersion);
    if(manager.getDb().first()) {
      manager.getDb().initialize();
      return;
    }
    saveVersion = manager.getDb().version();
    TNELib.instance().getLogger().info("Save file of version: " + saveVersion + " detected.");
    load();
  }

  public void load() {
    if(saveVersion < versionInstance.versionNumber() && saveVersion != 0) {
      manager.getDb().update(saveVersion);
      TNELib.instance().getLogger().info("Saved data has been updated!");
    }
    Version loadVersion = (saveVersion != 0.0) ? versions.get(saveVersion) : versionInstance;
    manager.getDb().load(loadVersion.versionNumber());
    TNELib.instance().getLogger().info("Finished loading data!");
  }

  public void save() {
    Version loadVersion = (saveVersion != 0.0) ? versions.get(saveVersion) : versionInstance;
    manager.getDb().save(loadVersion.versionNumber());
    TNELib.instance().getLogger().info("Finished saving data!");
  }

  public DataManager getDataManager() {
    return manager;
  }
}