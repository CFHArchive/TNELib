/*
 * The New Economy Minecraft Server Plugin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.version.Version;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by creatorfromhell on 1/29/2017.
 **/
public class SaveManager {

  private TreeMap<Double, Version> versions = new TreeMap<>();

  private Version versionInstance;
  private SQLManager sqlManager;
  private Double saveVersion = 0.0;
  private File file;

  public SaveManager(SQLManager sqlManager) {
    this.sqlManager = sqlManager;
  }

  public void addVersion(Version version) {
    addVersion(version, false);
  }

  public void addVersion(Version version, boolean current) {
    if(current) TNELib.instance.currentSaveVersion = version.versionNumber();
    versions.put(version.versionNumber(), version);
  }

  public void initialize() {
    versionInstance = versions.get(TNELib.instance.currentSaveVersion);
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      file = new File(sqlManager.getFlatfile());
    }
    if(versionInstance.firstRun()) {
      initiate();
      return;
    }
    saveVersion = versionInstance.getSaveVersion();
    TNELib.instance.getLogger().info("Save file of version: " + saveVersion + " detected.");
    load();
  }

  private void update() {
    int i = 0;
    for(Double version : versions.headMap(TNELib.instance.currentSaveVersion, false).keySet()) {
      if(i != 0) {
        versions.get(version).update(versions.lowerKey(version), TNELib.instance.saveFormat);
      }
      i++;
    }
    versionInstance.update(versions.lowerKey(TNELib.instance.currentSaveVersion), TNELib.instance.saveFormat);
  }

  private void initiate() {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      if(!file.exists()) {
        try {
          file.createNewFile();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      versionInstance.createTables(TNELib.instance.saveFormat);
    }
  }

  public void load() {
    if(saveVersion < versionInstance.versionNumber() && saveVersion != 0) {
      update();
      TNELib.instance.getLogger().info("Saved data has been updated!");
    }
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      loadFlatFile();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) {
      loadMySQL();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("sqlite")) {
      loadSQLite();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("h2")) {
      loadH2();
    }
    TNELib.instance.getLogger().info("Finished loading data!");
  }

  public void save() {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      saveFlatFile();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) {
      saveMySQL();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("sqlite")) {
      saveSQLite();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("h2")) {
      saveH2();
    }
    TNELib.instance.getLogger().info("Finished saving data!");
  }

  //Actual Save/Load Methods
  //FlatFile Methods
  private void loadFlatFile() {
    Version loadVersion = (saveVersion != 0.0) ? versions.get(saveVersion) : versionInstance;
    loadVersion.loadFlat(file);
  }

  private void saveFlatFile() {
    versionInstance.saveFlat(file);
  }

  //MySQL Methods
  private void loadMySQL() {
    Version loadVersion = (saveVersion != 0.0) ? versions.get(saveVersion) : versionInstance;
    loadVersion.loadMySQL();
  }

  private void saveMySQL() {
    versionInstance.saveMySQL();
  }

  //SQLite Methods
  private void loadSQLite() {
    Version loadVersion = (saveVersion != 0.0) ? versions.get(saveVersion) : versionInstance;
    loadVersion.loadSQLite();
  }

  private void saveSQLite() {
    versionInstance.saveSQLite();
  }

  private void loadH2() {
    Version loadVersion = (saveVersion != 0.0) ? versions.get(saveVersion) : versionInstance;
    loadVersion.loadH2();
  }

  private void saveH2() {
    versionInstance.saveH2();
  }
}