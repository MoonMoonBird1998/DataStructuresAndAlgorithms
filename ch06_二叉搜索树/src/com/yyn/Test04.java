package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test04 {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] ints = {
                8,4,13,2,6,10,1,3,5,7,9,12,11
        };

        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);

        System.out.println(tree.height());
        System.out.println(tree.isComplete());

    }
}
