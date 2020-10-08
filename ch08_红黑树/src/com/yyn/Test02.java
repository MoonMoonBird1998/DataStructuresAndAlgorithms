package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test02 {
    /**
     * 测试删除功能
     */
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        int[] ints = {
                8,4,13,2,6,10,1,3,5,7,9,12,11
        };

        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);
        tree.remove(1);
        BinaryTrees.println(tree);
        tree.remove(2);
        BinaryTrees.println(tree);
        tree.remove(3);
        BinaryTrees.println(tree);
        tree.remove(12);
        BinaryTrees.println(tree);
        tree.remove(7);
        BinaryTrees.println(tree);
        tree.remove(8);
        BinaryTrees.println(tree);
    }
}
