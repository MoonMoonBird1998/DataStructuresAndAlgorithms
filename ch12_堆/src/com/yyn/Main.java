package com.yyn;

import com.yyn.heap.BinaryHeap;
import com.yyn.printer.BinaryTrees;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        test05();
    }

    static void test01(){
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(5);
        heap.add(8);
        heap.add(10);
        heap.add(2);
        heap.add(6);
        heap.add(12);
        heap.add(4);
        System.out.println("size= " + heap.size());
        BinaryTrees.println(heap);
        heap.remove();
        BinaryTrees.println(heap);
        heap.replace(1);
        BinaryTrees.println(heap);
    }

    static void test02(){
        Integer[] elements = {12,5,8,9,6,2,16,99,1,32,45,11};
        BinaryHeap<Integer> heap = new BinaryHeap<>(elements);
        BinaryTrees.println(heap);
        System.out.println(elements.length == heap.size());
    }

    static void test03(){
        Integer[] elements = {12,5,8,9,6,2};

        BinaryHeap<Integer> heap = new BinaryHeap<>(elements, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
/*        heap.add(5);
        heap.add(8);
        heap.add(10);
        heap.add(2);
        heap.add(6);
        heap.add(12);
        heap.add(4);*/
        BinaryTrees.println(heap);
    }

    /**
     * 从n个数中找出最大的k个数
     */
    static void test04(){
        //找出下面数组中最大的3个值
        int k = 3;
        Integer[] elements = {12,5,8,36,9,78,6,2,16,99,1,32,45,11,100,3,56,4};
        //需要用到小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        //首先要将所有数都扫描到
        for (int i = 0; i < elements.length; i++){
            //取出前3个数
            if (heap.size() < k){
                heap.add(elements[i]);
            }else if (elements[i] > heap.get()){
                heap.replace(elements[i]);
            }
        }
        BinaryTrees.println(heap);
    }

    /**
     * 从n个数中找出最小的k个数
     */
    static void test05(){
        //找出下面数组中最大的3个值
        int k = 3;
        Integer[] elements = {12,5,8,36,9,78,6,2,16,99,1,32,45,11,100,3,56,4};
        //需要用到小顶堆
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        //首先要将所有数都扫描到
        for (int i = 0; i < elements.length; i++){
            //取出前3个数
            if (heap.size() < k){
                heap.add(elements[i]);
            }else if (elements[i] < heap.get()){
                heap.replace(elements[i]);
            }
        }
        BinaryTrees.println(heap);
    }
}
