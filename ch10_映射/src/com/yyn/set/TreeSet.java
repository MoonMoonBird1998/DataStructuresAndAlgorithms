package com.yyn.set;

import com.yyn.map.Map;
import com.yyn.map.TreeMap;

/**
 * 使用TreeMap实现TreeSet
 * @param <E>
 */
public class TreeSet<E> implements Set<E> {

    TreeMap<E, Object> map = new TreeMap<>();
    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean contains(E element) {
        return map.containsKey(element);
    }

    @Override
    public void add(E element) {
        map.put(element, null);
    }

    @Override
    public void remove(E element) {
        map.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        map.traversal(new Map.Visitor<E, Object>() {
            @Override
            public boolean visit(E key, Object value) {
                visitor.visit(key);
                return false;
            }
        });
    }
}
