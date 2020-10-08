package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test03 {
    /**
     * 测试删除功能
     */
    public static void main(String[] args) {
        RBTree<Integer> binaryTree = new RBTree<>();
        binaryTree.add(1);
        binaryTree.add(2);
        binaryTree.add(3);
        binaryTree.add(4);
        binaryTree.add(5);
        binaryTree.add(6);
        binaryTree.add(7);
        binaryTree.add(8);
        binaryTree.add(9);
        binaryTree.add(10);
        binaryTree.add(11);
        BinaryTrees.println(binaryTree);

    }
}
