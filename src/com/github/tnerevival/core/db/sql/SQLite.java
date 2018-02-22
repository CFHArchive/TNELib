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

  public SQLite(DataManager manager) {
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
      return;
    }
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:" + manager.getFile());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}