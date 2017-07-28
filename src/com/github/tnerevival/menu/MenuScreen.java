package com.github.tnerevival.menu;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/**
 * Created by creatorfromhell on 7/27/2017.
 * All rights reserved.
 **/
public abstract class MenuScreen {
  public abstract String getName();
  public abstract boolean isMain();
  public abstract InventoryType getType();

  public void open(Player player) {
    UUID id = IDFinder.getID(player);

    player.openInventory(getInventory());
    MenuViewer viewer = TNELib.menuManager().getViewer(id);
    viewer.setCurrentMenu(getName());
    TNELib.menuManager().addViewer(viewer);
  }

  public void onOpen(Player player) {

  }

  public void onClick(Player player, Integer slot) {

  }
  public abstract Inventory getInventory();
}