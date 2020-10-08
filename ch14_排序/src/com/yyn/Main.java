package com.yyn;

import com.yyn.tools.Integers;
import com.yyn.排序.*;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //Integer[] random = Integers.ascOrder(1, 10000);
        //Integer[] random = Integers.tailAscOrder(1, 10, 8);
        Integer[] random = Integers.random(10000, 1, 10000);
        test(random, new BubbleSort1(), new BubbleSort2(), new BubbleSort3(), new HeapSort(), new SelectionSort());
        /*isAscOrder(random, new SelectionSort());
        isAscOrder(random, new BubbleSort3());
        isAscOrder(random, new HeapSort());*/
    }

    static void isAscOrder(Integer[] integers, Sort sort){
        Integer[] copy = Integers.copy(integers);
        sort.sort(copy);
        Integers.println(copy);
        System.out.println(Integers.isAscOrder(copy));
    }

    static void test(Integer[] integers, Sort... sorts){
        for (Sort sort: sorts){
            sort.sort(Integers.copy(integers));
        }
        //对这些排序算法进行排序，规则是时间长短
        Arrays.sort(sorts);
        for (Sort sort: sorts){
            System.out.println(sort);
        }
    }
}
