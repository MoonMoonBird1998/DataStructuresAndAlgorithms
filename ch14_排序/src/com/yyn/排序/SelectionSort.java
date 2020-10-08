package com.yyn.排序;

public class SelectionSort extends Sort {
    @Override
    protected void sort() {
        for (int end = integers.length - 1; end > 0; end--){
            int maxIndex = 0;
            for (int begin = 1; begin <= end; begin ++){
                if (compare(maxIndex, begin) <= 0){
                    maxIndex = begin;
                }
            }
            swap(maxIndex, end);
        }
    }
}
