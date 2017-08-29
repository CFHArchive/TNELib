package com.github.tnerevival.core.version;

import com.github.tnerevival.core.DataManager;

public abstract class Version {

  protected DataManager manager;

  public Version(DataManager manager) {
    this.manager = manager;
  }

  //abstract methods to be implemented by each child class
  public abstract double getSaveVersion();
  public abstract double versionNumber();
}