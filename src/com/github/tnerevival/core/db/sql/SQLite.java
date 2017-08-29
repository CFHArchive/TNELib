package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.SQLDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class SQLite extends SQLDatabase {

  private String file;

  public SQLite(DataManager manager) {
    super(manager);
  }

  @Override
  public void connect(DataManager manager) {
    File db = new File(file);
    if(!db.exists()) {
      try {
        db.createNewFile();
      } catch(IOException e) {
        System.out.println("Unable to create database file.");
        e.printStackTrace();
      }
    }
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + file);
    } catch (SQLException e) {
      System.out.println("Unable to connect to SQLite.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Unable to find JBDC File.");
      e.printStackTrace();
    }
  }
}