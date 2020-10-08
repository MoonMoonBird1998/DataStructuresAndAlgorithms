package com.yyn;

public class Test {

    public static void main(String[] args) {
        CircleLinkedList linkedList = new CircleLinkedList();
        linkedList.add(1);
        System.out.println(linkedList);
        linkedList.add(2);
        System.out.println(linkedList);
        linkedList.add(3);
        System.out.println(linkedList);
        linkedList.add(0, 5);
        linkedList.add(4,10);
        System.out.println(linkedList);
        linkedList.remove(0);
        System.out.println(linkedList);
        linkedList.remove(1);
        System.out.println(linkedList);
    }
}
