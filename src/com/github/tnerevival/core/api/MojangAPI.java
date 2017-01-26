package com.github.tnerevival.core.api;

import com.github.tnerevival.TNELib;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class MojangAPI {

  public static UUID getPlayerUUID(String name) {
    if(TNELib.uuidCache.containsKey(name)) {
      return TNELib.uuidCache.get(name);
    }
    JSONObject object = send("https://api.mojang.com/users/profiles/minecraft/" + name);
    UUID id = UUID.fromString(dashUUID(object.get("id").toString()));

    if(id != null) {
      TNELib.uuidCache.put(name, id);
    }

    return id;
  }

  public static String getPlayerUsername(UUID id) {
    if(TNELib.uuidCache.containsValue(id)) {
      for(Map.Entry<String, UUID> entry : TNELib.uuidCache.entrySet()) {
        if(entry.getValue().equals(id)) {
          return entry.getKey();
        }
      }
    }
    JSONObject object = send("https://sessionserver.mojang.com/session/minecraft/profile/" + id.toString().replace("-", ""));
    String name = object.get("name").toString();

    if(name != null) {
      TNELib.uuidCache.put(name, id);
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
    }
    return builder.toString();
  }
}