package com.yyn;


/**
 * 测试删除功能
 */
public class Test06 {

    public static void main(String[] args) {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        int[] ints = {
                8, 4, 13, 2, 6, 10, 1, 3, 5, 7, 9, 12, 11
        };

        for (int i = 0; i < ints.length; i++) {
            tree.add(ints[i]);
        }

        tree.preorderTraver(new BinarySearchTree.Visitor<Integer>() {
            @Override
            boolean visit(Integer element) {
                if (element == 11) return true;
                System.out.print("-" + element + "-");
                return false;
            }
        });
    }
}
