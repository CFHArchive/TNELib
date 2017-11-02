package com.github.tnerevival.menu;

import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public abstract class MenuManager {

  protected Map<UUID, ViewerData> data = new HashMap<>();
  protected List<String> switching = new ArrayList<>();

  public abstract ScreenHolder getHolder(UUID id);

  public abstract MenuScreen getScreen(UUID id);

  public abstract void addViewer(MenuViewer viewer);

  public abstract void removeViewer(UUID id);

  public abstract MenuViewer getViewer(UUID id);

  public abstract void addMenu(ScreenHolder menu);

  public abstract ScreenHolder getHolder(String name);

  public abstract Map<UUID, MenuViewer> getViewers();

  public Object getViewerData(UUID viewer, String identifier) {
    if(data.containsKey(viewer)) {
      return data.get(viewer).getValue(identifier);
    }
    return null;
  }

  public void setViewerData(UUID viewer, String identifier, Object value) {
    if(!data.containsKey(viewer)) {
      data.put(viewer, new ViewerData(viewer));
    }
    data.get(viewer).setValue(identifier, value);
  }

  public void addSwitching(String id) {
    switching.add(id);
    System.out.println("addSwitching: " + IDFinder.getUsername(id));
  }

  public boolean isSwitching(String id) {
    System.out.println("isSwitching: " + IDFinder.getUsername(id));
    return switching.contains(id);
  }

  public void removeSwitching(String id) {
    switching.remove(id);
    System.out.println("removeSwitching: " + IDFinder.getUsername(id));
  }

  public void switchScreens(Player player, String screen) {
    System.out.println("Player null: " + (player == null));
    System.out.println("Player: " + player);
    System.out.println("Screen null: " + (screen == null));
    System.out.println("Screen: " + screen);
    getHolder(IDFinder.getID(player)).getScreen(screen).open(player, true);
  }
}