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
import com.github.tnerevival.core.db.*;

/**
 * Created by creatorfromhell on 1/16/2017.
 **/
public class SQLManager {
  private String mysqlHost;
  private Integer mysqlPort;
  private String mysqlDatabase;
  private String mysqlUser;
  private String mysqlPassword;
  private String prefix;
  private String flatfile;
  private String h2File;
  private String sqliteFile;

  private Database db;

  public SQLManager(String mysqlHost, Integer mysqlPort, String mysqlDatabase, String mysqlUser, String mysqlPassword, String prefix, String h2File, String sqliteFile, String flatfile) {
    this.mysqlHost = mysqlHost;
    this.mysqlPort = mysqlPort;
    this.mysqlDatabase = mysqlDatabase;
    this.mysqlUser = mysqlUser;
    this.mysqlPassword = mysqlPassword;
    this.prefix = prefix;
    this.flatfile = flatfile;
    this.h2File = h2File;
    this.sqliteFile = sqliteFile;
  }

  private void createDB() {
    createDB(TNELib.instance.saveFormat);
  }

  private void createDB(String type) {
    switch(type) {
      case "mysql":
        db = new MySQL(mysqlHost, mysqlPort, mysqlDatabase, mysqlUser, mysqlPassword);
        break;
      case "h2":
        db = new H2(h2File, mysqlUser, mysqlPassword);
        break;
      case "sqlite":
        db = new SQLite(sqliteFile);
        break;
      default:
        db = new FlatFile(flatfile);
    }
  }

  public String getPrefix() {
    return prefix;
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
}