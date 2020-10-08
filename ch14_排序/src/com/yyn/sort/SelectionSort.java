package com.yyn.sort;

public class SelectionSort {

    public static void sort(Comparable[] elements){
        for(int i = 0; i < elements.length - 1; i++){
            int minIndex = i;
            for (int j = i + 1; j < elements.length; j++){
                if (greater(elements[minIndex], elements[j])) minIndex = j;
            }
            swap(elements, minIndex, i);
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
