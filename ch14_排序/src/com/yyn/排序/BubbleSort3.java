package com.yyn.排序;

public class BubbleSort3 extends Sort {

    @Override
    protected void sort() {
        for (int end = integers.length - 1; end > 0 ; end--){
            int lastSwapIndex = 0;
            for (int begin = 1; begin <= end; begin++){
                if (compare(begin, begin-1) < 0){
                    swap(begin, begin-1);
                    lastSwapIndex = begin;
                }
            }
            end = lastSwapIndex;
        }
    }
}
