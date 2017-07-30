package com.github.tnerevival.menu.icon.impl;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class ScreenIcon extends PermissibleIcon {

  private String screen;

  public ScreenIcon(Integer slot, ItemStack stack, String parentScreen, String permission, String screen) {
    super(slot, stack, parentScreen, permission);
    this.screen = screen;
  }

  @Override
  public boolean onClick(Player player) {
    UUID id = IDFinder.getID(player);
    System.out.println("Screen icon for " + screen);
    TNELib.instance().menuManager().getHolder(id).getScreens().get(screen).open(player);
    return true;
  }
}