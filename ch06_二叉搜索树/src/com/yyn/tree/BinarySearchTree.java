package com.yyn.tree;

import com.yyn.printer.BinaryTreeInfo;

import java.util.*;

/**
 * 二叉搜索树
 */
public class BinarySearchTree<E> extends BinaryTree {

    public BinarySearchTree(){
        super(null);
    }

    public BinarySearchTree(Comparator comparator){
        super(comparator);
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



    public boolean contains(E element){
        return node(element) != null;
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


}
