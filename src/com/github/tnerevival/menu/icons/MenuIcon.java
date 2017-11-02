package com.github.tnerevival.menu.icons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public abstract class MenuIcon {

  private String label;
  private Material material;
  private short data;

  public MenuIcon(String label, Material material) {
    this(label, material, (short)0);
  }

  public MenuIcon(String label, Material material, short data) {
    this.label = label;
    this.material = material;
    this.data = data;
  }

  public abstract String getSwitch();
  public abstract boolean onClick(Player player);
  public abstract boolean willClose(Player player);
  public abstract boolean willDestroy(Player player);
  public abstract boolean willSwitch(Player player);

  public String getLabel() {
    return label;
  }

  public Material getMaterial() {
    return material;
  }

  public int getData() {
    return data;
  }

  public ItemStack getStack() {
    ItemStack stack = new ItemStack(material, 1, data);
    ItemMeta meta = stack.getItemMeta();
    meta.setDisplayName(ChatColor.WHITE + label);
    stack.setItemMeta(meta);

    return stack;
  }
}