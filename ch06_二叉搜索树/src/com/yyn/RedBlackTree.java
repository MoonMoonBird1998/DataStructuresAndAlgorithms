package com.yyn;

import com.sun.org.apache.regexp.internal.RE;
import com.yyn.printer.BinaryTreeInfo;
import com.yyn.printer.BinaryTrees;

/**
 * 红黑树
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> implements BinaryTreeInfo {
    //根节点
    private Node root;
    //记录树中元素的个数
    private int N;
    //红色链接
    private static final boolean RED = true;
    //黑色链接
    private static final boolean BLACK = false;


    //结点类
    private class Node {
        //存储键
        public Key key;
        //存储值
        private Value value;
        //记录左子结点
        public Node left;
        //记录右子结点
        public Node right;
        //由其父结点指向它的链接的颜色
        public boolean color;

        public Node(Key key, Value value, Node left, Node right, boolean color) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        @Override
        public String toString() {
            return (color ? "R" : "B") + "_" + key + "=" + value;
        }
    }


    //获取树中元素的个数
    public int size() {
        return N;
    }


    /**
     * 判断当前节点的父指向链接是否为红色
     *
     * @param x
     * @return
     */
    private boolean isRed(Node x) {
        if (x==null){
            return false;
        }
        return x.color==RED;
    }

    /**
     * 左旋转
     *
     * @param h
     * @return
     */
    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /**
     * 右旋
     *
     * @param h
     * @return
     */
    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /**
     * 颜色反转,相当于完成拆分4-节点
     *
     * @param h
     */
    private void flipColors(Node h) {
        h.color =RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    /**
     * 在整个树上完成插入操作
     *
     * @param key
     * @param val
     */
    public void put(Key key, Value val) {
        root = put(root,key,val);
        //根结点的颜色总是黑色
        root.color = BLACK;
    }

    /**
     * 在指定树中，完成插入操作,并返回添加元素后新的树
     *
     * @param h
     * @param key
     * @param val
     */
    private Node put(Node h, Key key, Value val) {
        if (h == null) {
            N++;
            return new Node(key,val,null,null,RED);
        }

        int cmp = key.compareTo(h.key);
        if (cmp > 0){
            h.right = put(h.right, key ,val);
        }else if (cmp < 0){
            h.left = put(h.left, key ,val);
        }else {
            //覆盖之前的val
            h.value = val;
        }

        //判断是否需要修正红黑树的性质
        if (isRed(h.right) && !isRed(h.left)){
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)){
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)){
            flipColors(h);
        }
        return h;
    }

    //根据key，从树中找出对应的值
    public Value get(Key key) {
        return get(root,key);
    }

    //从指定的树x中，查找key对应的值
    public Value get(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);

        if (cmp > 0){
            return get(x.right,key);
        }else if (cmp < 0){
            return get(x.left,key);
        }else {
            return x.value;
        }

    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((RedBlackTree.Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((RedBlackTree.Node) node).right;
    }

    @Override
    public Object string(Object node) {
        return (RedBlackTree.Node) node;
    }


    public static void main(String[] args) {
        RedBlackTree<Integer, String> binaryTree = new RedBlackTree<>();

        binaryTree.put(1,"张三");
        binaryTree.put(2,"李四");
        binaryTree.put(3,"王五");
        binaryTree.put(4,"赵六");
        binaryTree.put(5,"孙七");
        binaryTree.put(6,"刘八");
        binaryTree.put(7,"刘能");
        binaryTree.put(8,"张能");
        binaryTree.put(9,"吴能");
        binaryTree.put(10,"松江");
        binaryTree.put(11,"吴用");
        BinaryTrees.println(binaryTree);
    }

}
