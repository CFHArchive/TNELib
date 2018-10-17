package com.github.tnerevival.core.utils;

import java.util.Map;

/**
 * Created by creatorfromhell on 7/5/2017.
 * All rights reserved.
 **/
public class Utilities {
  @SuppressWarnings("rawtypes")
  public static Object getKey(Map m, Object value) {
    for(Object obj : m.keySet()) {
      if(m != null && m.get(obj) != null && m.get(obj).equals(value)) {
        return obj;
      }
    }
    return null;
  }
}