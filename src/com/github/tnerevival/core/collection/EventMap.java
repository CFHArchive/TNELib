/**
 * The New Economy Library Minecraft Server Plugin
 *
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/ or send a letter to
 * Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.
 * Created by creatorfromhell on 06/30/2017.
 */
package com.github.tnerevival.core.collection;

import com.github.tnerevival.TNELib;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by creatorfromhell on 11/2/2016.
 **/
public class EventMap<K, V> extends HashMap<K, V> {

  private MapListener<K, V> listener;
  private Map<K, V> map = new HashMap<>();
  private long lastRefresh = new Date().getTime();

  public EventMap() {
    super();
  }

  public EventMap(MapListener listener) {
    super();
    this.listener = listener;
  }

  public void update() {
    listener.update();
    listener.clearChanged();
    lastRefresh = new Date().getTime();
  }

  @Override
  public V get(Object key) {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate()) {
      if(!TNELib.instance().getSaveManager().getDataManager().isCacheData() || !map.containsKey(key)) {
        return listener.get(key);
      }
    }
    if(!TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate()
        || TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      return map.get(key);
    }
    return map.get(key);
  }

  @Override
  public V put(K key, V value) {
    return put(key, value, false);
  }

  public V put(K key, V value, boolean skip) {
    if(!TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() || TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      map.put(key, value);
    }

    if(!skip) {
      if (TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate()) {
        if (!TNELib.instance().getSaveManager().getDataManager().isCacheData() || !map.containsKey(key)) {
          listener.put(key, value);
        }

        if(TNELib.instance().getSaveManager().getDataManager().isCacheData() && map.containsKey(key)) {
          listener.changed().put(key, value);
        }
      }
    }
    return value;
  }

  @Override
  public V remove(Object key) {
    return remove(key, true);
  }

  public V remove(Object key, boolean database) {
    V removed = get(key);
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && database) {
      listener.preRemove(key, removed);
    }

    if(!TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() || TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      removed = map.remove(key);
      listener.put((K)key, removed);
    }

    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && !TNELib.instance().getSaveManager().getDataManager().isCacheData() && database || database) {
      listener.remove(key);
    }
    return removed;
  }

  @Override
  public boolean containsKey(Object key) {
    return get(key) != null;
  }

  public EventMapIterator<Map.Entry<K, V>> getIterator() {
    return new EventMapIterator<>(entrySet().iterator(), listener);
  }

  @Override
  public int size() {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate()) {
      return listener.size();
    }
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate()) {
      return listener.isEmpty();
    }
    return map.isEmpty();
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    putAll(m, false);
  }

  public void putAll(Map<? extends K, ? extends V> m, boolean skip) {
    for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
      put(entry.getKey(), entry.getValue(), skip);
    }
  }

  @Override
  public boolean containsValue(Object value) {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && !TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      return listener.containsValue(value);
    }
    return map.containsValue(value);
  }

  @Override
  public Set<K> keySet() {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && !TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      return listener.keySet();
    }
    return map.keySet();
  }

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && !TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      listener.map().forEach(action);
      return;
    }
    map.forEach(action);
  }

  @Override
  public Collection<V> values() {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && !TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      return listener.values();
    }
    return map.values();
  }

  @Override
  public Set<Map.Entry<K, V>> entrySet() {
    if(TNELib.instance().getSaveManager().getDataManager().getDb().supportUpdate() && !TNELib.instance().getSaveManager().getDataManager().isCacheData()) {
      return listener.entrySet();
    }
    return map.entrySet();
  }

  public MapListener getListener() {
    return listener;
  }

  public void setListener(MapListener<K, V> listener) {
    this.listener = listener;
  }
}