package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test03 {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] ints = {
                8,4,13,2,6,10,1,3,5,7,9,12,11
        };

        for (int i = 0; i < ints.length; i++){
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);
        /*tree.preorderTraver(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print("-" + element + "-");
            }
        });*/
        /*tree.inorderTraver(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print("-" + element + "-");
            }
        });*/

        /*tree.postorderTraver(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public void visit(Integer element) {
                System.out.print("-" + element + "-");
            }
        });*/
        tree.LevelOrderTraver(new BinarySearchTree.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.print("-" + element + "-");
                return false;
            }
        });
    }
}
