package com.github.tnerevival.menu;

import java.util.Map;
import java.util.UUID;

/**
 * Created by creatorfromhell on 7/28/2017.
 * All rights reserved.
 **/
public abstract class MenuManager {

  public abstract ScreenHolder getHolder(UUID id);

  public abstract MenuScreen getScreen(UUID id);

  public abstract void addViewer(MenuViewer viewer);

  public abstract void removeViewer(UUID id);

  public abstract MenuViewer getViewer(UUID id);

  public abstract void addMenu(ScreenHolder menu);

  public abstract ScreenHolder getHolder(String name);

  public abstract Map<UUID, MenuViewer> getViewers();
}