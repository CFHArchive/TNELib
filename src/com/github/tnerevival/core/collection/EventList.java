/*
 * The New Economy Minecraft Server Plugin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.

 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.tnerevival.core.collection;

import com.github.tnerevival.TNELib;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by creatorfromhell on 11/2/2016.
 **/
public class EventList<E> extends ArrayList<E> {
  private ListListener<E> listener;
  private List<E> list = new ArrayList<>();
  private long lastRefresh = new Date().getTime();

  public EventList() {
    super();
  }

  public EventList(ListListener listener) {
    super();
    this.listener = listener;
  }

  public void update() {
    listener.update();
    listener.clearChanged();
    lastRefresh = new Date().getTime();
  }

  @Override
  public E get(int index) {
    return list.get(index);
  }

  @Override
  public int size() {
    return listener.size();
  }

  @Override
  public boolean isEmpty() {
    return listener.isEmpty();
  }

  @Override
  public boolean add(E item) {
    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile") || TNELib.instance.cache) {
      if(!TNELib.instance.saveFormat.equalsIgnoreCase("flatfile") && TNELib.instance.cache && !contains(item)) {
        listener.add(item);
      }
      return list.add(item);
    }
    return listener.add(item);
  }

  @Override
  public E remove(int index) {
    E value = get(index);
    remove(value);
    return value;
  }

  @Override
  public boolean remove(Object item) {
    return remove(item, true);
  }

  public boolean remove(Object item, boolean database) {
    boolean removed = true;
    if(!TNELib.instance.saveFormat.equalsIgnoreCase("flatfile") && database) {
      listener.preRemove(item);
    }

    if(TNELib.instance.saveFormat.equalsIgnoreCase("flatfile") || TNELib.instance.cache) {
      removed = list.remove(item);
    }

    if(!TNELib.instance.saveFormat.equalsIgnoreCase("flatfile") && !TNELib.instance.cache || database) {
      removed = listener.remove(item);
    }
    return removed;
  }

  public EventListIterator<E> getIterator() {
    return new EventListIterator<>(list.iterator(), listener);
  }

  public void setListener(ListListener<E> listener) {
    this.listener = listener;
  }
}