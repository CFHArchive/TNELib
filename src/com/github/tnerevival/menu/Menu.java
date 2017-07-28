package com.github.tnerevival.menu;

import org.bukkit.entity.Player;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class Menu extends ScreenHolder {
  public Menu(String title, String name) {
    super(title, name);
  }

  public void onClick(int slot, Player player) {
    if (icons.containsKey(slot)) {
      icons.get(slot).onClick(player);
    }
  }
}
