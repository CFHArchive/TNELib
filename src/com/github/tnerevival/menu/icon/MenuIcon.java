package com.github.tnerevival.menu.icon;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public abstract class MenuIcon {

  protected Integer slot;
  protected ItemStack stack;
  protected String screen;

  public MenuIcon(Integer slot, ItemStack stack, String screen) {
    this.slot = slot;
    this.stack = stack;
    this.screen = screen;
  }

  public abstract boolean onClick(Player player);

  public Integer getSlot() {
    return slot;
  }

  public ItemStack getStack() {
    return stack;
  }

  public String getScreen() {
    return screen;
  }
}