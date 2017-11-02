package com.github.tnerevival.menu;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public class MenuManager {

  private Map<String, Menu> menus = new HashMap<>();

  public void addMenu(Menu menu) {
    menus.put(menu.getName(), menu);
  }

  public Menu fromName(String name) {
    return menus.get(name);
  }

  public boolean exists(String title) {
    for(Menu menu : menus.values()) {
      if(menu.getTitle().equalsIgnoreCase(title)) return true;
    }
    return false;
  }

  public Menu fromTitle(String title) {
    for(Menu menu : menus.values()) {
      if(menu.getTitle().equalsIgnoreCase(title)) return menu;
    }
    return null;
  }
}