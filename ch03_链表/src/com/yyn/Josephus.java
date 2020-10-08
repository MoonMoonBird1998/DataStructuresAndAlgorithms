package com.yyn;

/**
 * 约瑟夫问题
 * 这里借助单向循环链表来实现
 */
public class Josephus {

    public static void kill(){
        SingleCircleLinkedList<Integer> linkedList = new SingleCircleLinkedList<>();
        for (int i = 1; i <= 8; i++){
            linkedList.add(i);
        }
        linkedList.reset();
        while (!linkedList.isEmpty()){
            linkedList.next();
            linkedList.next();
            System.out.println(linkedList.remove());
        }
    }

    public static void main(String[] args) {
        kill();
    }
}
