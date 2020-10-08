package com.yyn.heap;

import com.yyn.printer.BinaryTreeInfo;

import java.util.Arrays;
import java.util.Comparator;

public class BinaryHeap<E> implements Heap<E>, BinaryTreeInfo {
    private int size;
    private E[] elements;
    private Comparator comparator;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap(){
        this(null, null);
    }

    public BinaryHeap(Comparator comparator){
        this(null, comparator);
    }

    public BinaryHeap(E[] elements){
        this(elements, null);
    }

    public BinaryHeap(E[] elements, Comparator comparator){
        this.comparator = comparator;
        if (elements == null || elements.length == 0){
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        }else {
            //将外面传入的数组进行拷贝，拷贝到堆中的数组
            int length = elements.length > DEFAULT_CAPACITY ? elements.length : DEFAULT_CAPACITY;
            this.elements = (E[]) new Object[length];
            size = elements.length;
            System.arraycopy(elements, 0, this.elements, 0, elements.length);
            heapify();
        }
    }

    private void heapify() {
        //从最后一个非叶子节点开始往前下溢
        for (int i = (size >> 1) - 1; i >= 0; i--){
            siftDown(i);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        checkNull(element);
        //确保容量
        ensureCapacity(size + 1);
        //添加
        elements[size++] = element;
        //上滤
        siftUp(size - 1);
    }

    private void siftUp(int index){
        //获取父元素的索引
        E element = elements[index];
        while (index > 0){
            int pIndex = (index - 1) >> 1;
            E parent = elements[pIndex];
            if (compare(element, parent) <= 0) break;
            elements[index] = parent;
            index = pIndex;
        }
        elements[index] = element;
    }

    private void ensureCapacity(int oldCapacity) {
        if (oldCapacity < elements.length) return;
        //当size = 数组的长度时，表示应该进行扩容,这里使用位运算符，扩容之后的容量为之前的1.5倍
        int newCapacity = elements.length + (elements.length >> 1);
        //调用copyOf进行数组拷贝
        elements = Arrays.copyOf(elements, newCapacity);
    }

    @Override
    public E get() {
        checkEmpty();
        return elements[0];
    }

    @Override
    public E remove() {
        checkEmpty();
        //要删除堆顶元素，首先找到数组最后一个元素来代替堆顶元素，然后删除最后一个元素，最后对代替的元素进行下滤
        int lastIndex = --size;
        E element = elements[0];
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);

        return element;
    }

    private void siftDown(int index){
        E e = elements[index];
        int half = size >> 1;//计算出非叶子节点的数量，非叶子节点的数量 = 叶子节点开始的索引
        while (index < half){//只有index叶子节点的索引时才进行循环，因为叶子节点不存在下滤
           //默认与左节点进行比较，因为左子节点一定存在
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];
            int rightIndex = childIndex + 1;//右子节点的索引
            if (rightIndex < size && compare(child, elements[rightIndex]) < 0){
                childIndex = rightIndex;
                child = elements[rightIndex];
            }
            if (compare(e, child) >= 0) break;
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = e;
    }

    @Override
    public E replace(E element) {
        E top = null;
        if (size == 0){
            elements[0] = element;
            size++;
        }else {
            //替换掉堆顶元素，然后进行下滤
            top = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return top;
    }

    private void checkEmpty(){
        if (size == 0){
            throw new RuntimeException("堆为空！");
        }
    }

    private void checkNull(E element){
        if (element == null){
            throw new RuntimeException("不能添加空元素！");
        }
    }

    private int compare(E e1, E e2){
        return comparator == null ? ((Comparable) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer)node;
        index = (index << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer)node;
        index = (index << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(Integer)node];
    }
}
