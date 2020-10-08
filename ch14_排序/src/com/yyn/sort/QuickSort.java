package com.yyn.sort;

public class QuickSort {

    private static int count;

    public static void sort(Comparable[] elements){
        int lo = 0;
        int hi = elements.length - 1;
        sort(elements, lo, hi);
        System.out.println("快速递归次数"+count);
    }

    private static void sort(Comparable[] elements, int lo, int hi){
        count+=1;
        if (lo >= hi) return;

        //进行切分，获得临界值所在位置的索引
        int partition = partition(elements, lo, hi);

        //根据临界值索引，对左右数组进行排序
        sort(elements, lo, partition - 1);
        sort(elements, partition + 1, hi);

    }

    private static int partition(Comparable[] elements, int lo, int hi){
        //确定分解值
        Comparable key = elements[lo];
        //定义左右指针
        int right = hi+1;
        int left = lo;

        //左右指针开始扫描
        while (true){
            //右指针先走
            while (less(key, elements[--right])){
                if (right == lo) break;
            }

            //左指针再走
            while (less(elements[++left], key)){
                if (left == hi) break;
            }
            //判断左指针是否已经大与等于右指针的索引,如果大于等于则则结束循环
            if (left >= right){
                break;
            }else {
                //如果不大于则交换左右指针所在位置的元素
                swap(elements, left, right);
            }
        }

        //交换临界值与右指针所在位置的值
        swap(elements, lo, right);

        //右指针所在的位置则是切分后临界值的索引
        return right;
    }


    private static boolean less(Comparable e1, Comparable e2){
        return e1.compareTo(e2) < 0;
    }

    private static void swap(Comparable[] elements, int index1, int index2){

        Comparable item = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = item;
    }
}
