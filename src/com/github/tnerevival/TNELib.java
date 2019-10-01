/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival;

import com.github.tnerevival.core.SaveManager;
import com.github.tnerevival.core.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by creatorfromhell on 11/24/2016.
 **/
public class TNELib extends JavaPlugin {

  public List<UUID> special = new ArrayList<>();

  protected static TNELib instance;
  private SaveManager saveManager;
  private UUIDManager uuidManager;

  public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss.S");
  public static final Pattern uuidCreator = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

  /*
   * DataProvider settings
   */
  protected boolean datasource = false;
  public boolean debugMode = false;
  public boolean useUUID = true;
  public String consoleName = "Server Name";
  protected String poolName = "TNE_ConnectionPool";


  public Double currentSaveVersion = 0.0;
  public String defaultWorld = "Default";

  //UUID Cache variables
  protected Map<String, UUID> uuidCache = new HashMap<>();
  public String townPrefix = "town-";
  public String nationPrefix = "nation-";
  public String factionPrefix = "faction-";

  public void onEnable() {
    instance = this;


    if(Bukkit.getWorlds().size() >= 1) {
      defaultWorld = Bukkit.getServer().getWorlds().get(0).getName();
    } else {
      defaultWorld = "world";
    }
  }

  public static TNELib instance() {
    return instance;
  }

  public SaveManager getSaveManager() {
    return saveManager;
  }

  public void setSaveManager(SaveManager manager) {
    this.saveManager = manager;
  }

  public UUIDManager getUuidManager() {
    return uuidManager;
  }

  public void setUuidManager(UUIDManager manager) {
    this.uuidManager = manager;
  }

  public static boolean useDataSource() {
    return instance().datasource;
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

  public String poolName() {
    return poolName;
  }
}