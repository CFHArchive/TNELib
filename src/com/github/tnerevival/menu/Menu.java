package com.github.tnerevival.menu;

import com.github.tnerevival.menu.icons.MenuIcon;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public abstract class Menu {

  private Map<Integer, MenuIcon> icons = new HashMap<>();

  private String name;
  private String title;
  private int size;
  private Plugin plugin;

  public Menu(String name, String title, int size, Plugin plugin) {
    this.name = name;
    this.title = title;
    this.size = size;
    this.plugin = plugin;
  }

  public String getName() {
    return name;
  }

  public String getTitle() {
    return title;
  }

  public int getSize() {
    return size;
  }

  public Plugin getPlugin() {
    return plugin;
  }

  public Inventory getInventory() {
    Inventory inventory = Bukkit.createInventory(null, size, title);
    icons.forEach((key, value)->{
      inventory.setItem(key, value.getStack());
    });
    return inventory;
  }
}