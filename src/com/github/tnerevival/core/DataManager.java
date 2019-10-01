/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core;

import com.github.tnerevival.core.db.DataProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 1/16/2017.
 **/
public class DataManager {
  protected Map<String, DataProvider> providers = new HashMap<>();

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
  protected boolean ssl = false;

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

  public Map<String, DataProvider> getProviders() {
    return providers;
  }

  public boolean registerProvider(Class<? extends DataProvider> provider) {
    DataProvider instance = null;
    try {
      instance = provider.getDeclaredConstructor(DataManager.class).newInstance(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if(instance == null) return false;
    this.providers.put(instance.identifier(), instance);
    return true;
  }

  public void registerProviders(HashMap<String, Class<? extends DataProvider>> providers) {
    for(Class<? extends DataProvider> provider : providers.values()) {
      registerProvider(provider);
    }
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

  public boolean isSsl() {
    return ssl;
  }

  public void setSsl(boolean ssl) {
    this.ssl = ssl;
  }

  public DataProvider getDb() {
    return providers.get(format);
  }
}