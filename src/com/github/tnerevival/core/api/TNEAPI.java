/*
 * The New Economy Minecraft Server Plugin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNERPGSS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.tnerevival.core.api;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.user.IDFinder;

import java.util.UUID;

/**
 * Created by creatorfromhell on 11/29/2016.
 **/
public class TNEAPI {

  public TNELib plugin;

  public TNEAPI(TNELib plugin) {
    this.plugin = plugin;
  }

  public UUID getID(String identifier) {
    return IDFinder.getID(identifier);
  }

  /*
   * Configuration-related Methods.
   */
  public String getString(String configuration) {
    return (String)getConfiguration(configuration, TNELib.instance().defaultWorld);
  }

  public String getString(String configuration, String world) {
    return (String)getConfiguration(configuration, world, "");
  }

  public String getString(String configuration, String world, UUID uuid) {
    return (String)getConfiguration(configuration, world, uuid.toString());
  }

  public String getString(String configuration, String world, String player) {
    return (String)getConfiguration(configuration, world, player);
  }

  public Boolean getBoolean(String configuration) {
    return (Boolean)getConfiguration(configuration, TNELib.instance().defaultWorld);
  }

  public Boolean getBoolean(String configuration, String world) {
    return (Boolean)getConfiguration(configuration, world, "");
  }

  public Boolean getBoolean(String configuration, String world, UUID uuid) {
    return (Boolean)getConfiguration(configuration, world, uuid.toString());
  }

  public Boolean getBoolean(String configuration, String world, String player) {
    return (Boolean)getConfiguration(configuration, world, player);
  }

  public Integer getInteger(String configuration) {
    return (Integer)getConfiguration(configuration, TNELib.instance().defaultWorld);
  }

  public Integer getInteger(String configuration, String world) {
    return (Integer)getConfiguration(configuration, world, "");
  }

  public Integer getInteger(String configuration, String world, UUID uuid) {
    return (Integer)getConfiguration(configuration, world, uuid.toString());
  }

  public Integer getInteger(String configuration, String world, String player) {
    return (Integer)getConfiguration(configuration, world, player);
  }

  public boolean hasConfiguration(String configuration) {
    if(configuration.toLowerCase().contains("database")) return false;
    return TNELib.configurations().hasConfiguration(configuration);
  }

  public Object getConfiguration(String configuration) {
    return getConfiguration(configuration, TNELib.instance().defaultWorld);
  }

  public Object getConfiguration(String configuration, String world) {
    return getConfiguration(configuration, world, "");
  }

  public Object getConfiguration(String configuration, String world, UUID uuid) {
    return getConfiguration(configuration, world, uuid.toString());
  }

  public Object getConfiguration(String configuration, String world, String player) {
    if(configuration.toLowerCase().contains("database")) return "";
    return TNELib.configurations().getConfiguration(configuration, world, player);
  }

  public void setConfiguration(String configuration, Object value) {
    if(configuration.toLowerCase().contains("database")) return;
    TNELib.configurations().setConfiguration(configuration, value);
  }
}