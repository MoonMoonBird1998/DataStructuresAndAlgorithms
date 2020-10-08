package com.yyn.排序;

public class HeapSort extends Sort {
    private int size;
    


    private void siftDown(int index){
        Integer i = integers[index];
        int half = size >> 1;//计算出非叶子节点的数量，非叶子节点的数量 = 叶子节点开始的索引
        while (index < half){//只有index叶子节点的索引时才进行循环，因为叶子节点不存在下滤
            //默认与左节点进行比较，因为左子节点一定存在
            int childIndex = (index << 1) + 1;
            Integer child = integers[childIndex];
            int rightIndex = childIndex + 1;//右子节点的索引
            if (rightIndex < size && compareElement(child, integers[rightIndex]) < 0){
                childIndex = rightIndex;
                child = integers[rightIndex];
            }
            if (compareElement(i, child) >= 0) break;
            integers[index] = child;
            index = childIndex;
        }
        integers[index] = i;
    }
    
    @Override
    protected void sort() {
        size = integers.length;
        for (int i = (integers.length >> 1) - 1; i >= 0; i--){
            siftDown(i);
        }
        while (size > 1){
            swap(0, --size);
            //交换位置后进行下滤，恢复堆的性质
            siftDown(0);
        }
    }
}
