/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core.collection;

import java.util.Collection;
import java.util.List;

/**
 * Created by creatorfromhell on 11/6/2016.
 **/
public interface ListListener<E> {
  void update();
  List<E> changed();
  void clearChanged();
  Collection<E> getAll();
  Collection<E> getAll(Object identifier);
  boolean add(E item);
  int size();
  boolean isEmpty();
  boolean contains(Object item);
  void preRemove(Object item);
  boolean remove(Object item);
}
