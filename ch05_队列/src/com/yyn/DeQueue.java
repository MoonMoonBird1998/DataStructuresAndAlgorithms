package com.yyn;

import java.util.LinkedList;

/**
 * 双端队列
 * @param <E>
 */
public class DeQueue<E> {

    LinkedList<E> list = new LinkedList<>();

    public DeQueue() {
    }

    /**
     * 从队列的左边入队，也就是头部
     */
    public void enQueueLeft(E element){
        list.add(0,element);
    }

    /**
     * 从队列的右边入队，也就是尾部
     */
    public void enQueueRight(E element){
        list.add(element);
    }

    /**
     * 从左边出队
     * @return
     */
    public E deQueueLeft(){
        return list.remove(0);
    }

    /**
     * 从右边出队
     * @return
     */
    public E deQueueRight(){
        return list.remove(list.size() - 1);
    }

    public int size(){
        return list.size();
    }

    public boolean empty(){
        return list.isEmpty();
    }

    public E left(){
        return list.get(0);
    }

    public E right(){
        return list.get(list.size() - 1);
    }

    public void clear(){
        list.clear();
    }
}
