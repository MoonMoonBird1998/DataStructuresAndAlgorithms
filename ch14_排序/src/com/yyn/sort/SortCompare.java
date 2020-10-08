package com.yyn.sort;

import com.yyn.tools.Integers;
import com.yyn.tools.Times;
import com.yyn.排序.Sort;

import java.util.Arrays;

public class SortCompare {

    public static void main(String[] args) {
        //Integer[] random = Integers.descOrder(1,10000);
        //Integer[] random = Integers.ascOrder(1,10000);
        Integer[] random = Integers.random(3000000, 1, 100000000);
        System.out.println(random.length);
        Integer[] random2 = Integers.copy(random);
        Integer[] random3 = Integers.copy(random);
        Integer[] random4 = Integers.copy(random);
        Integer[] random5 = Integers.copy(random);
        Integer[] random6 = Integers.copy(random);
        Integer[] random7 = Integers.copy(random);
        //第二种方法当数组比较快速可以完全有序时比较高效
        //第三种方法当数组后面的有序数据数量较多时效率高
        /*Times.test("shellSort", () -> {
            ShellSort.sort(random);
        });*/
 /*       Times.test("selectionSort", () -> {
            SelectionSort.sort(random2);
        });
        Times.test("bubbleSort", () -> {
            BubbleSort.sort(random3);
        });
        Times.test("insertionSort", () -> {
            InsertionSort.sort(random4);
        });*/
        Times.test("MergeSort", () -> {
            MergeSort.sort(random5);
        });

        Times.test("QuickSort", () -> {
            QuickSort.sort(random6);
        });
        Times.test("HeapSort", () -> {
            HeapSort.sort(random7);
        });
    }
}
