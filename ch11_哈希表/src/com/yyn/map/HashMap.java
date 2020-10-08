package com.yyn.map;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yyn.printer.BinaryTreeInfo;
import com.yyn.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 哈希表映射,借助红黑树来实现
 * @param <K>
 * @param <V>
 */
public class HashMap<K,V> implements Map<K,V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size;
    /**
     * 数组，每个位置存储红黑树的根节点
     */
    private Node<K,V>[] table;
    /**
     * 数组的默认容量
     */
    private static final int DEFAULT_CAPACITY = 1 << 4;

    /**
     * 默认加载因子，当默认加载因子大与0.75时将哈希表进行扩容
     */
    private static final float DEFAULT_LOAD_FACTORY = 0.75f;

    public HashMap(){
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        for (int i = 0; i < table.length; i++){
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        //每次添加之前都调用resize方法来判断是否需要扩容
        resize();
        //通过key来计算哈希值
        int index = index(key);
        //根据索引取出根节点
        Node<K,V> root = table[index];
        if (root == null) {
            //此时说明是第一个节点
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
        } else {
            Node<K, V> parent = root;
            Node<K, V> node = root;
            int result = 0;
            int k1Hash = key == null ? 0 : key.hashCode();
            //当找到key相同的元素时，用resultNode来存储
            Node<K,V> resultNode = null;
            //标记，用于循环中对所有节点的查找，当已经查询一次后就不再查询
            boolean searched = false;
            //通过比较哈希值来确定往左还是往右添加
            do {
                parent = node;
                int k2Hash = node.hashCode;
                if (k1Hash < k2Hash){
                    result = -1;
                }else if (k1Hash > k2Hash){
                    result = 1;
                }else {//说明哈希值相等
                    if (Objects.equals(key,node.key)){//说明这两个key相等
                        result = 0;
                    }else if (key != null && node.key != null && key.getClass() == node.key.getClass() &&
                            key instanceof Comparable && (result = ((Comparable) key).compareTo(node.key)) != 0){
                    }else if (!searched){
                        if((node.left != null && (resultNode = node(node.left, key)) != null)
                            || ( node.left != null && (resultNode = node(node.left, key)) != null)){
                            node = resultNode;
                            result = 0;
                        }else {//当来到这里说明第一次循环没有找到相同key的节点，因此将标记转为true，表示已经搜索过，下次循环不用再进行搜索
                            searched = true;
                            result = System.identityHashCode(key) - System.identityHashCode(node.key);
                        }
                    }else {
                        result = System.identityHashCode(key) - System.identityHashCode(node.key);
                    }
                }
                //比较完成后对比较结果进行处理
                if (result < 0){
                    node = node.left;
                }else if (result > 0){
                    node = node.right;
                }else {
                    V oldValue = node.value;
                    node.key = key;
                    node.value = value;
                    return oldValue;
                }
            }while (node != null);

            Node<K, V> newNode = new Node<>(key, value, parent);
            if (result > 0) {
                parent.right = newNode;
            } else {
                parent.left = newNode;
            }
            afterPut(newNode);
            size++;
        }
        return null;
    }

    private void resize() {
        if ((float) size / table.length <= DEFAULT_LOAD_FACTORY) return;
        //如果元素个数/数组长度 > 0.75则进行扩容
        Node<K,V>[] oldTable = table;
        table = new Node[table.length << 1];//扩容为之前的两倍
        //要将之前的节点全部移动到新的数组中,使用for循环和层序遍历
        Queue<Node<K,V>> queue = new LinkedList<>();
        Node<K,V> node = null;//用来存放每一次循环中入队的节点
        for (int i = 0; i < oldTable.length; i++){
            node = oldTable[i];
            if (node == null) continue;
            queue.offer(node);
            while (!queue.isEmpty()){
                node = queue.poll();
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
                //在移动之前将这个节点的左右节点和父节点都清空
                node.parent = null;
                node.right = null;
                node.left = null;
                //移动节点
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K,V> newNode){
        //每次添加之前都调用resize方法来判断是否需要扩容
        resize();
        //通过key来计算哈希值
        int index = index(newNode);
        //根据索引取出根节点
        Node<K,V> root = table[index];
        if (root == null) {
            //此时说明是第一个节点
            root = newNode;
            table[index] = root;
            afterPut(root);
        } else {
            Node<K, V> parent = root;
            Node<K, V> node = root;
            int result = 0;
            K key = newNode.key;
            int k1Hash = key == null ? 0 : key.hashCode();
            //通过比较哈希值来确定往左还是往右添加
            do {
                parent = node;
                int k2Hash = node.hashCode;
                if (k1Hash < k2Hash){
                    result = -1;
                }else if (k1Hash > k2Hash){
                    result = 1;
                }else if (key != null && node.key != null && key.getClass() == node.key.getClass() &&
                            key instanceof Comparable && (result = ((Comparable) key).compareTo(node.key)) != 0){
                }else {
                        result = System.identityHashCode(key) - System.identityHashCode(node.key);
                }
                //比较完成后对比较结果进行处理
                if (result < 0){
                    node = node.left;
                }else if (result > 0){
                    node = node.right;
                }
            }while (node != null);

            newNode.parent = parent;
            if (result > 0) {
                parent.right = newNode;
            } else {
                parent.left = newNode;
            }
            afterPut(newNode);
        }
    }

    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        //每次新插入节点就将该节点染红
        node = red(node);
        if (parent == null) {
            //如果新添加的节点为根节点，将根节点染黑
            black(node);
            return;
        }
        //第一种情况，当父节点是黑色,此时不需要做任何处理
        if (isBlack(parent)) return;

        //第二种情况，父节点是红色，叔父节点是红色
        Node<K, V> uncle = parent.sibling();
        //因为接下来的每一种都要将祖父节点染红，因此在这里统一将祖父节点染红
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) {
            //将父节点和叔父节点染黑
            black(parent);
            black(uncle);
            afterPut(grand);
            return;
        }

        if (isBlack(uncle)) {//叔父节点不是红色
            if (parent.isLeftChild()) {//L
                if (node.isLeftChild()) {//LL
                    black(parent);
                    rotateRight(grand);
                } else {//LR
                    black(node);
                    rotateLeft(parent);
                    rotateRight(grand);
                }
            } else {//R
                if (node.isLeftChild()) {//RL
                    black(node);
                    rotateRight(parent);
                    rotateLeft(grand);
                } else {//RR
                    black(parent);
                    rotateLeft(grand);
                }
            }
        }
    }

    private void rotateRight(Node<K, V> node) {
        Node<K, V> parent = node.left;
        Node<K, V> child = parent.right;
        node.left = child;
        parent.right = node;
        afterRotate(node, parent, child);
    }

    private void rotateLeft(Node<K, V> node) {
        Node<K, V> parent = node.right;
        Node<K, V> child = parent.left;
        node.right = child;
        parent.left = node;
        afterRotate(node, parent, child);
    }

    private void afterRotate(Node<K, V> node, Node<K, V> parent, Node<K, V> child) {
        //更新parent
        parent.parent = node.parent;
        if (node.isLeftChild()) {
            node.parent.left = parent;
        } else if (node.isRightChild()) {
            node.parent.right = parent;
        } else {
            //说明node本来是根节点
            table[index(node)] = parent;
        }
        node.parent = parent;
        if (child != null) {
            child.parent = node;
        }
    }

    /**
     * 将节点染红
     *
     * @param node
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 将节点染黑
     *
     * @param node
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    /**
     * 判断节点的颜色
     *
     * @return
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 判断是否为黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 判断是否为红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++){
            Node<K,V> node = table[i];
            if (node == null) continue;
            queue.offer(node);
            while (!queue.isEmpty()){
                node = queue.poll();
                if (node.value == value) return true;
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0) return;
        Queue<Node<K,V>> queue = new LinkedList<>();
        for (int i = 0; i < table.length; i++){
            Node<K,V> node = table[i];
            if (node == null) continue;
            queue.offer(node);
            while (!queue.isEmpty()){
                node = queue.poll();
                visitor.visit(node.key, node.value);
                if (node.left != null){
                    queue.offer(node.left);
                }
                if (node.right != null){
                    queue.offer(node.right);
                }
            }
        }
        return;
    }

    private int index(K key){
        if (key == null) return 0;//如果key为空，则返回0位置的索引
        int hash = key.hashCode();
        //通过哈希值与数组长度计算出索引位置
        return (hash ^ (hash >> 16)) & (table.length - 1);
    }
    private int index(Node<K,V> node){
        if (node == null) return 0;//如果key为空，则返回0位置的索引
        int hash = node.hashCode;
        //通过哈希值与数组长度计算出索引位置
        return (hash ^ (hash >> 16)) & (table.length - 1);
    }

    private Node<K, V> predesessor(Node<K, V> node) {

        //节点为空直接返回null
        if (node == null) return null;
        //第一种情况，左子树不为空
        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        while (node.parent != null && node != node.parent.right) {
            node = node.parent;
        }
        return node.parent;
    }

    private V remove(Node<K,V> node){
        if (node == null) return null;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChild()){
            //说明要删除度为2的节点
            //找到前驱或者后继节点
            Node<K,V> prev = predesessor(node);
            node.key = prev.key;
            node.value = prev.value;
            node.hashCode = prev.hashCode;
            node = prev;
        }
        Node<K,V> replacement = node.right == null ? node.left : node.right;
        int index = index(node);//根节点的索引
        if (replacement == null){
            //说明要删除的节点为叶子节点
            if (node.parent == null){
                //说明要删除的节点为根节点，并且是叶子节点
                table[index] = null;
            }else if (node.isRightChild()){
                node.parent.right = null;
            }else {
                node.parent.left = null;
            }
            afterRemove(node);
        }else {
            //说明度为1，让替换节点的parent指向要删除节点的parent
            replacement.parent = node.parent;
            if (node.parent == null){
                //说明该节点为根节点
                table[index] = replacement;
            }else if(node.isLeftChild()){
                node.parent.left = replacement;

            }else {
                node.parent.right = replacement;
            }
            //如果要删除的是度为1的节点，这里将替代被删除节点的节点传入，对于AVL树来说没有什么影响，因为他们的父节点都是被删除节点的父节点
            //对于红黑树来说，传入后会判断被删除节点的替代节点的颜色
            afterRemove(replacement);
        }
        return oldValue;
    }

    private void afterRemove(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }

        Node<K, V> parent = node.parent;
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateLeft(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rotateRight(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private Node<K,V> node(Node<K,V> node, K key){
        int result = 0;
        //用来存储被找到的节点
        Node<K,V> resultNode = null;
        int key1Hash = key == null ? 0 : key.hashCode();
        while (node != null){
            int key2Hash = node.hashCode;
            if (key1Hash < key2Hash){
                node = node.left;
            }else if (key1Hash > key2Hash){
                node = node.right;
            }else if (Objects.equals(key, node.key)) {
                return node;
            }else if (key != null && node.key != null
                        && key.getClass() == node.key.getClass()
                        && key instanceof Comparable
                        && (result = ((Comparable) key).compareTo(node.key)) != 0){
                node = result > 0 ? node.right : node.left;
            }else if (node.left != null && (resultNode = node(node.left, key)) != null){
                    return resultNode;
            }else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * 通过key找到node
     * @param key
     * @return
     */
    private Node<K,V> node(K key){
        Node<K,V> root = table[index(key)];
        return root == null ? null : node(root, key);
    }


    private static class Node<K, V> {
        int hashCode;
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;


        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hashCode = key.hashCode();
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return right == null && left == null;
        }

        /**
         * 寻找兄弟节点
         *
         * @return
         */
        public Node<K, V> sibling() {
            if (isRightChild()) {
                return parent.left;
            }

            if (isLeftChild()) {
                return parent.right;
            }

            return null;
        }

        /**
         * 判断是否为左子树
         *
         * @return
         */
        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        /**
         * 判断是否为右子树
         *
         * @return
         */
        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public boolean hasTwoChild() {
            return right != null && left != null;
        }

        @Override
        public String toString() {
            return "node_" + key + "=" + value;
        }
    }

    public void print(){
        if (size == 0) return;
        for (int i = 0; i < table.length; i++){
            final Node<K,V> root = table[i];
            System.out.println("位置" + "=" + i);
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K,V>)node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K,V>)node).right;
                }

                @Override
                public Object string(Object node) {
                    return node;
                }
            });
        }
    }
}
