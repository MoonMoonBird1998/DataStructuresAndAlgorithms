package com.yyn;

import com.yyn.printer.BinaryTreeInfo;
import com.yyn.printer.BinaryTrees;

public class Heap<T extends Comparable<T>> implements BinaryTreeInfo {

    //存储堆中的元素
    private T[] items;
    //记录堆中元素的个数
    private int N;

    public Heap(int capacity) {
        this.items= (T[]) new Comparable[capacity+1];
        this.N=0;
    }

    //判断堆中索引i处的元素是否小于索引j处的元素
    private boolean less(int i,int j){
        return items[i].compareTo(items[j])<0;
    }

    //交换堆中i索引和j索引处的值
    private void exch(int i,int j){
        T item = items[i];
        items[i] = items[j];
        items[j] = item;
    }

    //往堆中插入一个元素
    public void insert(T t){
        //将数组中索引为N位置处赋值
        items[++N] = t;//将N++

        //进行上浮
        swim(N);
    }

    //使用上浮算法，使索引k处的元素能在堆中处于一个正确的位置
    private void swim(int k){

        while (k > 1){
            //找到父节点的索引
            int p = k/2;
            //比较当前索引处的值和父节点的值的大小
            if (less(p, k)){
                //交换位置
                exch(p, k);
                k = p;
            }else {
                break;
            }
        }

    }

    //删除堆中最大的元素,并返回这个最大元素
    public T delMax(){
        //记录堆顶元素
        T top = items[1];

        //交换堆顶元素与末尾元素的位置
        exch(1,N);

        //删除尾部元素（之前是堆顶元素）
        items[N--] = null;

        //对新的堆顶元素进行下沉操作
        sink(1);

        return top;
    }

    //使用下沉算法，使索引k处的元素能在堆中处于一个正确的位置
    private void sink(int k){

        while (k*2 <= N){//循环条件是k所在节点必须为非叶子节点，因为叶子节点没有下沉的必要
            //先找出左子节点
            int max = k*2;

            //如果存在右子节点，让左子节点与右子节点进行比较，找出大的那个与要下沉的节点进行比较
            if ((max + 1) <= N && (less(max, max+1))){
                max = k*2 + 1;
            }

            //进行对比
            if (less(k, max)){
                exch(k, max);
                k = max;
            }else {
                break;
            }
        }

    }

    public static void main(String[] args) {

        Heap<Integer> heap = new Heap<>(10);
        heap.insert(5);
        heap.insert(6);
        heap.insert(3);
        heap.insert(2);
        heap.insert(7);
        heap.insert(1);
        heap.insert(8);
        heap.insert(10);
        heap.insert(4);
        heap.insert(9);

        BinaryTrees.println(heap);

        heap.delMax();
        BinaryTrees.println(heap);
        heap.delMax();
        BinaryTrees.println(heap);
        heap.delMax();
        BinaryTrees.println(heap);
        heap.insert(10);
        BinaryTrees.println(heap);

    }

    @Override
    public Object root() {
        return 1;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer)node;
        index = (index << 1);
        return index >= N ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer)node;
        index = (index << 1) + 1;
        return index >= N ? null : index;
    }

    @Override
    public Object string(Object node) {
        return items[(Integer)node];
    }
}
