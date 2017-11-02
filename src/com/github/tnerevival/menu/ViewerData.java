package com.github.tnerevival.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 11/2/2017.
 * All rights reserved.
 **/
public class ViewerData {

  private Map<String, Object> data = new HashMap<>();
  private UUID viewer;

  public ViewerData(UUID viewer) {
    this.viewer = viewer;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public UUID getViewer() {
    return viewer;
  }

  public void setViewer(UUID viewer) {
    this.viewer = viewer;
  }

  public Object getValue(String identifier) {
    return data.get(identifier);
  }

  public void setValue(String identifier, Object value) {
    data.put(identifier, value);
  }
}