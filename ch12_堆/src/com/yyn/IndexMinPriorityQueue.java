package com.yyn;

public class IndexMinPriorityQueue<T extends Comparable<T>> {
    //存储堆中的元素
    private T[] items;
    //保存每个元素在items数组中的索引，pq数组需要堆有序
    private int[] pq;
    //保存qp的逆序，pq的值作为索引，pq的索引作为值
    private int[] qp;
    //记录堆中元素的个数
    private int N;


    public IndexMinPriorityQueue(int capacity) {
        items = (T[]) new Comparable[capacity + 1];
        pq = new int[capacity + 1];
        qp = new int[capacity + 1];
        N = 0;
        //默认情况下，如果没有数据，qp数组中所有的位置都为0
        for (int i = 0; i < qp.length; i++){
            qp[i] = -1;
        }
    }

    //获取队列中元素的个数
    public int size() {
        return N;
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return N==0;
    }

    //判断堆中索引i处的元素是否小于索引j处的元素
    private boolean less(int i, int j) {
        return items[pq[i]].compareTo(items[pq[j]]) < 0;
    }

    //交换堆中i索引和j索引处的值
    private void exch(int i, int j) {
        //交换pq数组中的值
        int item = pq[i];
        pq[i] = pq[j];
        pq[j] = item;

        //交换qp数组中的值
        qp[pq[i]] = i;
        qp[pq[j]] = j;
    }

    //判断k对应的元素是否存在
    public boolean contains(int k) {
        //只要关联了索引，元素就存在
        return qp[k] !=-1;
    }

    //最小元素关联的索引
    public int minIndex() {
        return pq[1];
    }


    //往队列中插入一个元素,并关联索引i
    public void insert(int i, T t) {
        //判断i是否已经被关联，如果已经被关联，则不让插入
        if (contains(i)){
            return;
        }
        //元素个数+1
        N++;
        //存储数据
        items[i] = t;
        //关联pq索引
        pq[N] = i;
        //关联qp索引
        qp[i] = N;
        //上浮
        swim(N);

    }

    //删除队列中最小的元素,并返回该元素关联的索引
    public int delMin() {
        //获得最小元素的索引
        int minIndex = pq[1];
        //交换位置
        exch(1, N);
        //删除末尾元素
        items[pq[N]] = null;
        //删除关联索引
        qp[pq[N]] = -1;
        pq[N] = -1;
        N--;
        //下沉
        sink(1);
        return minIndex;
    }

    //删除索引i关联的元素
    public void delete(int i) {
        //找到i在pq中的位置
        int k = qp[i];
        //交换位置
        exch(k, N);

        //删除元素
        items[pq[N]] = null;
        //删除关联索引
        qp[pq[N]] = -1;
        pq[N] = -1;
        N--;
        //上浮和下沉
        swim(k);
        sink(k);

    }

    //把与索引i关联的元素修改为为t
    public void changeItem(int i, T t) {
        //修改
        items[i] = t;
        //找到索引i在pq中的位置
        int k = qp[i];
        //进行上浮和下沉
        sink(k);
        swim(k);
    }


    //使用上浮算法，使索引k处的元素能在堆中处于一个正确的位置
    private void swim(int k) {
        while(k>1){
            if (less(k,k/2)){
                exch(k,k/2);
            }

            k = k/2;
        }

    }


    //使用下沉算法，使索引k处的元素能在堆中处于一个正确的位置
    private void sink(int k) {
        while(2*k<=N){
            //找到子结点中的较小值
            int min;
            if (2*k+1<=N){
                if (less(2*k,2*k+1)){
                    min = 2*k;
                }else{
                    min = 2*k+1;
                }
            }else{
                min = 2*k;
            }
            //比较当前结点和较小值
            if (less(k,min)){
                break;
            }

            exch(k,min);
            k = min;
        }
    }

    public static void main(String[] args) {
        IndexMinPriorityQueue queue = new IndexMinPriorityQueue(5);
        queue.insert(0, 10);
        queue.insert(1, 30);
        queue.insert(2, 50);
        queue.delete(1);
        System.out.println(queue.delMin());
        System.out.println(queue.delMin());
    }

}

