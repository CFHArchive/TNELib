package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public interface DatabaseConnector {
  String getDriver();
  Boolean dataSource();
  String dataSourceURL();
  String getURL(String file, String host, int port, String database);
  void initialize(DataManager manager);

  default Map<String, Object> hikariProperties() {
    return new HashMap<>();
  }
}