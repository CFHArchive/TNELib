package com.github.tnerevival.menu;

import com.github.tnerevival.menu.icon.MenuIcon;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public class IconHolder {
  protected Map<Integer, MenuIcon> icons = new HashMap<>();

  public void onClick(int slot, Player player) {
    if (icons.containsKey(slot)) {
      icons.get(slot).onClick(player);
    }
  }

  public Map<Integer, MenuIcon> getIcons() {
    return icons;
  }

  public void addIcon(MenuIcon icon) {
    icons.put(icon.getSlot(), icon);
  }

  public void removeIcon(Integer slot) {
    icons.remove(slot);
  }
}