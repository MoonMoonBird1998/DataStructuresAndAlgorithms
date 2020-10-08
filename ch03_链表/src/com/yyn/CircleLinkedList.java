package com.yyn;


/**
 * 手写一个双向链表
 * @param <E>
 */
public class CircleLinkedList<E> {
    /**
     * 元素的个数
     */
    private int size;
    /**
     * 第一个元素的内存地址
     */
    private Node<E> first;
    /**
     * 最后一个元素的内存地址
     */
    private Node<E> last;

    /**
     * 定义一个内部类，表示每个元素所在的节点
     */
    private static class Node<E>{
        /**
         * 节点中的元素
         */
        E element;
        /**
         * 下一个节点的内存地址
         */
        Node<E> next;

        /**
         * 上一个节点的内存地址
         */
        Node<E> pre;

        public Node() {
        }

        public Node(E element, Node<E> next, Node<E> pre) {
            this.element = element;
            this.next = next;
            this.pre = pre;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (pre == null){
                sb.append("null<");
            }else {
                sb.append(pre.element + "<");
            }
            sb.append(element);
            if (next == null){
                sb.append(">null");
            }else {
                sb.append(">" + next.element);
            }
            return sb.toString();
        }
    }

    /**
     * 根据索引来获取节点
     * @param index
     * @return
     */
    private Node<E> node(int index){
        rangeCheck(index);
        //先判断这个节点的位置，如果在后半段则从last找起，前半段从first找起
        Node<E> node = null;
        if (index > (size >> 2)){
            node = last;
            for (int i = size - 1; i > index; i--){
                node = node.pre;
            }
        }else {
            node = first;
            for (int i = 0; i < index; i++){
                node = node.next;
            }
        }
        return node;
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("索引越界");
        }
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("索引越界");
        }
    }

    /**
     * 获取元素的个数
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * 清空所有元素
     */
    public void clear(){
        first = null;
        last = null;
        size = 0;
    }

    /**
     * 通过下标获取元素，只需要得到这个节点即可
     * @param index
     * @return
     */
    public E get(int index){
        return node(index).element;
    }

    /**
     * 改变某个元素的值，并且返回原来的值
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element){
        Node<E> node = node(index);
        E old = node.element;
        node.element = element;
        return old;
    }

    /**
     * 判断链表是否为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 往链表中某个位置添加元素
     * @param index
     * @param element
     */
    public void add(int index, E element){
        rangeCheckForAdd(index);
        //获得要插入位置的原节点，也就是插入节点的下一个节点
        if (index == size){//当链表中没有元素或者往最后添加元素时
            Node<E> oldLast = last;
            Node<E> newLast = new Node<E>(element, first, oldLast);
            if (oldLast == null){
                newLast.pre = newLast;
                newLast.next = newLast;
                first = newLast;
                last = first;
            }else {
                oldLast.next = newLast;
                first.pre = newLast;
                last = newLast;
            }

        }else {
            Node<E> next = node(index);
            Node<E> pre = next.pre;
            Node<E> node = new Node<>(element,next,pre);
            next.pre = node;
            pre.next = node;
            if (pre == last){
                first = node;
                last.next = first;
            }
        }
        size++;
    }

    public void add(E element){
        add(size, element);
    }

    /**
     * 移除某处元素
     * @param index
     * @return
     */
    public E remove(int index){
        rangeCheck(index);
        Node<E> node = node(index);
        if (size == 1){
            first = null;
            last = null;
        } else {
            Node<E> pre = node.pre;
            Node<E> next = node.next;
            pre.next = next;
            next.pre = pre;
            if (pre == last){
                first = next;
            }
            if (next == first){
                last = pre;
            }
        }
        size--;
        return node.element;
    }

    public int indexOf(E element){
        Node<E> node = first;
        if (element == null){
            for (int i = 0; i < size; i++){
                if (node.element == null) return i;
                node = node.next;
            }
        }else {
            for (int i = 0; i < size; i++){
                if (element.equals(node.element)) return i;
                node = node.next;
            }
        }
        return -1;
    }

    public boolean contains(E element){
        return indexOf(element) != -1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(", {");
        Node<E> node = first;
        for(int i = 0; i < size; i++){
            if (i != 0){
                sb.append(", ");
            }
            sb.append(node);
            node = node.next;
        }
        sb.append("}");
        return sb.toString();
    }
}

