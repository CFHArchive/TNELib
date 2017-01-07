package com.github.tnerevival.core.configurations;

import com.github.tnerevival.TNELib;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;


public abstract class Configuration {

  public abstract FileConfiguration getConfiguration();

  public abstract String node();

  public static HashMap<String, Object> configurations = new HashMap<>();

  public void load(FileConfiguration configurationFile) {
    Iterator<java.util.Map.Entry<String, Object>> it = configurations.entrySet().iterator();

    while(it.hasNext()) {
      java.util.Map.Entry<String, Object> entry = it.next();
      if(configurationFile.contains(entry.getKey())) {
        setValue(entry.getKey(), configurationFile.get(entry.getKey()));
      }
    }
  }

  public void save(FileConfiguration configurationFile) {
    if(!new File(TNELib.instance.getDataFolder(), configurationFile.getName()).exists() || TNELib.configurations.changed.contains(configurationFile.getName())) {
      Iterator<java.util.Map.Entry<String, Object>> it = configurations.entrySet().iterator();

      while (it.hasNext()) {
        java.util.Map.Entry<String, Object> entry = it.next();
        if (configurationFile.contains(entry.getKey())) {
          configurationFile.set(entry.getKey(), entry.getValue());
        }
      }
    }
  }

  public Boolean hasNode(String node) {
    return configurations.get(node) != null;
  }

  public Object getValue(String node) {
    return configurations.get(node);
  }

  public void setValue(String node, Object value) {
    configurations.put(node, value);
  }

}