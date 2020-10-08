package com.yyn;

import com.yyn.printer.BinaryTreeInfo;

import java.util.*;

/**
 * 二叉树，这里提供一些二叉树共有的方法，各种类型的二叉树只要继承该类即可
 */
public class BinaryTree<E> implements BinaryTreeInfo {

    protected int size;

    /**
     * 根节点
     */
    protected Node<E> root;

    protected Comparator comparator;

    public BinaryTree(){
        this(null);
    }

    public BinaryTree(Comparator comparator){
        this.comparator = comparator;
    }


    protected static class Node<E>{
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node (E element){
            this(element, null);
        }

        public Node (E element, Node<E> parent){
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeaf(){
            return right == null && left == null;
        }

        /**
         * 寻找兄弟节点
         * @return
         */
        public Node<E> sibling(){
            if (isRightChild()){
                return parent.left;
            }

            if (isLeftChild()){
                return parent.right;
            }

            return null;
        }

        @Override
        public String toString() {
            return element.toString() + ",p=" + (parent == null ? null : parent.element) + (",r=" +(right == null ? null : right.element)) + (",l=" + (left == null ? null : left.element)) + ",兄弟=" + (sibling() == null ? null : (sibling().element));
        }

        /**
         * 判断是否为左子树
         * @return
         */
        public boolean isLeftChild(){
            return parent != null && this == parent.left;
        }

        /**
         * 判断是否为右子树
         * @return
         */
        public boolean isRightChild(){
            return parent != null && this == parent.right;
        }

        public boolean hasTwoChild(){
            return right != null && left != null;
        }

        public boolean noChild(){
            return right == null && left == null;
        }
    }

    public void checkElement(E element){
        if (element == null){
            throw new IllegalArgumentException("element can't be null!");
        }
    }

    protected Node<E> createNode(E element, Node<E> parent){
        return new Node<>(element, parent);
    }

    public int size(){
        return size;
    }

    public void clear(){
        root = null;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     *  对外提供遍历的接口，支持前中后层四种遍历方式
     */
    public static interface Visitor<E>{
        //外界调用者可以自定义对遍历结果的处理方式
        void visit(E element);
    }
    /**
     * 前序遍历对外提供的方法
     */
    public void preorderTraver(Visitor visitor){
        preorderTraver(root, visitor);
    }

    /**
     * 前序遍历的实现，使用递归
     * @param node
     */
    protected void preorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor == null) return;
        //前序首先访问根节点
        visitor.visit(node.element);
        //访问左右子树
        preorderTraver(node.left, visitor);
        preorderTraver(node.right, visitor);
    }

    /**
     * 中序遍历，按照从小到大的顺序
     */
    public void inorderTraver(Visitor visitor){
        inorderTraver(root, visitor);
    }

