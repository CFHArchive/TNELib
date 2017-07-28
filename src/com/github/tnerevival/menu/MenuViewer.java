package com.github.tnerevival.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class MenuViewer {

  private Map<String, Object> data = new HashMap<>();

  private UUID id;
  private String screenHolder;
  private String currentMenu;

  public MenuViewer(UUID id, String screenHolder, String currentMenu) {
    this.id = id;
    this.screenHolder = screenHolder;
    this.currentMenu = currentMenu;
  }

  public UUID getId() {
    return id;
  }

  public String getCurrentMenu() {
    return currentMenu;
  }

  public void setCurrentMenu(String currentMenu) {
    this.currentMenu = currentMenu;
  }

  public String getScreenHolder() {
    return screenHolder;
  }

  public void setScreenHolder(String screenHolder) {
    this.screenHolder = screenHolder;
  }

  public void removeData(String name) {
    data.remove(name);
  }

  public Object getData(String name) {
    return data.get(name);
  }

  public void setData(String name, Object value) {
    data.put(name, value);
  }

  public Map<String, Object> getData() {
    return data;
  }
}