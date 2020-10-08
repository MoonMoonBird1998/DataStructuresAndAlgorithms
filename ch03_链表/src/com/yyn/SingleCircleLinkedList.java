package com.yyn;

/**
 * 手写一个单向循环链表
 * @param <E>
 */
public class SingleCircleLinkedList<E> {
    /**
     * 元素的个数
     */
    private int size;
    /**
     * 第一个元素的内存地址
     */
    private Node<E> first;

    /**
     * 当前指针指向的位置
     * 用于封装约瑟夫问题的解
     */
    private Node<E> current;

    /**
     * 让指针指向头节点
     */
    public void reset(){
        current = first;
    }

    /**
     * 让指针向前走一步
     * @return
     */
    public E next(){
        if (current == null) return null;
        Node<E> next = current.next;
        current = next;
        return next.element;
    }

    public E remove(){
        if (current == null) return null;
        E removed = remove(indexOf(current.element));
        next();
        return removed;
    }

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

        public Node() {
        }

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(element);
            sb.append(">" + next.element);
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
        Node<E> node = first;
        for (int i = 0; i < index; i++){
            node = node.next;
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
        Node<E> newNode = new Node<>(element, first);
        if ( index == 0){
            //如果为0，则说明添加到第一个节点
            if (size == 0){
                newNode.next = newNode;
                first = newNode;
            }else {
                Node<E> last = node(size - 1);
                first = newNode;
                last.next = first;
            }
        }else {
            Node<E> preNode = node(index - 1);
            newNode.next = preNode.next;
            preNode.next = newNode;
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
        Node<E> node = first;
        if (index == 0){
            //如果index==0,就说明该节点为第一个节点
            if (size == 1){
                first = null;
            }else {
                Node<E> last = node(size - 1);
                first = first.next;
                last.next = first;
            }
        }else {
            Node<E> preNode = node(index - 1);
            node = preNode.next;
            preNode.next = node.next;
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
