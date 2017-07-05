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
package com.github.tnerevival.user;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.core.api.MojangAPI;
import com.github.tnerevival.core.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by creatorfromhell on 11/6/2016.
 **/
public class IDFinder {

  public static UUID ecoID(String username) {
    return ecoID(username, false);
  }

  public static UUID ecoID(String username, boolean skip) {
    for(String s : TNELib.instance().offlineIDS.keySet()) {
      if(s.equalsIgnoreCase(username)) {
        return TNELib.instance().offlineIDS.get(s);
      }
    }
    UUID eco = (skip)? genUUID() : genUUID(username);
    TNELib.instance().offlineIDS.put(username, eco);
    return eco;
  }

  public static String getUsername(String identifier) {
    if(isUUID(identifier)) {
      if(getPlayer(identifier) == null) {
        if(getOffline(identifier) != null) {
          return getOffline(identifier).getName();
        }
        return MojangAPI.getPlayerUsername(getID(identifier));
      }
      return getPlayer(identifier).getName();
    }
    return identifier;
  }

  public static UUID genUUID(String name) {
    UUID id = MojangAPI.getPlayerUUID(name);
    if(id != null) return id;

    return genUUID();
  }

  public static UUID genUUID() {
    UUID id = UUID.randomUUID();
    while(TNELib.instance().offlineIDS.containsValue(id)) {
      //This should never happen, but we'll play it safe
      id = UUID.randomUUID();
    }
    return id;
  }

  public static String ecoToUsername(UUID id) {
    return (Utilities.getKey(TNELib.instance().offlineIDS, id) != null)? (String)Utilities.getKey(TNELib.instance().offlineIDS, id) : getUsername(id.toString());
  }

  public static UUID getID(Player player) {
    return getID(player.getName());
  }

  public static UUID getID(OfflinePlayer player) {
    if(!TNELib.instance().useUUID) {
      return ecoID(player.getName());
    }
    return player.getUniqueId();
  }

  public static Player getPlayer(String identifier) {
    UUID id = (getID(identifier));
    if(!TNELib.instance().useUUID) {
      return Bukkit.getPlayer(IDFinder.ecoToUsername(id));
    }
    return Bukkit.getPlayer(id);
  }

  public static OfflinePlayer getOffline(String identifier) {
    UUID id = getID(identifier);

    return Bukkit.getOfflinePlayer(id);
  }

  public static UUID getID(String identifier) {
    identifier = ChatColor.stripColor(identifier.replaceAll("\\[.*?\\] ?", "")).trim();
    TNELib.debug("GETID: " + identifier);
    if(isUUID(identifier)) {
      return UUID.fromString(identifier);
    }

    if(identifier.contains("faction-")) {
      TNELib.debug("Faction");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    if(identifier.contains("town-")) {
      TNELib.debug("Towny Town");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    if(identifier.contains("nation-")) {
      TNELib.debug("Towny Nation");
      UUID id = ecoID(identifier);
      checkSpecial(id);
      return id;
    }

    if(!TNELib.instance().useUUID) {
      TNELib.debug("ECO ID RETURNED");
      return ecoID(identifier);
    }

    UUID mojangID = (identifier.equalsIgnoreCase(TNELib.instance().consoleName))? null : MojangAPI.getPlayerUUID(identifier);
    if(mojangID == null) {
      TNELib.debug("MOJANG API RETURNED NULL VALUE");
      return ecoID(identifier);
    }
    return mojangID;
  }

  private static void checkSpecial(UUID id) {
    if(!TNELib.instance().special.contains(id)) {
      TNELib.instance().special.add(id);
    }
  }

  public static boolean isUUID(String lookup) {
    try {
      UUID.fromString(lookup);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}