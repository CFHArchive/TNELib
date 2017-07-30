package com.github.tnerevival.menu.icon.impl;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.menu.MenuViewer;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class DataIcon extends PermissibleIcon {

  private String name;
  private Object value;

  public DataIcon(Integer slot, ItemStack stack, String screen, String permission, String name, Object value) {
    super(slot, stack, screen, permission);
    this.name = name;
    this.value = value;
  }

  @Override
  public boolean onClick(Player player) {
    UUID id = IDFinder.getID(player);
    MenuViewer viewer = TNELib.instance().menuManager().getViewer(id);
    viewer.setData(name, value);
    TNELib.instance().menuManager().addViewer(viewer);
    return true;
  }
}
