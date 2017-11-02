package com.github.tnerevival.menu;

import org.bukkit.entity.Player;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class Menu extends ScreenHolder {
  public Menu(String title, String name, String main) {
    super(title, name, main);
  }

  @Override
  public void onClick(int slot, Player player) {
    super.onClick(slot, player);
  }
}
