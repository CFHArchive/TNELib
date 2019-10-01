/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core.db.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by creatorfromhell on 1/10/2017.
 **/
public class SQLResult {

  private int id;
  private Statement statement;
  private ResultSet result;

  public SQLResult(int id, Statement statement, ResultSet result) {
    this.id = id;
    this.statement = statement;
    this.result = result;
  }

  public void close() {
    try {
      result.close();
      statement.close();
    } catch(SQLException e) {
      e.printStackTrace();
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ResultSet getResult() {
    return result;
  }

  public void setResult(ResultSet result) {
    this.result = result;
  }
}