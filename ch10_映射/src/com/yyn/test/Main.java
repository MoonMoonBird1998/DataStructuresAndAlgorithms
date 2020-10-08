package com.yyn.test;

import com.yyn.map.Map;
import com.yyn.map.TreeMap;
import com.yyn.set.Set;
import com.yyn.set.TreeSet;

public class Main {
    static void test01(){
        Map<String, Integer> map = new TreeMap<>();
        map.put("zs",26);
        map.put("a",28);
        map.put("c",32);
        map.put("b",52);
        map.put("g",55);
        map.put("z",98);
        map.put("e",123);
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "=" + value);
                return false;
            }
        });
        System.out.println("size=" + map.size());
        System.out.println("-------");
        System.out.println(map.get("5"));
        map.remove("zs");
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "=" + value);
                return false;
            }
        });
        System.out.println(map.containsValue(null));

    }

    static void test02(){
        TreeSet<Integer> set = new TreeSet<>();
        set.add(23);
        set.add(219);
        set.add(9);
        set.add(9);
        set.add(9);
        set.add(32);
        set.add(23);
        set.remove(32);
        set.remove(32);
        set.remove(19);
        set.remove(9);
        set.remove(219);
        set.remove(23);
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
        System.out.println("size=" + set.size());

    }

    public static void main(String[] args) {
        //test01();
        test02();
    }
}
