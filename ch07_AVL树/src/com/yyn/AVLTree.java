package com.yyn;

import java.util.Comparator;

public class AVLTree<E> extends BinarySearchTree<E>{

    public AVLTree(Comparator comparator){
        super(comparator);
    }

    public AVLTree(){
        this(null);
    }

    private static class AVLNode<E> extends Node<E>{
        //AVL树的高度
        int height = 1;

        public AVLNode(E element, Node<E> parent){
            super(element,parent);
        }

        @Override
        public String toString() {
            return element.toString() + ",p=" + (parent == null ? null : parent.element) + ",h=" + height;
        }

        /**
         * 计算平衡因子
         * 平衡因子 = 左子树高度 - 右子树高度
         * @return
         */
        public int balanceFactor(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return leftHeight - rightHeight;
        }

        public Node<E> tallerChild(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) return left;
            if (leftHeight < rightHeight) return right;
            //如果相等，则看父节点是左节点还是右节点，返回与父节点相同的方向
            return this.isLeftChild() ? left : right;
        }

        public void updateHeight(){
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height =1 +  Math.max(leftHeight, rightHeight);
        }

    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null){
            //判断是否平衡
            if (isBalance(node)){
                //如果平衡，则更新高度
                updateHeight(node);
            }else {
                reBalance(node);
                //恢复平衡后结束循环
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null){
            //判断是否平衡
            if (isBalance(node)){
                //如果平衡，则更新树的高度
                updateHeight(node);
            }else {
                reBalance(node);
            }
        }
    }

    /**
     * 判断是否平衡
     * @param node
     * @return
     */
    private boolean isBalance(Node<E> node){
        //如果平衡因子 <= 1说明这棵树是平衡的
        return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
    }

    /**
     * 恢复平衡
     *
     */
    private void reBalance(Node<E> grand){
        //拿到该节点最高的子节点和孙子节点
        //之所以是最高是因为最高的节点导致了不平衡
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> child = ((AVLNode<E>) parent).tallerChild();
        //判断是四种情况下的哪一种
        //LL LR RR RL
        if (parent.isLeftChild()){
            if (child.isLeftChild()){
                //LL,需要右旋转
                rotateRight(grand);

            }else {//LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        }else {
            if (child.isRightChild()){//RR
                rotateLeft(grand);
            }else {//RL
                rotateRight(parent);
                rotateLeft(grand);
            }
        }
    }

    /**
     * 更新树的高度
     * @param node
     */
    private void updateHeight(Node<E> node){
        //树的高度 = 左子树和右子树中高度较大的那一个再加上1
        AVLNode<E> avlNode = (AVLNode<E>) node;
        avlNode.updateHeight();
    }



    private void rotateRight(Node<E> node){
        Node<E> parent = node.left;
        Node<E> child = parent.right;
        node.left = child;
        parent.right = node;
        //旋转之后重新设置parent以及更新高度
        afterRotate(node, parent, child);
    }

    private void rotateLeft(Node<E> node){
        Node<E> parent = node.right;
        Node<E> child = parent.left;
        node.right = child;
        parent.left = node;//旋转之后重新设置parent以及更新高度

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

        //更新高度
        updateHeight(node);
        updateHeight(parent);
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }
}
