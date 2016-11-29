package com.github.tnerevival.core.configurations;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class ConfigurationManager {

  public boolean save = false;

  public HashMap<String, Configuration> configurations = new HashMap<>();

  public ConfigurationManager() {
    loadAll();
  }

  public void loadAll() {
  }

  private FileConfiguration getFileConfiguration(String id) {
    return configurations.get(id).getConfiguration();
  }

  private Configuration getConfiguration(String id) {
    return configurations.get(id);
  }

  public void load(FileConfiguration configurationFile, String configID) {
    getConfiguration(configID).load(configurationFile);
  }

  public void save(FileConfiguration configurationFile, String configID) {
    getConfiguration(configID).save(configurationFile);
  }

  public Object getValue(String node, String configuration) {
    return getConfiguration(configuration).getValue(node);
  }

  public void setValue(String node, String configuration, Object value) {
    getConfiguration(configuration).setValue(node, value);
    getConfiguration(configuration).save(getFileConfiguration(configuration));
  }

  public Boolean hasNode(String node, String configuration) {
    return getConfiguration(configuration).hasNode(node);
  }

  public Boolean getBoolean(String node) {
    return getBoolean(node, "main");
  }

  public Integer getInt(String node) {
    return getInt(node, "main");
  }

  public Double getDouble(String node) {
    return getDouble(node, "main");
  }

  public Long getLong(String node) {
    return getLong(node, "main");
  }

  public String getString(String node) {
    return getString(node, "main");
  }

  public Boolean getBoolean(String node, String configID) {
    return (Boolean)getConfiguration(configID).getValue(node);
  }

  public Integer getInt(String node, String configID) {
    return (Integer)getValue(node, configID);
  }

  public Double getDouble(String node, String configID) {
    return (Double)getValue(node, configID);
  }

  public Long getLong(String node, String configID) {
    return Long.valueOf(getInt(node, configID));
  }

  public String getString(String node, String configID) {
    return (String)getValue(node, configID);
  }

  /*
   * Helper methods for configurations.
   */
  public Object getConfiguration(String configuration, String world, String player) {
    String[] exploded = configuration.split("\\.");
    String path = configuration;
    String prefix = "Core";
    if(ConfigurationType.fromPrefix(exploded[0]) != ConfigurationType.UNKNOWN) {
      prefix = exploded[0];
      if(ConfigurationType.fromPrefix(prefix) == ConfigurationType.MAIN) {
        path = path.replace(prefix + ".", "");
      }
    }
    return getValue(configuration, ConfigurationType.fromPrefix(prefix).getIdentifier());
  }

  public void setConfiguration(String configuration, Object value) {
    String[] exploded = configuration.split("\\.");
    String prefix = "Core";
    if(ConfigurationType.fromPrefix(exploded[0]) != ConfigurationType.UNKNOWN) {
      prefix = exploded[0];
    }
    setValue(configuration, ConfigurationType.fromPrefix(prefix).getIdentifier(), value);
  }

  public Boolean hasConfiguration(String configuration) {
    String[] exploded = configuration.split("\\.");
    String prefix = "Core";
    if(ConfigurationType.fromPrefix(exploded[0]) != ConfigurationType.UNKNOWN) {
      prefix = exploded[0];
    }
    return hasNode(configuration, ConfigurationType.fromPrefix(prefix).getIdentifier());
  }
}