package com.yyn.main;

import java.util.Date;

/**
 * 求斐波那契数
 */
public class FibonacciNumber {
    public static void main(String[] args) {
        /*Date date1 = new Date();
        System.out.println(fib2(50));
        Date date2 = new Date();
        System.out.println("方法的执行时间为:" + (date2.getTime() - date1.getTime()));*/

        System.out.println(fib1(4));
    }

    /**
     * 第一种方式，使用递归的方式求解
     * @param n
     * @return
     */
    public static int fib1(int n){
        if (n <= 1){
            return 0;
        } else if (n <= 2){
            return 1;
        }
        return fib1(n - 1) + fib1(n - 2);
    }
    /**
     * 第二种方式，使用for循环
     * 求出第n个数为多少
     * @param n
     * @return
     */
    public static int fib2(int n){
        //定义第一个和第二个数，第三个数等于两者之和
        int first = 0;
        int second = 1;
        //定义结果
        int result = 0;
        //第n个数为前两者之和，这里使用for循环来对前面的数进行相加
        //如果需要计算第n个数，则需要进行n-1次相加
        for(int i = 1; i <= n-1; i++){
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }
}
