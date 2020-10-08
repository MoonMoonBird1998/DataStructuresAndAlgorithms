package com.yyn;


/**
 * 基于拉链法的散列表
 */
public class LinerProbingHashST<Key, Value> {

    private int N;//键值对总数
    private int M;//散列表的大小

    private Key[] keys;
    private Value[] values;

    public LinerProbingHashST() {
        this(16);
    }

    public LinerProbingHashST(int capacity) {
        keys = (Key[]) new Object[capacity];
        values = (Value[]) new Object[capacity];
        M = capacity;
    }

    /**
     * 计算key的哈希值
     * @return
     */
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key){

        if (key == null) return null;
        int hash = hash(key);
        //如果key不为空，则该索引处已被占用
        while (keys[hash] != null){
            //如果key相等，则覆盖
            if (key.equals(keys[hash])){
                return values[hash];
            }
            //需要向下探测
            hash = (hash + 1) % M;
        }
        return null;
    }

    public void put(Key key, Value value){
        if (key == null) return;
        int hash = hash(key);
        //如果key不为空，则该索引处已被占用
        while (keys[hash] != null){
            //如果key相等，则覆盖
            if (key.equals(keys[hash])){
                values[hash] = value;
                return;
            }
            //需要向下探测
            hash = (hash + 1) % M;
        }
        keys[hash] = key;
        values[hash] = value;
        N++;
    }

    public boolean contains(Key key){
        if (key == null) return false;
        for (int hash = hash(key); keys[hash] != null; hash = (hash + 1) % M){
            if (key.equals(keys[hash])){
                return true;
            }
        }
        return false;
    }

    public void delete(Key key){
        if (!contains(key)) return;
        int hash = hash(key);
        //线性探索，直到找到该key
        while (!key.equals(keys[hash])){
            hash = (hash + 1) % M;
        }
        //将该key位置删除
        keys[hash] = null;
        values[hash] = null;
        N--;
        hash = (hash + 1) % M;
        //将删除处索引后的元素删除后再重新加入表中，直到遇到空
        while (keys[hash] != null){
            Key oldKey = keys[hash];
            Value oldValue = values[hash];
            keys[hash] = null;
            values[hash] = null;
            N--;
            put(oldKey, oldValue);
            hash = (hash + 1) % M;
        }
    }

    public void see(){
        for (int i =0; i < M; i++){
            System.out.println(keys[i] + "=" +values[i]);
        }
    }


    public int size(){
        return N;
    }

    public static void main(String[] args) {
        LinerProbingHashST<Integer, String> st = new LinerProbingHashST<>();
        st.put(1,"张三");
        st.put(0,"李四");
        st.put(1,"王五");
        st.put(1,"王五");
        st.put(10,"王五");
        st.put(11,"王五");
        st.put(20,"王哈哈");
        st.delete(1);
        System.out.println(st.get(20));
        st.see();
        System.out.println(st.size());
    }
}
