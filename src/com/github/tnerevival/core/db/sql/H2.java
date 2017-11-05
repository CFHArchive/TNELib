package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.SQLDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2 extends SQLDatabase {

  private String file;
  private String user;
  private String password;

  public H2(DataManager manager) {
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
      Class.forName("org.h2.Driver");
      connection = DriverManager.getConnection("jdbc:h2:" + file + ";mode=MySQL", user, password);
    } catch (SQLException e) {
      System.out.println("Unable to connect to H2.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Unable to find JBDC File.");
      e.printStackTrace();
    }
  }
}