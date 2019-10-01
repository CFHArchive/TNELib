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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by creatorfromhell on 11/6/2016.
 **/
public interface MapListener<K, V> {
  void update();
  Map<K, V> changed();
  void clearChanged();
  void put(K key, V value);
  V get(Object key);
  Collection<V> values();
  int size();
  boolean isEmpty();
  boolean containsKey(Object key);
  boolean containsValue(Object value);
  void preRemove(Object key, V value);
  Set<K> keySet();
  Set<Map.Entry<K, V>> entrySet();
  void remove(Object key);
  default Map<K, V> map() {
    Map<K, V> m = new HashMap<K, V>();
    entrySet().forEach((entry)->m.put(entry.getKey(), entry.getValue()));

    return m;
  }
}