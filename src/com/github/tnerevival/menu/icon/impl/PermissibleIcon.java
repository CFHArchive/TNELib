package com.github.tnerevival.menu.icon.impl;

import com.github.tnerevival.menu.icon.MenuIcon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public abstract class PermissibleIcon extends MenuIcon {

  protected String permission;

  public PermissibleIcon(Integer slot, ItemStack stack, String screen, String permission) {
    super(slot, stack, screen);
    this.permission = permission;
  }

  public boolean onPopulateView(Player player) {
    return player.hasPermission(permission);
  }
}