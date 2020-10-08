package com.yyn;



/**
 * 循环队列
 */
public class CircleQueue<E> {
    private int size;
    private E[] elements;
    private int front;
    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue(){
        this(DEFAULT_CAPACITY);
    }
    public CircleQueue(int initCapacity){
        initCapacity  = initCapacity <= 1 ? DEFAULT_CAPACITY : initCapacity;
        elements = (E[]) new Object[initCapacity];
    }

    public void enQueue(E element){
        ensureCapacity(size + 1);
        elements[index(size)] = element;
        size++;
    }

    private void ensureCapacity(int oldCapacity) {
        if (oldCapacity < elements.length) return;
        //当size = 数组的长度时，表示应该进行扩容,这里使用位运算符，扩容之后的容量为之前的1.5倍
        int newCapacity = elements.length + (elements.length >> 1);
        E[] newElements = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++){
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        front = 0;
    }

    public E deQueue(){
        E element =  elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return element;
    }

    public E front(){
        return elements[front];
    }

    public int size(){
        return size;
    }

    public boolean empty(){
        return size == 0;
    }

    /**
     * 通过传入的索引返回数组中的真实索引
     * @param index
     * @return
     */
    private int index(int index){
        index += front;
        return  index >= elements.length ? index-elements.length : index;
    }


    public void clear(){
        for (int i = 0; i < size; i++){
            elements[index(i)] = null;
        }
        front = 0;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(",front=").append(front).append(", [");
        for (int i = 0; i < elements.length; i ++){
            if (i != 0){
                sb.append(", ");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
