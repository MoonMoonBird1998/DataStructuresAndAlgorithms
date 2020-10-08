package com.yyn;


import java.util.Arrays;

/**
 * 手写实现一个ArrayList集合
 */
public class ArrayList<E> {
    /**
     * 集合中元素的数量
     */
    private int size;

    /**
     * 集合的默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 如果没有找到该元素，则返回该值
     */
    private static final int INDEX_NOT_FOUND = -1;

    /**
     * 集合中存放元素的数组
     */
    private E[] elements;

    /**
     * 无参构造方法，初始化容量为10
     */
    public ArrayList(){
        //调用有参构造方法
        this(DEFAULT_CAPACITY);
    }

    /**
     * 有参构造方法，可以自定义数组容量
     * @param capacity
     */
    public ArrayList(int capacity){
        //先判断是否比初始化容量大，如果没用初始化容量大则还是使用初始化容量
        capacity = capacity < DEFAULT_CAPACITY ? DEFAULT_CAPACITY : capacity;
        //给集合的初始化容
        elements = (E[]) new Object[capacity];
    }

    /**
     * 获取数组中元素的个数
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * 往集合中添加元素，添加到集合的末尾
     * @param element
     */
    public void add(E element){
        //直接调用另一个add方法即可，这样可以减少代码的变动
        add(size, element);
    }

    /**
     * 往集合中的某个位置添加元素
     * @param index
     * @param element
     */
    public void add(int index, E element){
        //检查索引范围是否正确
        rangeCheckForAdd(index);
        //要保证数组的容量够用
        ensureCapacity(size);
        for (int i = size; i > index; i--){
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    private void ensureCapacity(int oldCapacity) {
        if (oldCapacity < elements.length) return;
        //当size = 数组的长度时，表示应该进行扩容,这里使用位运算符，扩容之后的容量为之前的1.5倍
        int newCapacity = elements.length + (elements.length >> 1);
        //调用copyOf进行数组拷贝
        elements = Arrays.copyOf(elements, newCapacity);
    }

    /**
     * 移除集合中某个元素
     * @param index
     * @return
     */
    public E remove(int index){
        indexOutOfBoundCheck(index);
        E element = elements[index];
        for(int i = index + 1; i < size; i++){
            //从index位置开始，将每个元素都向前移动一位
            elements[i - 1] = elements[i];
        }
        //将先前最后一个元素的位置设置为null
        elements[--size] = null;
        return element;
    }

    /**
     * 将数组中某个位置设置为某个元素
     * @param index
     * @param element
     * @return 返回本来的元素
     */
    public E set(int index, E element){
        indexOutOfBoundCheck(index);
        E oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    /**
     * 获得数组中某个位置的元素
     * @param index
     * @return
     */
    public E get(int index){
        indexOutOfBoundCheck(index);
        return elements[index];
    }

    /**
     * 判断集合是否为空，如果size为0则代表集合为空
     * @return
     */
    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * 判断是否包含某个元素
     * @param element
     * @return
     */
    public boolean contains(E element){
        return indexOf(element) != INDEX_NOT_FOUND;
    }

    /**
     * 得到集合中某个元素的位置，如果集合中没用该元素则返回-1
     * @param element
     * @return
     */
    public int indexOf(E element){
        //这里进行空值判断，如果输入的参数为null，就不能使用equals进行比较
        //此时我们要便利集合返回第一个为null的值
        if (element == null){
            for(int i = 0; i < size; i++){
                if (elements[i] == null) return i;
            }
            return INDEX_NOT_FOUND;
        }else {
            for(int i = 0; i < size; i++){
                if (element.equals(elements[i])) return i;
            }
            return INDEX_NOT_FOUND;
        }
    }

    /**
     * 清空集合，将size设置为0
     */
    public void clear(){
        //将数组中每一个元素设置为null
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * 当一个方法中有索引参数时应该调用该方法检查索引是否越界
     * @param index
     */
    private void indexOutOfBoundCheck(int index){
        if (index >= size || index < 0){
            throw new IndexOutOfBoundsException("index is " + index + " but size is " + size);
        }
    }

    private void rangeCheckForAdd(int index){
        if (index > size || index < 0){
            throw new IndexOutOfBoundsException("index is " + index + " but size is " + size);
        }
    }

    @Override
    public String toString() {
        //使用StringBuilder来拼接字符串
        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size).append(",").append("[");
        for (int i = 0; i < size; i++){
            if (i != 0){
                sb.append(", ");
            }
            sb.append(elements[i]);
        }
        sb.append("]");
        return sb.toString();
    }
}
