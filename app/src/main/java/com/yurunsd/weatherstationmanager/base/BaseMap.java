package com.yurunsd.weatherstationmanager.base;

import android.support.annotation.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2017/7/4.
 */

public class BaseMap implements Map<Object, Object> {

    private Map<Object, Object> map;

    public BaseMap() {
        this.map = new HashMap<Object, Object>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return map.get(key);
    }

    public Object getDefault(Object key, Object value) {
        Object o = map.get(key);
        if (o == null) {
            return value;
        } else {
            return o;
        }

    }

    @Override
    public Object put(Object key, Object value) {
        return map.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<?, ?> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @NonNull
    @Override
    public Set<Object> keySet() {
        return map.keySet();
    }

    @NonNull
    @Override
    public Collection<Object> values() {
        return map.values();
    }

    @NonNull
    @Override
    public Set<Entry<Object, Object>> entrySet() {
        return map.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return map.equals(o);
    }

    @Override
    public int hashCode() {
        return map.hashCode();
    }
}
