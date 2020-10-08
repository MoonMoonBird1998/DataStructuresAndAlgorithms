package com.yyn.sort;

public class InsertionSort {

    public static void sort(Comparable[] elements){
        for(int i = 0; i < elements.length - 1; i++){
            for (int j = i; j >= 0; j--){
                if (greater(elements[j], elements[j+1])){
                    swap(elements, j, j+1);
                }else {
                    break;
                }
            }
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
