package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test05 {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] ints = {
                8,4,13,2,6,10,1,3,5,7,9,12,11
        };

        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }
        /*tree.preorderTraver(new BinarySearchTree.Visitor() {
            @Override
            public void visit(Object element) {
                System.out.print(element + ", ");
            }
        });

        System.out.println("");
        tree.inorderTraver(new BinarySearchTree.Visitor() {
            @Override
            public void visit(Object element) {
                System.out.print(element + ", ");
            }
        });*/

        int[] pre = new int[]{8, 4, 2, 1, 3, 6, 5, 7, 13, 10, 9, 12, 11};
        int[] in = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

        BinarySearchTree<Integer> reTree = new BinarySearchTree<>(pre, in);
        BinaryTrees.println(reTree);

    }
}
