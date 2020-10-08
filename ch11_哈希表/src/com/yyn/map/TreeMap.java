package com.yyn.map;



import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;


public class TreeMap<K,V> implements Map<K,V> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private int size;

    /**
     * 根节点
     */
    private Node<K, V> root;

    private Comparator comparator;

    public TreeMap() {
        this(null);
    }

    public TreeMap(Comparator comparator) {
        this.comparator = comparator;

    }

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;
        boolean color = RED;


        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
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

    }

    private int compare(K e1, K e2) {
        if (comparator == null) {
            //如果比较器为null，则说明按照元素自己的比较规则来进行比较
            return ((Comparable<K>) e1).compareTo(e2);
        }
        //程序到此处说明比较器不为空，则按照比较器中的规则来进行比较
        return comparator.compare(e1, e2);
    }

    private void checkKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key can't be null!");
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
            root = parent;
        }
        node.parent = parent;
        if (child != null) {
            child.parent = node;
        }

    }

    private V remove(Node<K,V> node){
        if (node == null) return null;
        size--;
        V oldValue = node.value;
        if (node.hasTwoChild()){
            //说明要删除度为2的节点
            //找到前驱或者后继节点
            Node<K,V> pre = predesessor(node);
            node.key = pre.key;
            node.value = pre.value;
            node = pre;
        }
        //程序走到这里说明有两种可能：
        //1.要删除的节点度为1或者0
        //2.要删除的节点度为2，前面已经将要删除的节点替换为前驱节点，这里只需要删除前驱节点即可，前驱节点的度只能为1或者0
        //因此代码可以放到一起
        //这里用来判断应该用要删除的节点到底存在左节点还是右节点，又或者是没有节点，
        //如果有左或者右节点，那么用左/右节点取代原来的节点如果没有节点，那么不管三目运算符取哪一个结果，这个替换节点都为null
        Node<K,V> replacement = node.right == null ? node.left : node.right;
        if (replacement == null){
            //说明要删除的节点为叶子节点
            if (node.parent == null){
                //说明要删除的节点为根节点，并且是叶子节点
                root = null;
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
                root = replacement;
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

    /**
     * 中序遍历，按照从小到大的顺序
     */
    public void inorderTraver(Visitor visitor) {
        inorderTraver(root, visitor);
    }

    private void inorderTraver(Node<K, V> node, Visitor visitor) {
        if (node == null || visitor == null) return;
        //中序遍历首先访问左子树
        inorderTraver(node.left, visitor);
        //然后访问自己
        visitor.visit(node.key, node.value);
        //最后访问右子树
        inorderTraver(node.right, visitor);
    }


    /**
     * 获取一个节点的前驱节点
     * 前驱节点是中序遍历时一个节点的前一个节点
     *
     * @param node
     * @return
     */
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

    /**
     * 获取一个节点的后继节点
     *
     * @return
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        while (node.parent != null && node != node.parent.left) {
            node = node.parent;
        }
        return node.parent;

    }

    private Node<K, V> node(K key) {
        Node<K, V> node = root;
        while (node != null) {
            int result = compare(key, node.key);
            if (result == 0) {
                return node;
            } else if (result < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
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
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        //先检查参数是否为空
        checkKey(key);
        if (root == null) {
            //此时说明是第一个节点
            root = new Node<>(key, value, null);
            size++;
            afterPut(root);
        } else {
            Node<K, V> parent = root;
            Node<K, V> node = root;
            int result = 0;
            while (node != null) {
                result = compare(key, node.key);
                parent = node;
                if (result < 0) {
                    //寻找左子树
                    node = node.left;
                } else if (result > 0) {
                    //寻找右子树
                    node = node.right;
                } else {
                    //相等则覆盖
                    node.key = key;
                    V oldValue = node.value;
                    node.value = value;
                    return oldValue;
                }
            }
            //在这里添加节点
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

    @Override
    public V get(K key) {
        Node<K,V> node = node(key);
        return node == null ? null : node.value;
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
        if (root == null) return false;

        Queue<Node<K,V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node<K,V> node = queue.poll();
            if (compareVal(value, node.value)) return true;

            if (node.left != null){
                queue.offer(node.left);
            }
            if (node.right != null){
                queue.offer(node.right);
            }

        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        traversal(root, visitor);
    }

    public boolean compareVal(V v1, V v2){
        return v1 == null ? v2 == null : v1.equals(v2);
    }


    private void traversal(Node<K,V> node, Visitor visitor){
        if (node == null) return;
        inorderTraver(node.left, visitor);
        visitor.visit(node.key, node.value);
        inorderTraver(node.right, visitor);

    }
}
