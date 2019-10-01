/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core.collection.paginate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by creatorfromhell on 1/8/2017.
 **/
public class Page {
  private int page;
  List<Object> elements = new ArrayList<>();

  public Page(int page) {
    this.page = page;
  }

  public void addElement(Object o) {
    elements.add(o);
  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public List<Object> getElements() {
    return elements;
  }
}