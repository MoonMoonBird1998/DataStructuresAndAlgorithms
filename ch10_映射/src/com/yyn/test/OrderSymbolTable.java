package com.yyn.test;

public class OrderSymbolTable<Key extends Comparable<Key>,Value> {
    //记录首结点
    private Node head;
    //记录符号表中元素的个数
    private int N;

    private class Node{
        //键
        public Key key;
        //值
        public Value value;
        //下一个结点
        public Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public OrderSymbolTable() {
        this.head = new Node(null,null,null);
        this.N=0;
    }

    //获取符号表中键值对的个数
    public int size(){
        return N;
    }

    //key是否包含对应的value
    public boolean contains(Key key){
        if (key == null) return false;
        Node n = head;
        while (n.next != null){
            n = n.next;
            if (key.equals(n.key)){
                return true;
            }
        }
        return false;
    }

    //往符号表中插入键值对
    public void put(Key key,Value value){
        if (key == null || value == null) return;
        Node curr = head.next;
        Node pre = head;
        while (curr != null && key.compareTo(curr.key) > 0){
            pre = curr;
            curr = curr.next;
        }

        if (curr != null && key.compareTo(curr.key) == 0){
            //覆盖
            curr.value = value;
            return;
        }

        Node newNode = new Node(key, value, curr);
        pre.next = newNode;
        N++;

    }
    //删除符号表中键为key的键值对
    public void delete(Key key){
        if (key == null) return;
        Node n = head;
        while (n.next != null){
            if (key.equals(n.next.key)){
                n.next = n.next.next;
                N--;
                return;
            }
            n = n.next;
        }
    }

    //从符号表中获取key对应的值
    public Value get(Key key){
        if (key == null) return null;
        Node n = head;
        while (n.next != null){
            n = n.next;
            if (key.equals(n.key)){
                return n.value;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        OrderSymbolTable<Integer, String> symbolTable = new OrderSymbolTable<>();

        symbolTable.put(1,"王五");
        symbolTable.put(2,"张三");
        symbolTable.put(5,"李四");
        symbolTable.put(3,"张飞");

        System.out.println("元素的数量" + symbolTable.size());
        symbolTable.put(2,"赵六");
        System.out.println("元素的数量" + symbolTable.size());
        System.out.println("是否包含key为2的元素" + symbolTable.contains(2));
        symbolTable.delete(1);
        System.out.println("是否包含key为1的元素" + symbolTable.contains(1));
        System.out.println("元素的数量" + symbolTable.size());
        System.out.println(symbolTable.get(1));
        System.out.println(symbolTable.get(2));
        System.out.println(symbolTable.get(3));
    }


}