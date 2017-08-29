package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public interface DatabaseConnector {
  Boolean connected(DataManager manager);
  void connect(DataManager manager);
  Object connection(DataManager manager);
  void close(DataManager manager);
}