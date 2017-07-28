package com.github.tnerevival.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

/**
 * Created by creatorfromhell on 7/27/2017.
 * All rights reserved.
 **/
public interface MenuScreen {
  String getName();
  boolean isMain();
  InventoryType getType();
  void onClick(Player player);
  Inventory getInventory();
}