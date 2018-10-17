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

import com.github.tnerevival.core.SaveManager;
import com.github.tnerevival.core.UUIDManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.math.BigDecimal;
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
  public boolean debugMode = false;
  public boolean useUUID = true;
  public String consoleName = "Server Name";


  public BigDecimal currentSaveVersion = BigDecimal.ZERO;
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