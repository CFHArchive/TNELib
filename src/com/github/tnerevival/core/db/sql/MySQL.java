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
  private String host;
  private Integer port;
  private String database;
  private String user;
  private String password;

  public MySQL(DataManager manager) {
    super(manager);
  }

  @Override
  public void connect(DataManager manager) {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/"  + database + "?useSSL=false", user, password);
    } catch (SQLException e) {
      System.out.println("Unable to connect to MySQL.");
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Unable to find JBDC File.");
      e.printStackTrace();
    }
  }
}