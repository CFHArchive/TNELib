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

import com.github.tnerevival.core.api.MojangAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by creatorfromhell on 11/6/2016.
 **/
public class IDFinder {

  public static UUID genUUID(String name) {
    UUID id = MojangAPI.getPlayerUUID(name);
    if(id != null) return id;

    return genUUID();
  }

  public static UUID genUUID() {
    return UUID.randomUUID();
  }

  public static UUID getID(Player player) {
    return getID(player.getDisplayName());
  }

  public static UUID getID(OfflinePlayer player) {
    return player.getUniqueId();
  }

  public static Player getPlayer(String identifier) {
    UUID id = (getID(identifier));
    return Bukkit.getPlayer(id);
  }

  public static Player getPlayer(UUID id) {
    return Bukkit.getPlayer(id);
  }

  public static UUID getID(String identifier) {
    identifier = ChatColor.stripColor(identifier);
    if(isUUID(identifier)) {
      return UUID.fromString(identifier);
    }
    UUID mojangID = MojangAPI.getPlayerUUID(identifier);
    if(mojangID == null) {
      return genUUID(identifier);
    }
    return mojangID;
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