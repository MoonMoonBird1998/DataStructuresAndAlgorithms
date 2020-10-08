package com.yyn.排序;

import java.text.DecimalFormat;

public abstract class Sort implements Comparable<Sort>{
    private int compareCount;
    private int swapCount;
    protected Integer[] integers;
    private long time;
    private DecimalFormat fmt = new DecimalFormat("#.00");

    public void sort(Integer[] integers){
        if (integers == null || integers.length < 2) return;

        this.integers = integers;

        long begin = System.currentTimeMillis();
        sort();
        time = System.currentTimeMillis() - begin;
    }

    protected abstract void sort();

    protected int compare(Integer i1, Integer i2){
        compareCount++;
        return integers[i1] - integers[i2];
    }

    protected int compareElement(Integer i1, Integer i2){
        compareCount++;
        return i1 - i2;
    }

    protected void swap(Integer i1, Integer i2){
        swapCount++;
        Integer temp = integers[i1];
        integers[i1] = integers[i2];
        integers[i2] = temp;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(compareCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        return "【" + getClass().getSimpleName() + "】\n"
                + timeStr + " \t"
                + compareCountStr + "\t "
                + swapCountStr + "\n"
                + "------------------------------------------------------------------";

    }

    /**
     * 用来比较看哪个排序算法执行时间短，按时间长短进行排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Sort o) {
        return (int) (this.time - o.time);
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";
        return fmt.format(number / 100000000.0) + "亿";
    }


}

