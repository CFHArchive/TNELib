package com.github.tnerevival.menu.icon.impl;

import com.github.tnerevival.core.Message;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class MessageIcon extends PermissibleIcon {

  private String message;

  public MessageIcon(Integer slot, ItemStack stack, String screen, String message, String permission) {
    super(slot, stack, screen, permission);
    this.message = message;
  }

  @Override
  public boolean onClick(Player player) {
    Message m = new Message(message);
    m.translate(player.getWorld().getName(), player);
    return true;
  }
}