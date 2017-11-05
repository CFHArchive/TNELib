package com.github.tnerevival.menu;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.menu.event.IconClickEvent;
import com.github.tnerevival.menu.icons.MenuIcon;
import com.github.tnerevival.user.IDFinder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public class Menu implements Listener {

  protected Map<Integer, MenuIcon> icons = new HashMap<>();
  protected Map<Integer, MenuIcon> dummyIcons = new HashMap<>();

  protected String name;
  protected String title;
  protected int size;
  protected boolean border;
  protected Plugin plugin;

  public Menu(String name, String title, int size, Plugin plugin) {
    this(name, title, size, plugin, true);
  }

  public Menu(String name, String title, int size, Plugin plugin, boolean border) {
    this.name = name;
    this.title = title;
    this.size = size;
    this.border = border;
    this.plugin = plugin;
  }

  public String getName() {
    return name;
  }

  public String getTitle() {
    return title;
  }

  public int getSize() {
    return size;
  }

  public Plugin getPlugin() {
    return plugin;
  }

  public Menu addIcon(MenuIcon icon) {
    icons.put(icon.getSlot(), icon);
    return this;
  }

  public Menu addIconBulk(Map<Integer, MenuIcon> bulk) {
    for(MenuIcon icon : bulk.values()) {
      System.out.println("Adding icon....");
      icons.put(icon.getSlot(), icon);
    }
    return this;
  }

  public Menu addDummyIcon(MenuIcon icon) {
    dummyIcons.put(icon.getSlot(), icon);
    return this;
  }

  public Menu addDummyBulk(Map<Integer, MenuIcon> bulk) {
    dummyIcons.putAll(bulk);
    return this;
  }

  public void open(Player player) {
    if(onOpen(player)) {
      System.out.println("OPEN  Menu: " + getName());
      player.openInventory(getInventory());
      plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
  }

  public void close(Player player) {
    onClose(player);
    HandlerList.unregisterAll(this);
  }

  public void onClose(Player player) {

  }
  public boolean onOpen(Player player) {
    return true;
  }

  public Inventory getInventory() {
    Inventory inventory = Bukkit.createInventory(null, size, title);
    icons.forEach((key, value)->{
      inventory.setItem(key, value.getStack());
    });
    dummyIcons.forEach((key, value)->{
      inventory.setItem(key, value.getStack());
    });
    if(border) {
      for(int i = 0; i < inventory.getSize(); i++) {
        if(inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
          inventory.setItem(i, MenuManager.getBorder());
        }
      }
    }
    return inventory;
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onInventoryClick(InventoryClickEvent event) {
    if(event.getInventory().getTitle().equalsIgnoreCase(getTitle())) {
      event.setCancelled(true);
      Player player = (Player)event.getWhoClicked();
      UUID id = IDFinder.getID(player);
      int slot = event.getRawSlot();
      if(slot >= 0 && slot < size && icons.containsKey(slot)) {
        final Plugin plugin = this.plugin;
        IconClickEvent ev = new IconClickEvent(player, getName(), slot);
        MenuIcon icon = icons.get(slot);
        icon.getHandler().onIconClick(ev);
        if(ev.getData().size() > 0) {
          ev.getData().forEach((identifier, value)->TNELib.instance().menuManager().setViewerData(id, identifier, value));
        }
        if(ev.isClose()) {
          final Player p = (Player)event.getWhoClicked();
          Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, ()->{
            close(player);
            TNELib.instance().menuManager().removeData(id);
            player.closeInventory();
          }, 1L);
        }
        if(ev.isClose()) {
          if(!ev.getMessage().trim().equalsIgnoreCase("")) {
            player.sendMessage(ev.getMessage());
          }
          close(player);
        }

        if(ev.isSwitchMenu()) {
          Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, ()->{
            String menu = icons.get(slot).switchMenu();
            System.out.println("Menu: " + menu);
            close(player);
            player.closeInventory();
            TNELib.instance().menuManager().switchMenus(menu, player, plugin);
          }, 1L);
        }
      }
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onInventoryClose(InventoryCloseEvent event) {
    if(event.getInventory().getTitle().equalsIgnoreCase(getTitle())) {
      Player player = (Player)event.getPlayer();
      close(player);
    }
  }

  public Menu copy() {
    return new Menu(name, title, size, plugin, border)
                .addIconBulk(icons)
                .addDummyBulk(dummyIcons);
  }

  public Menu copy(String newName) {
    return new Menu(newName, title, size, plugin, border)
        .addIconBulk(icons)
        .addDummyBulk(dummyIcons);
  }

  public Menu copy(String newName, boolean isTitle) {
    if(isTitle) {
      return new Menu(name, newName, size, plugin, border)
          .addIconBulk(icons)
          .addDummyBulk(dummyIcons);
    }
    return new Menu(newName, title, size, plugin, border)
        .addIconBulk(icons)
        .addDummyBulk(dummyIcons);
  }
}