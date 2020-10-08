package com.yyn;

import com.yyn.printer.BinaryTrees;

public class Test07 {
    static void test01(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        int[] ints = {
                8, 4, 13, 2, 6, 10, 1, 3, 5, 7, 9, 12, 11
        };

        for (int i = 0; i < ints.length; i++) {
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);
        tree.preorderTraver(new BinarySearchTree.Visitor() {
            @Override
            boolean visit(Object element) {
                System.out.println(element);
                return false;
            }
        });
    }

    static void test02(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        int[] ints = {
                8, 4, 13, 2, 6, 10, 1, 3, 5, 7, 9, 12, 11
        };

        for (int i = 0; i < ints.length; i++) {
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);
        tree.inorderTraver(new BinarySearchTree.Visitor() {
            @Override
            boolean visit(Object element) {
                System.out.println(element);
                return false;
            }
        });
    }

    static void test03(){
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        int[] ints = {
                8, 4, 13, 2, 6, 10, 1, 3, 5, 7, 9, 12, 11
        };

        for (int i = 0; i < ints.length; i++) {
            tree.add(ints[i]);
        }

        BinaryTrees.println(tree);
        tree.postorderTraver(new BinarySearchTree.Visitor() {
            @Override
            boolean visit(Object element) {
                System.out.println(element);
                return false;
            }
        });
    }
    public static void main(String[] args) {
        test03();
    }
}
