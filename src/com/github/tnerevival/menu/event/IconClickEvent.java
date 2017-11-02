package com.github.tnerevival.menu.event;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public class IconClickEvent {

  private Map<String, Object> data;

  private Player player;
  private String menu;
  private int slot;
  private String message;

  private boolean close = false;
  private boolean switchMenu = false;

  public IconClickEvent(Player player, String menu, int slot) {
    this(player, menu, slot, "", new HashMap<>());
  }

  public IconClickEvent(Player player, String menu, int slot, String message) {
    this(player, menu, slot, message, new HashMap<>());
  }

  public IconClickEvent(Player player, String menu, int slot, String message, Map<String, Object> data) {
    this.player = player;
    this.menu = menu;
    this.slot = slot;
    this.message = message;
    this.data = data;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void addData(String identifier, Object value) {
    data.put(identifier, value);
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public String getMenu() {
    return menu;
  }

  public void setMenu(String menu) {
    this.menu = menu;
  }

  public int getSlot() {
    return slot;
  }

  public void setSlot(int slot) {
    this.slot = slot;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isClose() {
    return close;
  }

  public void setClose(boolean close) {
    this.close = close;
  }

  public boolean isSwitchMenu() {
    return switchMenu;
  }

  public void setSwitchMenu(boolean switchMenu) {
    this.switchMenu = switchMenu;
  }
}