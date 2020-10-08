package com.yyn.set;


import com.yyn.list.LinkedList;

public class ListSet<E> implements Set<E> {

    LinkedList<E> list = new LinkedList<>();

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(E element) {
        return list.contains(element);
    }

    @Override
    public void add(E element) {
        //判断添加的元素是否已经拥有
        int index = list.indexOf(element);
        if (index != -1){
            //如果包含则覆盖原来的值
            list.set(index,element);
        }else {
            list.add(element);
        }
    }

    @Override
    public E remove(E element) {
        E oldElement = list.remove(element);
        return oldElement;
    }

    @Override
    public void traversal(Visitor visitor) {
        if (visitor == null) return;
        int size = list.size();
        for (int i = 0; i < size; i++){
            if(visitor.visit(list.get(i))) return;
        }
    }
}
