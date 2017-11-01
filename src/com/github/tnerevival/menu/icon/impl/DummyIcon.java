package com.github.tnerevival.menu.icon.impl;

import com.github.tnerevival.menu.icon.MenuIcon;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by creatorfromhell on 10/29/2017.
 * All rights reserved.
 **/
public class DummyIcon extends MenuIcon {

  public DummyIcon(Integer slot, ItemStack stack, String screen) {
    super(slot, stack, screen);
  }

  @Override
  public boolean onClick(Player player) {
    return false;
  }
}