/*
 * The New Economy Minecraft Server Plugin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.tnerevival;

import com.github.tnerevival.commands.CommandManager;
import com.github.tnerevival.commands.TNECommand;
import com.github.tnerevival.core.SaveManager;
import com.github.tnerevival.core.api.TNELibAPI;
import com.github.tnerevival.core.configurations.ConfigurationManager;
import com.github.tnerevival.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by creatorfromhell on 11/24/2016.
 **/
public class TNELib extends JavaPlugin {

  public List<UUID> special = new ArrayList<>();

  protected static TNELib instance;
  protected TNELibAPI api;
  protected SaveManager saveManager;



  public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S");
  public static final Pattern uuidCreator = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

  private static ConfigurationManager configurations;
  protected CommandManager commandManager;
  protected MenuManager menuManager;

  /*
   * DataProvider settings
   */
  public boolean debugMode = false;
  public boolean useUUID = true;
  public String consoleName = "Server Name";

  public Double currentSaveVersion = 0.0;
  public String defaultWorld = "Default";
  public static Map<String, UUID> uuidCache = new HashMap<>();

  public void onEnable() {
    instance = this;

    api = new TNELibAPI(this);

    configurations = new ConfigurationManager();
    commandManager = new CommandManager();

    if(Bukkit.getWorlds().size() >= 1) {
      defaultWorld = Bukkit.getServer().getWorlds().get(0).getName();
    } else {
      defaultWorld = "world";
    }
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public void registerCommand(String[] accessors, TNECommand command) {
    commandManager.commands.put(accessors, command);
    commandManager.registerCommands();
  }

  public void registerCommands(Map<String[], TNECommand> commands) {
    commandManager.commands = commands;
    commandManager.registerCommands();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
    TNECommand ecoCommand = commandManager.Find(label);
    if(ecoCommand != null) {
      if(!ecoCommand.canExecute(sender)) {
        sender.sendMessage(ChatColor.RED + "I'm sorry, but you're not allowed to use that commands.");
        return false;
      }
      return ecoCommand.execute(sender, label, arguments);
    }
    return false;
  }

  private void initializeConfigurations() {
    try {
      setConfigurationDefaults();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  public void loadConfigurations() {
    saveConfigurations(false);
  }

  private void saveConfigurations(boolean check) {
  }

  private void setConfigurationDefaults() throws UnsupportedEncodingException {
  }

  public static TNELib instance() {
    return instance;
  }

  public MenuManager menuManager() {
    return menuManager;
  }

  public TNELibAPI api() {
    return api;
  }

  public static ConfigurationManager configurations() {
    return configurations;
  }

  public SaveManager getSaveManager() {
    return saveManager;
  }

  public Map<String, UUID> getOffline() {
    return new HashMap<>();
  }

  public static void debug(String message) {
    if(TNELib.instance().debugMode) {
      TNELib.instance().getLogger().info("[DEBUG MODE]" + message);
    }
  }

  public static void debug(StackTraceElement[] stack) {
    for(StackTraceElement element : stack) {
      debug(element.toString());
    }
  }

  public static void debug(Exception e) {
    if(TNELib.instance().debugMode) {
      e.printStackTrace();
    }
  }
}