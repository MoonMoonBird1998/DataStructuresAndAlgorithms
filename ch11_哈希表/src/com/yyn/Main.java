package com.yyn;

import com.yyn.domain.Key;
import com.yyn.domain.Num;
import com.yyn.domain.User;
import com.yyn.map.HashMap;
import com.yyn.map.Map;
import com.yyn.map.TreeMap;

public class Main {
    public static void main(String[] args) {
        //test01();
        //test04(new HashMap<>());
        test06();
    }

    static void test01(){
        HashMap<Object, Object> map = new HashMap<>();
        map.put(1,10);
        map.put(1,100);
        map.put(2,20);
        map.put(3,30);
        map.remove(2);
        System.out.println("size=" + map.size());
        map.print();
    }

    static void test04(HashMap<Object, Integer> map) {
        for (int i = 1; i <= 20; i++) {
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            map.put(new Key(i), i + 5);
        }
        Asserts.test(map.size() == 20);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == 10);
        Asserts.test(map.get(new Key(6)) == 11);
        Asserts.test(map.get(new Key(7)) == 12);
        Asserts.test(map.get(new Key(8)) == 8);
    }

    static void test05(){
        HashMap map = new HashMap();
        for (int i = 1; i <= 20; i++) {
            if (i == 3){
                System.out.println();
            }
            map.put(new Num(i), i);
        }
        map.print();
        map.remove(new Num(1));
        System.out.println(map.get(new Num(2)));
        System.out.println("size=" + map.size());
        map.traversal(new Map.Visitor() {
            @Override
            public boolean visit(Object key, Object value) {
                System.out.println(key + "=" + value);
                return false;
            }
        });
    }

    static void test06(){
        HashMap map = new HashMap();
        for (int i = 1; i <= 14; i++) {
            if (i == 14){
                System.out.println();
            }
            map.put(i, i*10);
        }

        System.out.println("size=" + map.size());
        map.print();
    }
}
