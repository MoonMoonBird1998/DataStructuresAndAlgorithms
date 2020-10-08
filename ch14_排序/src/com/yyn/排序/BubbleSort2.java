package com.yyn.排序;

public class BubbleSort2 extends Sort {

    @Override
    protected void sort() {
        for (int end = integers.length - 1; end > 0 ; end--){
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++){
                if (compare(begin, begin-1) < 0){
                    swap(begin, begin-1);
                    sorted = false;
                }
            }
            if (sorted) break;
        }
    }
}
