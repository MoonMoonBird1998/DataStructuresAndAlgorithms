package com.yyn;

public class Stack<E> {

    private ArrayList<E> list = new ArrayList<>();

    /**
     * 往栈里存放元素,也就是往数组的最后一个位置添加元素
     * @param element
     */
    public void push(E element){
        list.add(element);
    }

    /**
     * 弹出栈顶的元素，也就是删除数组末尾的元素
     * @return
     */
    public E pop(){
        return list.remove(list.size() - 1);
    }

    public int size(){
        return list.size();
    }

    public E peep(){
        return list.get(list.size() - 1);
    }

    public boolean isEmpty(){
        return list.isEmpty();
    }

    public void clear(){
        list.clear();
    }
}
