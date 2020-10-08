package com.yyn.sort;

public class MergeSort {

    private static Comparable[] assist;
    private static int count;

    public static void sort(Comparable[] elements){
        assist = new Comparable[elements.length];
        int lo = 0;
        int hi = elements.length - 1;

        sort(elements, lo, hi);
        System.out.println("归并递归次数"+count);
    }

    private static void sort(Comparable[] elements, int lo, int hi){
        count++;
        if (elements == null || lo >= hi) return;

        //先将大数组分为小数组
        int mid = lo + ((hi-lo) >> 1);
        //对两个小数组继续进行递归分组
        sort(elements, lo, mid);
        sort(elements, mid+1, hi);

        //合并
        merge(elements, lo, mid, hi);
    }

    private static void merge(Comparable[] elements, int lo, int mid, int hi){
        int index = lo;
        int p1 = lo;
        int p2 = mid + 1;

        //如果p1和p2两个指针都没有左右数组，则进行循环比较大小
        while (p1 <= mid && p2 <= hi){
            assist[index++] = less(elements[p1], elements[p2]) ? elements[p1++] : elements[p2++];
        }

        //将剩下的指针没有走完的组进行遍历
        while (p1 <= mid){
            assist[index++] = elements[p1++];
        }

        while (p2 <= hi){
            assist[index++] = elements[p2++];
        }

        //拷贝assist数组到原数组
        for (int i = lo; i <= hi; i++){
            elements[i] = assist[i];
        }
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
