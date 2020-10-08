package 队列;

import java.util.Stack;

/**
 * 使用栈实现队列的下列操作：
 *
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 * 示例:
 * MyQueue queue = new MyQueue();
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 * 链接：https://leetcode-cn.com/problems/implement-queue-using-stacks
 */
public class _232_用栈实现队列 {
    /**
     * 用于入队的栈
     */
    private Stack<Integer> inStack;
    /**
     * 用于出队的栈
     */
    private Stack<Integer> outStack;
    /** Initialize your data structure here. */
    public _232_用栈实现队列() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        inStack.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        checkOutStack();
        return outStack.pop();
    }

    /** Get the front element. */
    public int peek() {
        checkOutStack();
        return outStack.peek();
    }

    /**
     * 当pop或者peek时，需要判断出队的栈是否为空，如果为空，则需要将入队栈中的所有元素放入出队栈
     */
    private void checkOutStack(){
        if (outStack.isEmpty()){
            while(!inStack.isEmpty()){
                outStack.push(inStack.pop());
            }
        }
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }
}
