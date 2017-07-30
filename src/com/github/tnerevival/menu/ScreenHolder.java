package com.github.tnerevival.menu;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 7/27/2017.
 * All rights reserved.
 **/
public abstract class ScreenHolder extends IconHolder {
  protected Map<String, MenuScreen> screens = new HashMap<>();

  protected String title;
  protected String name;

  public ScreenHolder(String title, String name) {
    this.title = title;
    this.name = name;
  }


  public void onOpen(Player player) {
    UUID id = IDFinder.getID(player);
    player.openInventory(screens.get(getMainMenu()).getInventory());
    System.out.println("Inventory opened!");
    TNELib.instance().menuManager().addViewer(new MenuViewer(id, name, getMainMenu()));
    System.out.println("Adding menu viewer ID: " + id.toString() + " Total Viewers:" + TNELib.instance().menuManager().getViewers().size());
  }

  public void onClick(int slot, Player player) {
    UUID id = IDFinder.getID(player);
    TNELib.instance().menuManager().getScreen(id).onClick(slot, player);
  }

  public void onClose(Player player) {
    UUID id = IDFinder.getID(player);
    TNELib.instance().menuManager().removeViewer(id);
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

  public String getTitle() {
    return title;
  }

  public String getName() {
    return name;
  }
}