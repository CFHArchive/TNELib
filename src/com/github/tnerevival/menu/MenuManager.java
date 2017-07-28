package com.github.tnerevival.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class MenuManager {

  private Map<String, ScreenHolder> menus = new HashMap<>();
  private Map<UUID, MenuViewer> viewers = new HashMap<>();

  public ScreenHolder getHolder(UUID id) {
    if(viewers.containsKey(id)) {
      String holder = viewers.get(id).getScreenHolder();
      if(menus.containsKey(holder)) return menus.get(holder);
    }
    return null;
  }

  public MenuScreen getScreen(UUID id) {
    ScreenHolder holder = getHolder(id);
    if(holder != null) {
      String screen = viewers.get(id).getCurrentMenu();
      if(holder.getScreens().containsKey(screen)) return holder.getScreens().get(screen);
    }
    return null;
  }

  public void addViewer(MenuViewer viewer) {
    viewers.put(viewer.getId(), viewer);
  }

  public void removeViewer(UUID id) {
    viewers.remove(id);
  }

  public MenuViewer getViewer(UUID id) {
    return viewers.get(id);
  }
}