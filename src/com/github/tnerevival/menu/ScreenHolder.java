package com.github.tnerevival.menu;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.menu.icon.MenuIcon;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 7/27/2017.
 * All rights reserved.
 **/
public abstract class ScreenHolder {
  protected Map<String, MenuScreen> screens = new HashMap<>();
  protected Map<Integer, MenuIcon> icons = new HashMap<>();

  protected String title;
  protected String name;

  public ScreenHolder(String title, String name) {
    this.title = title;
    this.name = name;
  }


  public void onOpen(Player player) {
    UUID id = IDFinder.getID(player);
    TNELib.menuManager().addViewer(new MenuViewer(id, name, getMainMenu()));
  }

  public void onClose(Player player) {
    UUID id = IDFinder.getID(player);
    TNELib.menuManager().removeViewer(id);
  }

  public void onClick(int slot, Player player) {
    if (icons.containsKey(slot)) {
      icons.get(slot).onClick(player);
    }
  }

  public String getMainMenu() {
    String main = "";
    for(MenuScreen screen : screens.values()) {
      if(screen.isMain()) return screen.getName();
    }
    return "";
  }

  public Map<String, MenuScreen> getScreens() {
    return screens;
  }

  public Map<Integer, MenuIcon> getIcons() {
    return icons;
  }

  public String getTitle() {
    return title;
  }

  public String getName() {
    return name;
  }
}