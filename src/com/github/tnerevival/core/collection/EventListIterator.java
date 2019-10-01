/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core.collection;

import java.util.Iterator;

/**
 * Created by creatorfromhell on 11/6/2016.
 **/
public class EventListIterator<E> {

  Iterator<E> iterator;
  ListListener<E> listener;
  E last;

  public EventListIterator(Iterator<E> iterator, ListListener<E> listener) {
    this.iterator = iterator;
    this.listener = listener;
  }

  public void remove() {
    remove(true);
  }

  public void remove(boolean database) {
    if(database) listener.remove(last);
    iterator.remove();
  }

  public boolean hasNext() {
    return iterator.hasNext();
  }

  public E next() {
    last = iterator.next();
    return last;
  }
}