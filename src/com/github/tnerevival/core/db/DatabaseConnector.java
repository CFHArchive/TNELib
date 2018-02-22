package com.github.tnerevival.core.db;

import com.github.tnerevival.core.DataManager;

/**
 * Created by creatorfromhell on 8/29/2017.
 * All rights reserved.
 **/
public interface DatabaseConnector {
  Boolean connected(int id, DataManager manager);
  Connection connect(int id, DataManager manager);
  Object connection(int id, DataManager manager);
  void close(int id, DataManager manager);
}