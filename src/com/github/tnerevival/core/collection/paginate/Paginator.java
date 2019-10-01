/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core.collection.paginate;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by creatorfromhell on 1/8/2017.
 **/
public class Paginator {

  TreeMap<Integer, Page> pages = new TreeMap<>();

  List<Object> collection;
  int perPage;

  public Paginator(List<Object> collection, int perPage) {
    this.perPage = perPage;
    this.collection = collection;
    populate();
  }

  public void populate() {
    Object[] values = collection.toArray();
    Page p = new Page(1);
    for(int i = 0; i < collection.size(); i++) {
      if(i % perPage == 0 && i != 0) {
        pages.put(p.getPage(), p);
        p = new Page(pages.lastKey() + 1);
      }
      p.addElement(values[i]);
    }
    pages.put(p.getPage(), p);
  }

  public Page getPage(int page) {
    return pages.get(page);
  }

  public Integer getMaxPages() {
    return pages.lastKey();
  }
}