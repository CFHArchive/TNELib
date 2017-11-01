package com.github.tnerevival.menu;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.menu.icon.impl.BorderIcon;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

/**
 * Created by creatorfromhell on 7/27/2017.
 * All rights reserved.
 **/
public abstract class MenuScreen extends IconHolder {
  public abstract String getName();
  public abstract boolean isMain();

  public void open(Player player, String holder) {
    System.out.println("=====START MenuScreen.open(player, holder) =====");
    UUID id = IDFinder.getID(player);
    System.out.println("Player: " + IDFinder.getUsername(id.toString()));
    System.out.println("Player: " + id.toString());
    System.out.println("Name null: " + (getName() == null));
    System.out.println("Inventory null: " + (getInventory() == null));
    System.out.println("MenuScreen.open: " + player.toString() + " Holder: " + holder);
    MenuViewer viewer = (TNELib.instance().menuManager().getViewer(id) != null)?
        TNELib.instance().menuManager().getViewer(id) :
        new MenuViewer(id, holder, getName());
    viewer.setCurrentMenu(getName());
    TNELib.instance().menuManager().addViewer(viewer);
    open(player, false);
    System.out.println("=====END MenuScreen.open(player, holder) =====");
  }

  public void open(Player player, boolean switching) {
    System.out.println("=====START MenuScreen.open(player, switching) =====");
    onOpen(player);
    UUID id = IDFinder.getID(player);
    System.out.println("Player: " + IDFinder.getUsername(id.toString()));
    System.out.println("Player: " + id.toString());
    System.out.println("Name null: " + (getName() == null));
    System.out.println("Inventory null: " + (getInventory() == null));
    TNELib.instance().menuManager().addSwitching(id);
    if(switching) {
      player.getOpenInventory().getTopInventory().setContents(getInventory().getContents());
    } else {
      player.openInventory(getInventory());
    }
    MenuViewer viewer = TNELib.instance().menuManager().getViewer(id);
    viewer.setCurrentMenu(getName());
    TNELib.instance().menuManager().addViewer(viewer);
    System.out.println("=====END MenuScreen.open(player, switching) =====");
  }

  public void open(Player player) {
    System.out.println("=====START MenuScreen.open(player) =====");
    open(player, false);
    System.out.println("=====END MenuScreen.open(player) =====");
  }

  public void onOpen(Player player) {
    System.out.println("=====START MenuScreen.onOpen(player) =====");
    UUID id = IDFinder.getID(player);
    System.out.println("Player: " + IDFinder.getUsername(id.toString()));
    System.out.println("Player: " + id.toString());
    System.out.println("Name null: " + (getName() == null));
    System.out.println("Name: " + getName());
    System.out.println("Inventory null: " + (getInventory() == null));
    for(int i = 0; i < getInventory().getSize(); i++) {
      if(getInventory().getItem(i) == null) {
        BorderIcon icon = new BorderIcon(i, getName());
        getInventory().setItem(i, icon.getStack());
      }
    }
    System.out.println("=====END MenuScreen.onOpen(player) =====");
  }

  public abstract Inventory getInventory();
}