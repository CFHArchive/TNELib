package com.github.tnerevival.core.db.sql;

import com.github.tnerevival.core.DataManager;
import com.github.tnerevival.core.db.SQLDatabase;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Daniel Vidmar aka creatorfromhell
 *
 */
public class MySQL extends SQLDatabase {

  public MySQL(DataManager manager) {
    super(manager);
  }

  @Override
  public void connect(DataManager manager) {
    if(connection != null) {
      return;
    }
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://" + manager.getHost() + ":" + manager.getPort() + "/"  + manager.getDatabase() + "?useSSL=false", manager.getUser(), manager.getPassword());
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}