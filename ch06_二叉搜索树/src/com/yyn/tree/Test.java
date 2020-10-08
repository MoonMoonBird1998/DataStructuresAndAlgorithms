package com.yyn.tree;

import com.yyn.printer.BinaryTrees;

public class Test {
    /**
     * 测试删除功能
     */
    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] ints = {
                8,4,13,2,6,10,1,3,5,7,9,12,11
        };

        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }

        tree.remove(1);
        tree.remove(11);
        tree.remove(2);
        BinaryTrees.println(tree);
        tree.remove(8);
        tree.remove(13);
        tree.remove(9);
        tree.remove(12);
        tree.remove(10);
        BinaryTrees.println(tree);
        tree.remove(7);
        BinaryTrees.println(tree);
    }
}
