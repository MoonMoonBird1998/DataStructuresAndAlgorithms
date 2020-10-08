package com.yyn;

public class Test {

    public static void main(String[] args) {
        Stack stack = new Stack();

        System.out.println(stack.size());
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        System.out.println(stack.peep());
        System.out.println("---");
        System.out.println(stack.size());

        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }

}
