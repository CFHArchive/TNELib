package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.SQLDatabase;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2 extends SQLDatabase {

  public H2(DataManager manager) {
    super(manager);
  }

  @Override
  public void connect(DataManager manager) {
    File db = new File(manager.getFile());
    if(!db.exists()) {
      try {
        db.createNewFile();
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    if(connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    try {
      Class.forName("org.h2.Driver");
      connection = DriverManager.getConnection("jdbc:h2:" + manager.getFile() + ";mode=MySQL", manager.getUser(), manager.getPassword());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}