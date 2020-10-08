package com.yyn.sort;

import com.yyn.tools.Integers;
import com.yyn.tools.Times;

public class MergeSortTest {
    public static void main(String[] args) {
        Integer[] random = Integers.random(10000000, 1, 1000000000);
        System.out.println(Integers.isAscOrder(random));
        Times.test("归并排序",() ->{
            MergeSort.sort(random);
        });
        System.out.println(Integers.isAscOrder(random));
    }
}
