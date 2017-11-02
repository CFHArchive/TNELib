package com.github.tnerevival.menu.icons;

import com.github.tnerevival.menu.event.IconClickHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public class MenuIcon {

  private int slot;
  private String label;
  private Material material;
  private IconClickHandler handler;
  private short data;
  private List<String> lore;

  public MenuIcon(int slot, String label, Material material, IconClickHandler handler) {
    this(slot, label, material, handler, (short)0, new ArrayList<>());
  }

  public MenuIcon(int slot, String label, Material material, IconClickHandler handler, short data) {
    this(slot, label, material, handler, data, new ArrayList<>());
  }

  public MenuIcon(int slot, String label, Material material, IconClickHandler handler, short data, List<String> lore) {
    this.slot = slot;
    this.label = label;
    this.material = material;
    this.handler = handler;
    this.data = data;
    this.lore = lore;
  }
  public String switchMenu() {
    return "";
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public void setMaterial(Material material) {
    this.material = material;
  }

  public void setHandler(IconClickHandler handler) {
    this.handler = handler;
  }

  public void setData(short data) {
    this.data = data;
  }

  public void setLore(List<String> lore) {
    this.lore = lore;
  }

  public int getSlot() {
    return slot;
  }

  public String getLabel() {
    return label;
  }

  public Material getMaterial() {
    return material;
  }

  public IconClickHandler getHandler() {
    return handler;
  }

  public int getData() {
    return data;
  }

  public ItemStack getStack() {
    ItemStack stack = new ItemStack(material, 1, data);
    ItemMeta meta = stack.getItemMeta();
    meta.setDisplayName(ChatColor.WHITE + label);
    meta.setLore(lore);
    stack.setItemMeta(meta);

    return stack;
  }
}