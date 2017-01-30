package com.github.tnerevival.core;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.version.Version;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class SaveManager {

  public HashMap<Double, Version> versions = new HashMap<>();

  public Version versionInstance;
  Double saveVersion = 0.0;
  File file;

  public SaveManager() {
  }

  public void initialize() {
    versionInstance = versions.get(TNELib.instance.currentSaveVersion);
    if(firstRun()) {
      initiate();
      return;
    }
    getVersion();
    TNELib.instance.getLogger().info("Save file of version: " + saveVersion + " detected.");
    load();
  }

  public void recreate() {
    if(!TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      versionInstance.createTables(TNELib.instance.saveFormat);
    }
  }

  private Boolean firstRun() {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      return !file.exists();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) {
      Connection connection;
      PreparedStatement statement;
      ResultSet result;
      String table = versionInstance.prefix() + "_INFO";
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + versionInstance.mysqlHost() + ":" + versionInstance.mysqlPort() + "/" + versionInstance.mysqlDatabase(), versionInstance.mysqlUser(), versionInstance.mysqlPassword());


        result = connection.getMetaData().getTables(null, null, table, null);

        return !result.next();
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("sqlite")) {
      File sqliteDB = new File(versionInstance.sqliteFile());
      if(!sqliteDB.exists()) {
        return true;
      }
      Connection connection;
      PreparedStatement statement;
      ResultSet result;
      String table = versionInstance.prefix() + "_INFO";
      try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + versionInstance.sqliteFile());
        statement = connection.prepareStatement("SELECT * FROM sqlite_master WHERE TNELib.instance.saveFormat='table' AND name = ?;");
        statement.setString(1, table);
        result = statement.executeQuery();
        Boolean toReturn = result.next();
        connection.close();
        return !toReturn;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("h2")) {
      File h2DB = new File(versionInstance.h2File());
      if(!h2DB.exists()) {
        return true;
      }
      Connection connection;
      PreparedStatement statement;
      ResultSet result;
      String table = versionInstance.prefix() + "_INFO";
      try {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:" + versionInstance.h2File() + ";mode=MySQL", versionInstance.mysqlUser(), versionInstance.mysqlPassword());
        result = connection.getMetaData().getTables(null, null, table, null);
        return result.next();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return !file.exists();
  }

  private void initiate() {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      try {
        TNELib.instance.getDataFolder().mkdir();
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) {
      versionInstance.createTables("mysql");
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("sqlite") || TNELib.instance.saveFormat.equalsIgnoreCase("h2")) {
      versionInstance.createTables("h2");
    }
  }

  private void getVersion() {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      try {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        saveVersion = ois.readDouble();
        ois.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) {
      Connection connection;
      Statement statement;
      ResultSet result;
      String table = versionInstance.prefix() + "_INFO";
      try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://" + versionInstance.mysqlHost() + ":" + versionInstance.mysqlPort() + "/" + versionInstance.mysqlDatabase(), versionInstance.mysqlUser(), versionInstance.mysqlPassword());
        statement = connection.createStatement();
        result = statement.executeQuery("SELECT version FROM " + table + " WHERE id = 1;");
        if(result.first()) {
          saveVersion = Double.valueOf(result.getString("version"));
        }
        connection.close();
      } catch(Exception e) {
        e.printStackTrace();
      }
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("sqlite")) {
      Connection connection;
      Statement statement;
      ResultSet result;
      String table = versionInstance.prefix() + "_INFO";
      try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + versionInstance.sqliteFile());
        statement = connection.createStatement();
        result = statement.executeQuery("SELECT version FROM " + table + " WHERE id = 1;");
        if(result.next()) {
          saveVersion = Double.valueOf(result.getString("version"));
        }
        connection.close();
      } catch(Exception e) {
        e.printStackTrace();
      }
    } else if (TNELib.instance.saveFormat.equalsIgnoreCase("h2")) {
      Connection connection;
      Statement statement;
      ResultSet result;
      String table = versionInstance.prefix() + "_INFO";
      try {
        Class.forName("org.h2.Driver");
        connection = DriverManager.getConnection("jdbc:h2:" + versionInstance.h2File() + ";mode=MySQL", versionInstance.mysqlUser(), versionInstance.mysqlPassword());
        statement = connection.createStatement();
        result = statement.executeQuery("SELECT version FROM " + table + " WHERE id = 1;");
        if(result.next()) {
          saveVersion = Double.valueOf(result.getString("version"));
        }
        connection.close();
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
  }

  public void load() {
    if(saveVersion < versionInstance.versionNumber() && saveVersion != 0) {
      versionInstance.update(saveVersion, TNELib.instance.saveFormat.toLowerCase());
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
  }

  public void save() {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) {
      saveFlatFile();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) {
      saveMySQL();
    } else if(TNELib.instance.saveFormat.equalsIgnoreCase("h2") || TNELib.instance.saveFormat.equalsIgnoreCase("sqlite")) {
      saveH2();
    }
    TNELib.instance.getLogger().info("Data saved!");
  }

  public Boolean backupDatabase() throws IOException {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("mysql")) return false;

    String db = (TNELib.instance.saveFormat.equalsIgnoreCase("flatfile")) ? versionInstance.ffFile() : versionInstance.h2File();
    FileInputStream fileIn = new FileInputStream(new File(TNELib.instance.getDataFolder(), db));
    FileOutputStream fileOut = new FileOutputStream(new File(TNELib.instance.getDataFolder(), "Database.zip"));
    ZipOutputStream zipOut = new ZipOutputStream(fileOut);
    ZipEntry entry = new ZipEntry(db);
    zipOut.putNextEntry(entry);
    byte[] buffer = new byte[1024];
    int length;
    while ((length = fileIn.read(buffer)) > 0) {
      zipOut.write(buffer, 0, length);
    }
    fileIn.close();
    zipOut.closeEntry();
    zipOut.close();
    return true;
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