package com.yyn.test;


import com.yyn.set.ListSet;
import com.yyn.set.Set;
import com.yyn.set.TreeSet;

public class Main {

    public static void main(String[] args) {
        //test01();
        test02();
    }

    static void test01(){
        Set<Integer> set = new ListSet<>();
        set.add(12);
        set.add(13);
        set.add(18);
        set.add(18);
        set.add(14);
        set.add(12);
/*        set.remove(20);
        set.remove(12);
        set.remove(12);
        set.remove(13);
        set.remove(14);
        set.remove(18);
        set.remove(18);*/
        //set.clear();
        System.out.println("size = " + set.size());
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    static void test02(){
        Set<Integer> set = new TreeSet<>();
        set.add(12);
        set.add(13);
        set.add(18);
        set.add(18);
        set.add(14);
        set.add(12);
/*        set.remove(20);
        set.remove(12);
        set.remove(12);
        set.remove(13);
        set.remove(14);
        set.remove(18);
        set.remove(18);*/
        set.clear();
        System.out.println("size = " + set.size());
        set.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

}
