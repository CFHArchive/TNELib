package com.github.tnerevival.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public class MenuManager {

  private Map<String, Menu> menus = new HashMap<>();
  private Map<UUID, ViewerData> data = new HashMap<>();

  public void removeData(UUID id) {
    data.remove(id);
  }

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

  public static ItemStack getBorder() {
    ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte)15);
    ItemMeta setMeta = item.getItemMeta();
    setMeta.setDisplayName(ChatColor.WHITE + "'member borders?");
    item.setItemMeta(setMeta);

    return item;
  }
}