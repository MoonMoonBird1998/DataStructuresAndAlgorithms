package com.yyn;



public class Test {
    public static void main(String[] args) {
        CircleDeQueue queue = new CircleDeQueue();
        for (int i = 1; i < 3; i++){
            queue.enQueueLeft(i);
            queue.enQueueRight(i*10);
        }
        System.out.println(queue);
        queue.enQueueLeft(66);
        queue.enQueueLeft(77);
        System.out.println(queue);
        queue.clear();
        System.out.println(queue);
    }


}
