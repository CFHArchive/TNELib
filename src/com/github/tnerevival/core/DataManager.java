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

import com.github.tnerevival.core.db.Database;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 1/16/2017.
 **/
public class DataManager {
  protected Map<String, Database> providers = new HashMap<>();

  protected String format;
  protected String host;
  protected Integer port;
  protected String database;
  protected String user;
  protected String password;
  protected String prefix;
  protected String file;
  protected boolean directSave = false;
  protected boolean cacheData = false;
  protected Integer update = 600;
  protected boolean compress = true;

  public DataManager(String format, String host, Integer port, String database, String user, String password, String prefix, String file, boolean directSave, boolean cacheData, Integer update, boolean compress) {
    this.format = format;
    this.host = host;
    this.port = port;
    this.database = database;
    this.user = user;
    this.password = password;
    this.prefix = prefix;
    this.file = file;
    this.directSave = directSave;
    this.cacheData = cacheData;
    this.update = update;
    this.compress = compress;
  }

  public Map<String, Database> getProviders() {
    return providers;
  }

  public boolean registerProvider(String identifier, Class<? extends Database> provider) {
    Database instance = null;
    try {
      instance = provider.getDeclaredConstructor(DataManager.class).newInstance(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if(instance == null) return false;
    this.providers.put(identifier, instance);
    return true;
  }

  public String getHost() {
    return host;
  }

  public Integer getPort() {
    return port;
  }

  public String getDatabase() {
    return database;
  }

  public String getUser() {
    return user;
  }

  public String getPassword() {
    return password;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getFormat() {
    return format;
  }

  public boolean isDirectSave() {
    return directSave;
  }

  public boolean isCacheData() {
    return cacheData;
  }

  public Integer getUpdate() {
    return update;
  }

  public String getFile() {
    return file;
  }

  public boolean isCompress() {
    return compress;
  }

  public Database getDb() {
    return providers.get(format);
  }
}