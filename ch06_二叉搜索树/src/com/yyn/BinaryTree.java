package com.yyn;

import com.yyn.printer.BinaryTreeInfo;
import com.yyn.printer.BinaryTrees;
import com.yyn.queue.Queue;


public class BinaryTree<Key extends Comparable<Key>, Value> implements BinaryTreeInfo{
    //记录根结点
    private Node root;
    //记录树中元素的个数
    private int N;


    private class Node {
        //存储键
        public Key key;
        //存储值
        private Value value;
        //记录左子结点
        public Node left;
        //记录右子结点
        public Node right;

        public Node(Key key, Value value, Node left, Node right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return key + "==" + value;
        }

    }

    //获取树中元素的个数
    public int size() {
        return N;
    }

    //向树中添加元素key-value
    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    //向指定的树x中添加key-value,并返回添加元素后新的树
    private Node put(Node x, Key key, Value value) {
        if (x == null){
            //说明当前传入的子节点没有值，将新的节点创建并赋值
            x = new Node(key, value ,null, null);
            N++;
            return x;
        }

        //与当前节点进行比较
        int cmp = key.compareTo(x.key);

        if (cmp > 0){
            //如果大与当前节点，则往右子树找
            x.right = put(x.right, key, value);
        }else if (cmp < 0){
            //如果小于当前节点，则往左子树找
            x.left = put(x.left, key, value);
        }else {
            //等于时则说明key已经存在，则替换掉原来的value
            x.value = value;
        }
        return x;
    }

    //查询树中指定key对应的value
    public Value get(Key key) {
        return get(root, key);
    }

    //从指定的树x中，查找key对应的值
    public Value get(Node x, Key key) {
        if (x == null){
            return null;
        }

        //与当前节点进行比较
        int cmp = key.compareTo(x.key);
        if (cmp > 0){
            //如果大与当前节点，则往右子树找
            return get(x.right, key);

        }else if (cmp < 0){
            //如果小于当前节点，则往左子树找
            return get(x.left, key);
        }else {
            //等于说明找到，返回对应的value
            return x.value;
        }
    }


    //删除树中key对应的value
    public void delete(Key key) {
        root = delete(root, key);
    }

    //删除指定树x中的key对应的value，并返回删除后的新树
    private Node delete(Node x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if (cmp > 0){
            //如果大与当前节点，则往右子树找
            x.right = delete(x.right, key);

        }else if (cmp < 0){
            //如果小于当前节点，则往左子树找
            x.left = delete(x.left, key);
        }else {
            N--;
            //真正的删除代码
            //先判断左右节点是否存在
            if (x.left == null){
                return x.right;
            }
            if (x.right == null){
                return x.left;
            }
            //如果左右节点都存在,则找到后继节点来代替被删除的元素
            //寻找后继节点
            Node min = x.right;
            Node pre = x;//前驱节点的父节点
            //如果右子树没有左子树，则后继节点就是右子树的根节点
            if (min.left == null){
                min.left = pre.left;
                x = min;
                return x;
            }
            //如果有左子树，则找到最左的左子树
            while (min.left != null){
                pre = min;
                min = min.left;
            }
            //交换位置
            min.left = x.left;
            min.right = x.right;
            x = min;
            //找到后继节点的父节点，使其指向null
            pre.left = null;
        }
        return x;
    }

    //查找整个树中最小的键+
    public Key min(){
        return min(root).key;
    }

    //在指定树x中找出最小键所在的结点
    private Node min(Node x){

        if (x.left != null){
            return min(x.left);
        }
        return x;

    }

    //在整个树中找到最大的键
    public Key max(){
        return max(root).key;
    }

    //在指定的树x中，找到最大的键所在的结点
    public Node max(Node x){
        if (x.right != null){
            return max(x.right);
        }
        return x;
    }

    //获取整个树中所有的键
    public Queue<Key> preErgodic(){
        Queue<Key> keys = new Queue<>();

        preErgodic(root, keys);
        return keys;
    }

    //获取指定树x的所有键，并放到keys队列中
    private void preErgodic(Node x,Queue<Key> keys){
        if (x == null) return;
        //访问当前根节点
        keys.enQueue(x.key);
        //访问左子树
        preErgodic(x.left,keys);
        //访问右子树
        preErgodic(x.right, keys);

    }

    //使用中序遍历获取树中所有的键
    public Queue<Key> midErgodic(){
        Queue<Key> keys = new Queue<>();

        midErgodic(root, keys);
        return keys;
    }

    //使用中序遍历，获取指定树x中所有的键，并存放到key中
    private void midErgodic(Node x,Queue<Key> keys){
        if (x == null) return;
        //访问左子树
        midErgodic(x.left,keys);
        //访问当前根节点
        keys.enQueue(x.key);
        //访问右子树
        midErgodic(x.right, keys);

    }

    //使用后序遍历，把整个树中所有的键返回
    public Queue<Key> afterErgodic(){
        Queue<Key> keys = new Queue<>();

        afterErgodic(root, keys);
        return keys;
    }

    //使用后序遍历，把指定树x中所有的键放入到keys中
    private void afterErgodic(Node x,Queue<Key> keys){
        if (x == null) return;
        //访问左子树
        afterErgodic(x.left,keys);

        //访问右子树
        afterErgodic(x.right, keys);

        //访问当前根节点
        keys.enQueue(x.key);
    }


    //使用层序遍历，获取整个树中所有的键
    public Queue<Key> layerErgodic(){
        //需要两个队列
        Queue<Key> keys = new Queue<>();
        Queue<Node> queue = new Queue<>();
        Node n = root;
        queue.enQueue(root);
        while (!queue.empty()){
            n = queue.deQueue();
            keys.enQueue(n.key);
            if (n.left != null){
                queue.enQueue(n.left);
            }
            if (n.right != null){
                queue.enQueue(n.right);
            }
        }
        return keys;
    }


    //获取整个树的最大深度
    public int maxDepth(){
        return maxDepth(root);
    }


    //获取指定树x的最大深度
    private int maxDepth(Node x){
        if (x == null) return 0;
        //分别获取左右子树的深度，取最大的一个就是树的深度
        int max = 0;
        int maxL = 0;
        int maxR = 0;
        if (x.left != null){
            maxL = maxDepth(x.left);
        }
        if (x.right != null){
            maxR = maxDepth(x.right);
        }
        max = max + maxL >= maxR ? maxL+1 : maxR+1;
        return max;
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((BinaryTree.Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((BinaryTree.Node) node).right;
    }

    @Override
    public Object string(Object node) {
        return (BinaryTree.Node) node;
    }


    public static void main(String[] args) {
        BinaryTree<Integer, String> binaryTree = new BinaryTree<>();

        binaryTree.put(5,"张三");
        binaryTree.put(8,"李四");
        binaryTree.put(2,"王五");
        binaryTree.put(1,"赵六");
        binaryTree.put(7,"孙七");
        binaryTree.put(10,"刘八");
        binaryTree.put(3,"刘能");
        binaryTree.put(11,"张能");
        binaryTree.put(9,"吴能");
        binaryTree.put(6,"松江");
        BinaryTrees.println(binaryTree);
        System.out.println(binaryTree.maxDepth());
    }

}

