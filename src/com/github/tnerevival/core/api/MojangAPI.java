package com.github.tnerevival.core.api;

import com.github.tnerevival.TNELib;
import com.github.tnerevival.user.IDFinder;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class MojangAPI {

  public static UUID getPlayerUUID(String name) {
    if(TNELib.instance().getUuidManager().hasID(name)) {
      return TNELib.instance().getUuidManager().getID(name);
    }
    JSONObject object = send("https://api.mojang.com/users/profiles/minecraft/" + name);
    UUID id = (object != null && object.containsKey("id")) ? UUID.fromString(dashUUID(object.get("id").toString())) : IDFinder.ecoID(name, true);


    if(id != null) {
      TNELib.instance().getUuidManager().addUUID(name, id);
    }

    return id;
  }

  public static String getPlayerUsername(UUID id) {
    if(TNELib.instance().getUuidManager().containsUUID(id)) {
      return TNELib.instance().getUuidManager().getUsername(id);
    }
    JSONObject object = send("https://sessionserver.mojang.com/session/minecraft/profile/" + id.toString().replace("-", ""));
    String name = object.get("name").toString();

    if(name != null) {
      TNELib.instance().getUuidManager().addUUID(name, id);
    }
    return name;
  }

  private static JSONObject send(String url) {
    return (JSONObject) JSONValue.parse(sendGetRequest(url));
  }

  private static String dashUUID(String undashed) {
    return undashed.replaceAll(TNELib.uuidCreator.pattern(), "$1-$2-$3-$4-$5");
  }

  private static String sendGetRequest(String URL) {
    StringBuilder builder = new StringBuilder();
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
      connection.setRequestMethod("GET");
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String response;
      while((response = reader.readLine()) != null) {
        builder.append(response);
      }
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return builder.toString();
  }
}