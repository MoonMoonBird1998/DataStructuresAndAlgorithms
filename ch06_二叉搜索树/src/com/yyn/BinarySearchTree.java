package com.yyn;

import com.yyn.printer.BinaryTreeInfo;
import com.yyn.queue.Queue;

import java.util.*;

/**
 * 二叉搜索树
 */
public class BinarySearchTree<E> implements BinaryTreeInfo {

    private int size;

    /**
     * 根节点
     */
    private Node<E> root;

    /**
     * 比较器
     */
    private Comparator comparator;

    public BinarySearchTree(){
        this(null);
    }

    public BinarySearchTree(Comparator comparator){
        this.comparator = comparator;
    }

    public BinarySearchTree(int[] pre, int[] in){
        root = reConstructBinaryTree(pre,in);
    }

    /**
     * 定义私有类节点，拥有元素以及左节点和右节点，也就是左子树和右子树,以及父节点
     * @param <E>
     */
    private static class Node<E>{
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

        @Override
        public String toString() {
            return element.toString() + ",p=" + (parent == null ? null : parent.element);
        }
    }

    public void add(E element){
        //先检查参数是否为空
        checkElement(element);
        if (root == null){
            //此时说明是第一个节点
            root = new Node<>(element, null);
            size++;
        }else {
            Node<E> parent = root;
            Node<E> node = root;
            int result = 0;
            while (node != null){
                result = compare(element, node.element);
                parent = node;
                if (result < 0){
                    //寻找左子树
                    node = node.left;
                }else if (result > 0){
                    //寻找右子树
                    node = node.right;
                }else {
                    //相等则覆盖
                    node.element = element;
                    return;
                }
            }
            //在这里添加节点
            Node<E> newNode = new Node<>(element, parent);
            if (result > 0){
                parent.right = newNode;
            }else {
                parent.left = newNode;
            }
            size++;
        }
    }

    public void checkElement(E element){
        if (element == null){
            throw new IllegalArgumentException("element can't be null!");
        }
    }

    /**
     * 删除元素，先获取这个节点，然后删除这个节点
     * @param element
     */
    public void remove(E element){
        if (element == null) return;
        size--;
        remove(node(element));
    }

