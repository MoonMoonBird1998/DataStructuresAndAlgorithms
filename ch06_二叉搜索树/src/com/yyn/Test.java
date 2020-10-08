package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test {



    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] ints = {8,9,5,6,4,3,7,10,13,1,11};
        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }
        BinaryTrees.println(tree);
    }
}

