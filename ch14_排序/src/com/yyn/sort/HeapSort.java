package com.yyn.sort;

public class HeapSort {
    //判断heap堆中索引i处的元素是否小于索引j处的元素
    private static  boolean less(Comparable[] heap, int i, int j) {
        return heap[i].compareTo(heap[j])<0;
    }

    //交换heap堆中i索引和j索引处的值
    private static  void exch(Comparable[] heap, int i, int j) {
        Comparable tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }



    //根据原数组source，构造出堆heap
    private static void createHeap(Comparable[] source, Comparable[] heap) {
        //将原数组中的数据拷贝到堆中
        for (int i = 0; i < source.length; i++){
            heap[i+1] = source[i];
        }

        //将堆中的所有非叶子节点从右到左从下到上进行下沉,使其符合堆的性质
        for (int i = (heap.length-1)/2; i >= 1; i--){
            sink(heap, i, heap.length-1);
        }
    }



    //对source数组中的数据从小到大排序
    public static  void sort(Comparable[] source) {
        //对原数组原地建堆
        Comparable[] heap = new Comparable[source.length + 1];
        createHeap(source, heap);

        //记录末尾索引的位置
        int last = heap.length - 1;
        //将堆顶元素与末尾元素交换位置
        while (last > 1){
            //交换
            exch(heap, 1, last--);

            //下沉
            sink(heap, 1, last);
        }

        //将堆中的元素拷贝到原数组中
        for (int i = 1; i < heap.length; i++){
            source[i-1] = heap[i];
        }
    }


    //在heap堆中，对target处的元素做下沉，范围是0~range
    private static void sink(Comparable[] heap, int target, int range){
        while (target*2 <= range) {//循环条件是k所在节点必须为非叶子节点，因为叶子节点没有下沉的必要
            //先找出左子节点
            int max = target * 2;

            //如果存在右子节点，让左子节点与右子节点进行比较，找出大的那个与要下沉的节点进行比较
            if ((max + 1) <= range && (less(heap, max, max + 1))) {
                max = target * 2 + 1;
            }

            //进行对比
            if (less(heap, target, max)) {
                exch(heap, target, max);
                target = max;
            } else {
                break;
            }
        }
    }

}
