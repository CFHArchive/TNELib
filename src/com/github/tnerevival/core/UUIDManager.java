package com.github.tnerevival.core;

import java.util.UUID;

/**
 * Created by creatorfromhell on 11/28/2017.
 * All rights reserved.
 **/
public abstract class UUIDManager {

  public abstract boolean hasUsername(UUID id);
  public abstract boolean hasID(String username);
  public abstract boolean containsUUID(UUID id);
  public abstract void addUUID(String username, UUID id);
  public abstract UUID getID(String username);
  public abstract String getIdentifier(String username);
  public abstract String getUsername(UUID id);
  public abstract String getUsername(String id);
  public abstract void remove(String username);
}