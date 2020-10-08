package com.yyn;

import org.omg.CORBA.NO_IMPLEMENT;
import sun.text.normalizer.UCharacter;

import java.util.ArrayList;
import java.util.HashMap;

public class Trie <V>{
    private int size;
    private Node<V> root = new Node<>();

    private static class Node<V>{
        //存储下一个节点以及对应的字符
        HashMap<Character, Node<V>> child;
        Character character;
        Node<V> parent;
        V value;
        boolean word;
        public Node(){
            child = new HashMap<>();
        }
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public void clear(){
        size = 0;
        root.child.clear();
    }

    /**
     * 判断是否包含某个单词
     * @return
     */
    public boolean contains(String key){
        Node<V> node = node(key);
        return node != null && node.word;
    }

    /**
     * 通过传入的字符串来获得对应的节点
     * @param key
     * @return
     */
    private Node<V> node(String key){
        checkKeyNotNull(key);
        Node<V> node = root;
        int len = key.length();
        for (int i = 0; i < len; i++){
            char c = key.charAt(i);
            node = node.child.get(c);
            if (node == null) return null;
        }
        return node;
    }

    private void checkKeyNotNull(String key){
        if (key == null || key.length() == 0){
            throw new RuntimeException("key不能为null，并且长度必须大于1");
        }
    }

    public V get(String key){
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }

    /**
     * 添加一个单词并且指定一个对应的值
     * @param str
     * @param value
     * @return
     */
    public V add(String str, V value){
        checkKeyNotNull(str);
        Node<V> node = root;
        int len = str.length();
        for (int i = 0; i < len; i ++){
            char c = str.charAt(i);
            Node<V> childNode = node.child.get(c);
            if (childNode == null){
                childNode = new Node<>();
                childNode.parent = node;
                childNode.character = c;
                node.child.put(c, childNode);
            }
            node = childNode;
        }

        if (node.word){//这个单词已经存在
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node.word = true;
        node.value = value;
        size++;
        return null;
    }

    /**
     * 删除传入的单词
     * @param str
     * @return
     */
    public V remove(String str){
        Node<V> node = node(str);
        if (node == null || !node.word) return null;
        V oldValue = node.value;
        size--;
        if (node.child != null && !node.child.isEmpty()){//如果这个节点后面有子节点，那么将这个节点是否为单词设置为false就行
            node.word = false;
            return oldValue;
        }
        //程序来到这里说明要删除的节点没有子节点
        Node<V> parent = node.parent;
        while (parent != null){
            parent.child.remove(node.character);
            if (!parent.child.isEmpty() || parent.word) break;
            node = parent;
            parent = node.parent;
        }
        return oldValue;
    }

    /**
     * 判断是否有以传入字符串开始的单词
     * @return
     */
    public boolean startsWith(String prefix){
        Node<V> node = node(prefix);
        return node != null ? true : false;
    }
}
