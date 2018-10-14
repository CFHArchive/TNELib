package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public interface DatabaseConnector {
  String getDriver();
  Boolean dataSource();
  String dataSourceURL();
  String getURL(String file, String host, int port, String database);
  Boolean connected(DataManager manager);
  void connect(DataManager manager) throws Exception;
  Object connection(DataManager manager);
}