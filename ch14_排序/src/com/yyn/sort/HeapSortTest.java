package com.yyn.sort;

import com.yyn.tools.Integers;

public class HeapSortTest {

    public static void main(String[] args) {
        Integer[] random = Integers.random(10, 1, 50);
        Integers.println(random);
        HeapSort.sort(random);
        Integers.println(random);
    }
}