    /**
     *
     * @param node
     */
    private void remove(Node<E> node){
        if (node == null) return;
        if (node.right != null && node.left != null){
            //说明要删除度为2的节点
            //找到前驱或者后继节点
            Node<E> pre = predesessor(node);
            node.element = pre.element;
            node = pre;
        }
        //程序走到这里说明有两种可能：
        //1.要删除的节点度为1或者0
        //2.要删除的节点度为2，前面已经将要删除的节点替换为前驱节点，这里只需要删除前驱节点即可，前驱节点的度只能为1或者0
        //因此代码可以放到一起
        //这里用来判断应该用要删除的节点到底存在左节点还是右节点，又或者是没有节点，
        //如果有左或者右节点，那么用左/右节点取代原来的节点如果没有节点，那么不管三目运算符取哪一个结果，这个替换节点都为null
        Node<E> replacement = node.right == null ? node.left : node.right;
        if (replacement == null){
            //说明要删除的节点为叶子节点
            if (node.parent == null){
                //说明要删除的节点为根节点，并且是叶子节点
                root = null;
            }else if (node == node.parent.right){
                node.parent.right = null;
            }else {
                node.parent.left = null;
            }
        }else {
            //说明度为1，让替换节点的parent指向要删除节点的parent
            replacement.parent = node.parent;
            if (node.parent == null){
                //说明该节点为根节点
                root = replacement;
            }else if(node == node.parent.left){
                node.parent.left = replacement;

            }else {
                node.parent.right = replacement;
            }
        }
    }
    /**
     * 通过元素来获取节点
     * @param element
     * @return
     */
    private Node<E> node(E element){
        Node<E> node = root;
        while (node != null){
            int result = compare(element, node.element);
            if (result == 0) {
                return node;
            }else if (result < 0){
                node = node.left;
            }else {
                node = node.right;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    public void clear(){
        root = null;
        size = 0;
    }

    public boolean contains(E element){
        return node(element) != null;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     *  对外提供遍历的接口，支持前中后层四种遍历方式
     */
    public static abstract class Visitor<E>{
        //定义一个成员遍历，当stop为true时，停止遍历
        boolean stop;
        //外界调用者可以自定义对遍历结果的处理方式
        abstract boolean visit(E element);
    }
    /**
     * 前序遍历对外提供的方法
     */
    public void preorderTraver(Visitor visitor){
        if (visitor == null) return;
        preorderTraver(root, visitor);
    }

/*    private void preorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor == null || visitor.stop) return;
        //前序首先访问根节点
        visitor.stop = visitor.visit(node.element);
        //访问左右子树
        preorderTraver(node.left, visitor);
        preorderTraver(node.right, visitor);
    }*/

    /**
     * 使用非递归的方式进行前驱遍历
     * @param node
     * @param visitor
     */
    private void preorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor.stop) return;
        Stack<Node<E>> stack = new Stack<>();
        /*do {
            visitor.visit(node.element);
            if (node.right != null){
                stack.push(node.right);
            }
            if (node.left == null && !stack.isEmpty()){
                node = stack.pop();
                continue;
            }
            node = node.left;
        }while (node != null);*/
        while (true){
            if (node != null){
                visitor.visit(node);
                if (node.right != null){
                    stack.push(node.right);
                }
                node = node.left;
            }else if (!stack.isEmpty()){
                node = stack.pop();
            }else {
                return;
            }
        }
    }

    /**
     * 中序遍历，按照从小到大的顺序
     */
    public void inorderTraver(Visitor visitor){
        if (visitor == null) return;
        inorderTraver(root, visitor);
    }

/*    private void inorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor == null || visitor.stop) return;
        //中序遍历首先访问左子树
        inorderTraver(node.left, visitor);
        if (visitor.stop) return;//如果上一个递归中stop变为了true，那么下面就不应该再进行元素的打印了
        //然后访问自己
        visitor.stop = visitor.visit(node.element);
        //最后访问右子树
        inorderTraver(node.right, visitor);
    }*/

    private void inorderTraver(Node<E> node, Visitor visitor) {
        if (node == null || visitor.stop) return;
        Stack<Node<E>> stack = new Stack<>();
        while (true){
            if (node != null){
                stack.push(node);
                node = node.left;
            }else if (!stack.isEmpty()){
                node = stack.pop();
                visitor.visit(node);
                node = node.right;
            }else {
                return;
            }
        }
    }

    /**
     * 后序遍历对外提供的方法
     */
    public void postorderTraver(Visitor visitor){
        if (visitor == null) return;
        postorderTraver(root, visitor);
    }

    /**
     * 后序遍历的实现，使用递归
     * @param node
     */
/*    private void postorderTraver(Node<E> node, Visitor visitor){
        if (node == null || visitor.stop) return;
        //前序首先访问左右树
        postorderTraver(node.left, visitor);
        postorderTraver(node.right, visitor);
        //再访问根节点
        if (visitor.stop) return;//如果上一个递归中stop变为了true，那么下面就不应该再进行元素的打印了
        visitor.stop = visitor.visit(node.element);
    }*/

    private void postorderTraver(Node<E> node, Visitor visitor) {
        if (node == null || visitor.stop) return;
        //用来存储前一个弹出的元素
        Node<E> prev = null;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node<E> top = stack.peek();//查看栈顶部的元素
            if (top.isLeaf() || (prev != null && prev.parent == top)){
                //如果该节点为叶子节点，并且前一个弹出的元素是该元素的子节点，那么就访问该元素
                prev = stack.pop();
                visitor.visit(prev);
            }else {//如果不是叶子节点则将左右节点入栈
                if (top.right != null){
                    stack.push(top.right);
                }
                if (top.left != null){
                    stack.push(top.left);
                }
            }
        }
    }

    /**
     * 层序遍历，使用迭代的方式
     * @param visitor
     */
    public void LevelOrderTraver(Visitor visitor){
        if (visitor == null || root == null) return;
        //使用while循环以及队列来访问每一层的元素
        Node<E> node = root;
        java.util.Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<E> eNode = queue.poll();
            boolean stop = visitor.visit(eNode.element);
            if (stop) return;
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
    private int height(Node<E> node){
        if (node == null) return 0;
        //使用递归方式
        //一颗树的高等于1+它的最大子节点的高度，这里使用递归对比左右子树，最后得出最高的
        //return 1 + Math.max(height(node.right), height(node.left));

        //使用迭代的方式
        //使用while循环以及队列来访问每一层的元素
        java.util.Queue<Node<E>> queue = new LinkedList<>();
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
        java.util.Queue<Node<E>> queue = new LinkedList<>();
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

    private static Node reConstructBinaryTree(int[] pre, int[] in){
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
    private Node<E> predesessor(Node<E> node){

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
    private Node<E> successor(Node<E> node){
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

    /**
     * 定义一个用来比较元素的方法
     */
    public int compare(E e1, E e2){
        if (comparator == null){
            //如果比较器为null，则说明按照元素自己的比较规则来进行比较
            return ((Comparable<E>)e1).compareTo(e2);
        }
        //程序到此处说明比较器不为空，则按照比较器中的规则来进行比较
        return comparator.compare(e1, e2);
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
