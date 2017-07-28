package com.github.tnerevival.menu.icon.impl;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class ScreenIcon extends PermissibleIcon {
  public ScreenIcon(Integer slot, ItemStack stack, String permission, String screen) {
    super(slot, stack, permission);

  }

  @Override
  public boolean onClick(Player player) {
    return false;
  }
}