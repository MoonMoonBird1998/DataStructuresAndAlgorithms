package com.yyn.sort;

public class ShellSort {

    public static void sort(Comparable[] elements){
        //1.设置增长量
        int h = 1;
        while(h < elements.length>>1){
            h = h<<2 + 1;
        }
        //2.排序
        while(h >= 1){
            //3.找到待插入的元素
            for (int i = h; i < elements.length; i++){

                //3.找到合适的位置，根插入排序规则类似
                for (int j = i; j >= h; j-=h){
                    if (greater(elements[j-h], elements[j])){
                        swap(elements, j, j-h);
                    }else {
                        break;
                    }
                }
            }
            h = h >> 1;
        }
    }


    private static boolean greater(Comparable e1, Comparable e2){
        return e1.compareTo(e2) > 0;
    }

    private static void swap(Comparable[] elements, int index1, int index2){

        Comparable item = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = item;
    }
}
