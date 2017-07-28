package com.github.tnerevival.menu;

import java.util.UUID;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class MenuViewer {

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
}