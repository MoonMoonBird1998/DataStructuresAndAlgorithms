package com.yyn;

import com.yyn.domain.Key;

/**
 * 基于拉链法的散列表
 */
public class SeparateChainingHashST<Key, Value> {

    private int N;//键值对总数
    private int M;//散列表的大小

    private SymbolTable<Key, Value>[] symbolTable;//存放链表对象的数组

    public SeparateChainingHashST() {
        this(11);
    }

    public SeparateChainingHashST(int M) {
        //创建M条链表
        this.M = M;
        symbolTable = new SymbolTable[M];
        for (int i = 0; i < M; i++){
            symbolTable[i] = new SymbolTable<>();
        }
    }

    /**
     * 计算key的哈希值
     * @return
     */
    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get(Key key){

        return symbolTable[hash(key)].get(key);
    }

    public void put(Key key, Value value){
        symbolTable[hash(key)].put(key, value);
    }

    public void delete(Key key){
        symbolTable[hash(key)].delete(key);
    }

    public int size(){
        int n = 0;
        for (int i = 0; i < M; i++){
            n+=symbolTable[i].size();
        }
        N = n;
        return N;
    }

    public static void main(String[] args) {
        SeparateChainingHashST<Integer, String> st = new SeparateChainingHashST<>();
        st.put(1,"张三");
        st.put(0,"李四");
        st.put(1,"王五");
        int size = st.size();
        System.out.println(size);
        System.out.println(st.get(1));
        st.delete(1);
        System.out.println(st.get(1));
        System.out.println(st.size());
    }
}
