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
  private Map<String, MenuScreen> screens = new HashMap<>();

  protected String title;
  protected String name;
  protected String main;

  public ScreenHolder(String title, String name, String main) {
    this.title = title;
    this.name = name;
    this.main = main;
  }


  public void onOpen(Player player) {
    UUID id = IDFinder.getID(player);
    getScreen(main).open(player, name);
    System.out.println("Adding menu viewer ID: " + id.toString() + " Total Viewers:" + TNELib.instance().menuManager().getViewers().size());
  }

  public void onClick(int slot, Player player) {
    System.out.println("ScreenHolder > OnClick");
    UUID id = IDFinder.getID(player);
    TNELib.instance().menuManager().getScreen(id).onClick(slot, player);
  }

  public void onClose(Player player) {
    UUID id = IDFinder.getID(player);
    TNELib.instance().menuManager().removeViewer(id);
  }

  public String getMainMenu() {
    return main;
  }

  public MenuScreen getScreen(String name) {
    return screens.get(name).copy();
  }

  public void addScreen(MenuScreen screen) {
    screens.put(screen.getName(), screen);
  }

  public String getTitle() {
    return title;
  }

  public String getName() {
    return name;
  }
}