    protected void inorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor == null) return;
        //中序遍历首先访问左子树
        inorderTraver(node.left, visitor);
        //然后访问自己
        visitor.visit(node.element);
        //最后访问右子树
        inorderTraver(node.right, visitor);
    }

    /**
     * 后序遍历对外提供的方法
     */
    public void postorderTraver(Visitor visitor){
        postorderTraver(root, visitor);
    }

    /**
     * 后序遍历的实现，使用递归
     * @param node
     */
    protected void postorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor == null) return;
        //前序首先访问左右树
        postorderTraver(node.left, visitor);
        postorderTraver(node.right, visitor);
        //再访问根节点
        visitor.visit(node.element);
    }

    /**
     * 层序遍历，使用迭代的方式
     * @param visitor
     */
    public void LevelOrderTraver(Visitor visitor){
        if (visitor == null || root == null) return;
        //使用while循环以及队列来访问每一层的元素
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> eNode = queue.poll();
            visitor.visit(eNode.element);
            if (eNode.left != null) queue.offer(eNode.left);
            if (eNode.right != null) queue.offer(eNode.right);
        }

    }

    /**
     * 对外提供一个计算树高的方法
     * @return
     */
    public int height(){
        return height(root);
    }

    /**
     * 计算树高方法实际的实现
     * @param node
     * @return
     */
    protected int height(Node<E> node){
        if (node == null) return 0;
        //使用递归方式
        //一颗树的高等于1+它的最大子节点的高度，这里使用递归对比左右子树，最后得出最高的
        //return 1 + Math.max(height(node.right), height(node.left));

        //使用迭代的方式
        //使用while循环以及队列来访问每一层的元素
        Queue<Node<E>> queue = new LinkedList<>();
        //树的高度
        int height = 0;
        //当前层的元素个数
        int levelSize = 1;
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> eNode = queue.poll();
            //每取出一个元素，当前层的元素就-1
            levelSize--;
            if (eNode.left != null) queue.offer(eNode.left);
            if (eNode.right != null) queue.offer(eNode.right);

            if (levelSize == 0){
                //当该层元素数量为0时则进入访问下一层,高度随之加一，levelSize等于下一层的元素数量
                levelSize = queue.size();
                height++;
            }
        }
        return height;
    }

    /**
     * 判断该树是否为完全二叉树，
     * 完全二叉树最多只能有一个度为1的节点，该节点只有左子树
     * @return
     */
    public boolean isComplete(){
        if (root == null) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        //标记，如果是叶子节点，则为true
        boolean isLeaf = false;
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> eNode = queue.poll();
            //如果
            if (isLeaf && !eNode.isLeaf()) return false;
            if (eNode.left != null && eNode.right != null) {
                //如果左右都不为空，则入队
                queue.offer(eNode.left);
                queue.offer(eNode.right);
            }else if (eNode.left == null && eNode.right != null){
                //如果左边为空，右边不为空，说明不是完全二叉树
                return false;
            }else{
                //程序走到这里说明接下来的节点只能是叶子节点
                /*
                    这里只剩两种情况
                    eNode.left != null && eNode.right == null
                    eNode.left == null && eNode.right == null
                 */
                isLeaf = true;
            }

        }
        return true;
    }

    protected static Node reConstructBinaryTree(int[] pre, int[] in){
        if (pre.length == 0 || in.length == 0)  return null;
        int root = pre[0];
        Node node = new Node(root);
        //声明一个变量用于区分左右子树，如果是负数，则说明此时应该外左子树中添加元素，如果为正数，则往右子树添加元素
        int position = -1;
        //声明两个动态数组，一个存放左子树中的元素，一个存放右子树中的元素
        List<Integer> leftIn = new ArrayList<>();
        List<Integer> rightIn = new ArrayList<>();
        for (int i = 0; i < in.length; i++){
            if (in[i] == root){
                position = i;
            }
            if (position < 0){
                leftIn.add(in[i]);
            }else if (position > 0 && i > position){
                rightIn.add(in[i]);
            }
        }
        int[] leftPre = new int[leftIn.size()];
        int[] rightPre = new int[rightIn.size()];
        System.arraycopy(pre, 1, leftPre, 0, leftIn.size());
        System.arraycopy(pre, leftPre.length + 1, rightPre, 0, rightIn.size());
        node.left = reConstructBinaryTree(leftPre, leftIn.stream().mapToInt(Integer::valueOf).toArray());
        node.right = reConstructBinaryTree(rightPre, rightIn.stream().mapToInt(Integer::valueOf).toArray());
        return node;
    }

    /**
     * 获取一个节点的前驱节点
     * 前驱节点是中序遍历时一个节点的前一个节点
     * @param node
     * @return
     */
    protected Node<E> predesessor(Node<E> node){

        //节点为空直接返回null
        if (node == null) return null;
        //第一种情况，左子树不为空
        if (node.left != null){
            node = node.left;
            while (node.right != null){
                node = node.right;
            }
            return node;
        }

        while (node.parent != null && node != node.parent.right){
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 获取一个节点的后继节点
     * @return
     */
    protected Node<E> successor(Node<E> node){
        if (node == null) return null;
        if (node.right != null){
            node = node.right;
            while (node.left != null){
                node = node.left;
            }
            return node;
        }
        while (node.parent != null && node != node.parent.left){
            node = node.parent;
        }
        return node.parent;

    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return (Node<E>) node;
    }
}
