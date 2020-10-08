package com.yyn;

import java.util.Comparator;

public class RBTree<E> extends BinarySearchTree<E> {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    public RBTree(){
        this(null);
    }
    public RBTree(Comparator comparator){
        super(comparator);

    }

    private static class RBNode<E> extends Node<E>{

        boolean color = RED;

        public RBNode(E element, Node<E> parent){
            super(element,parent);
        }

        @Override
        public String toString() {
            return element.toString() + ",p=" + (parent == null ? null : parent.element)
                    + (color == RED ? "_R" : "_B") + ",兄弟=" + (sibling() == null ? null : (sibling().element));
        }
    }

    /**
     * 将节点染红
     * @param node
     * @return
     */
    private Node<E> red(Node<E> node){
        return color(node, RED);
    }

    /**
     * 将节点染黑
     * @param node
     * @return
     */
    private Node<E> black(Node<E> node){
        return color(node, BLACK);
    }

    private Node<E> color(Node<E> node, boolean color){
        if (node == null) return null;
        ((RBNode<E>) node).color = color;
        return node;
    }

    /**
     * 判断节点的颜色
     * @return
     */
    private boolean colorOf(Node<E> node){
        return node == null ? BLACK : ((RBNode<E> )node).color;
    }

    /**
     * 判断是否为黑色
     * @param node
     * @return
     */
    private boolean isBlack(Node<E> node){
        return colorOf(node) == BLACK;
    }

    /**
     * 判断是否为红色
     * @param node
     * @return
     */
    private boolean isRed(Node<E> node){
        return colorOf(node) == RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode<>(element, parent);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        //每次新插入节点就将该节点染红
        node = red(node);
        if (parent == null){
            //如果新添加的节点为根节点，将根节点染黑
            black(node);
            return;
        }
        //第一种情况，当父节点是黑色,此时不需要做任何处理
        if (isBlack(parent)) return;

        //第二种情况，父节点是红色，叔父节点是红色
        Node<E> uncle = parent.sibling();
        //因为接下来的每一种都要将祖父节点染红，因此在这里统一将祖父节点染红
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)){
            //将父节点和叔父节点染黑
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }

        if (isBlack(uncle)){//叔父节点不是红色
            if (parent.isLeftChild()){//L
                if (node.isLeftChild()){//LL
                    black(parent);
                    rotateRight(grand);
                }else {//LR
                    black(node);
                    rotateLeft(parent);
                    rotateRight(grand);
                }
            }else {//R
                if (node.isLeftChild()){//RL
                    black(node);
                    rotateRight(parent);
                    rotateLeft(grand);
                }else {//RR
                    black(parent);
                    rotateLeft(grand);
                }
            }
        }
    }

    private void rotateRight(Node<E> node){
        Node<E> parent = node.left;
        Node<E> child = parent.right;
        node.left = child;
        parent.right = node;
        afterRotate(node, parent, child);
    }

    private void rotateLeft(Node<E> node){
        Node<E> parent = node.right;
        Node<E> child = parent.left;
        node.right = child;
        parent.left = node;
        afterRotate(node, parent, child);
    }

    private void afterRotate(Node<E> node, Node<E> parent, Node<E> child){
        //更新parent
        parent.parent = node.parent;
        if (node.isLeftChild()){
            node.parent.left = parent;
        }else if (node.isRightChild()){
            node.parent.right = parent;
        }else {
            //说明node本来是根节点
            root = parent;
        }
        node.parent = parent;
        if (child != null){
            child.parent = node;
        }

    }

    @Override
    protected void afterRemove(Node<E> node) {
        //如果传入的节点是被删除节点的替代节点，如果为红色，则将其染黑，如果为黑色，则下面的if语句不执行
        //如果传入的节点是被删除的红色叶子节点，那么这个节点已经被删除了，即使将其染黑也没关系
        if (isRed(node)){
            //代替节点为红色,染黑
            black(node);
            return;
        }
        //程序走到这里说明要删除的节点是黑色，并且为叶子节点
        Node<E> parent = node.parent;
        if (parent == null) return;
        //黑兄弟
        //由于此时的node已经根父节点没有了关系，所以不能直接拿到兄弟节点，因此要在这里判断node是父节点的做还是右
        //如果是左，则兄弟节点是父节点的右子节点，反之为左子节点
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;

        if (left){//如果被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)){//红兄弟情况，转换为黑兄弟情况再进行处理
                //红兄弟
                red(parent);
                black(sibling);
                rotateLeft(parent);
                //更换兄弟
                sibling = parent.left;
            }
            if (isBlack(sibling.right) && isBlack(sibling.left)){
                boolean black = isBlack(parent);
                black(parent);
                red(sibling);
                if (black){
                    afterRemove(parent);
                }
            }else {
                if (isBlack(sibling.right)){
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(parent);
                black(sibling.right);
                rotateLeft(parent);
            }
        }else {//如果被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)){//红兄弟情况，转换为黑兄弟情况再进行处理
                //红兄弟
                red(parent);
                black(sibling);
                rotateRight(parent);
                //更换兄弟
                sibling = parent.left;
            }
            if (isBlack(sibling.right) && isBlack(sibling.left)){//如果黑兄弟左右都为黑（null），说明无法借节点
                //如果黑兄弟没有子节点，则让父节点向下与黑兄弟节点进行合并
                boolean black = isBlack(parent);
                black(parent);
                red(sibling);
                if (black){//如果一开始时父节点是黑色，则需要将父节点看作被删除的节点进行处理
                    afterRemove(parent);
                }
            }else {//黑兄弟至少有一个红节点
                //黑兄弟可以借节点
                if (isBlack(sibling.left)){
                    //如果兄弟节点的左边为黑色（null），则要对兄弟节点进行左旋转，使其符合下面的处理情况
                    rotateLeft(sibling);
                    sibling = parent.left;//此时兄弟节点会发生改变
                }
                color(sibling, colorOf(parent));
                black(parent);
                black(sibling.left);
                rotateRight(parent);
            }
        }
    }
}
