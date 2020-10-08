package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test03 {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>();
        int[] ints = {
                8,4,13,2,6,10,1,3,5,7,9,12,11
        };

        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);
        for (int i = ints.length - 1; i >= 10; i--){
            tree.remove(ints[i]);
        }

        BinaryTrees.println(tree);

        tree.remove(13);
        BinaryTrees.println(tree);
        tree.remove(10);
        BinaryTrees.println(tree);
    }

}
