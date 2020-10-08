package com.yyn;

import com.yyn.tools.Integers;
import com.yyn.tools.Times;

public class BubbleSort {

    public static void main(String[] args) {
        Integer[] random = Integers.tailAscOrder(1,20000, 2000);
        //Integer[] random = Integers.ascOrder(1,10000);
        System.out.println(random.length);
        Integer[] random2 = Integers.copy(random);
        Integer[] random3 = Integers.copy(random);
        //第二种方法当数组比较快速可以完全有序时比较高效
        //第三种方法当数组后面的有序数据数量较多时效率高
        Times.test("sort1", () -> {
            sort1(random);
        });
        Times.test("sort2", () -> {
            sort2(random2);
        });
        Times.test("sort3", () -> {
            sort3(random3);
        });
        /*sort1(random);
        sort2(random2);
        sort3(random3);*/

    }

    static void sort1(Integer[] integers){
        for (int end = integers.length - 1; end > 0 ; end--){
            for (int begin = 1; begin <= end; begin++){
                if (integers[begin - 1] > integers[begin]){
                    Integer temp = integers[begin];
                    integers[begin] = integers[begin - 1];
                    integers[begin - 1] = temp;
                }
            }
        }
        //Integers.println(integers);
    }

    static void sort2(Integer[] integers){
        for (int end = integers.length - 1; end > 0 ; end--){
            //设立一个标记，当发现数据已经是有序的时停止冒泡
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++){
                if (integers[begin - 1] > integers[begin]){
                    Integer temp = integers[begin];
                    integers[begin] = integers[begin - 1];
                    integers[begin - 1] = temp;
                    sorted = false;
                }
            }
            if (sorted) break;
        }
        //Integers.println(integers);
    }

    static void sort3(Integer[] integers){
        //最后一次交换的位置
        for (int end = integers.length - 1; end > 0 ; end--){
            int lastChangeIndex = 0;//当数组完全有序时，不进入第二层for循环的if语句，因此lastChangeIndex始终为0这时将退出下一轮循环
            for (int begin = 1; begin <= end; begin++){
                if (integers[begin - 1] > integers[begin]){
                    Integer temp = integers[begin];
                    integers[begin] = integers[begin - 1];
                    integers[begin - 1] = temp;
                    lastChangeIndex = begin;
                }
            }
            end = lastChangeIndex;
        }
        //Integers.println(integers);
    }
}
