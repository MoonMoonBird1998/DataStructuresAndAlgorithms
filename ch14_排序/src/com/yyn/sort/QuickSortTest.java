package com.yyn.sort;

import com.yyn.tools.Integers;
import com.yyn.tools.Times;

public class QuickSortTest {

    public static void main(String[] args) {
        Integer[] random = Integers.random(10000000, 1, 100000000);
        //Integers.println(random);
        Times.test("快速排序",() ->{
            QuickSort.sort(random);
        });
        System.out.println(Integers.isAscOrder(random));
    }
}
