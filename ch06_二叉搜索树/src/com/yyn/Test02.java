package com.yyn;

import com.yyn.printer.BinaryTrees;

import java.util.Comparator;

public class Test02 {
    public static void main(String[] args) {
        BinarySearchTree<User> tree = new BinarySearchTree<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return ((User)o2).getAge() - ((User)o1).getAge();
            }
        });

        tree.add(new User(5,"jack"));
        tree.add(new User(4));
        tree.add(new User(3));
        tree.add(new User(8));
        tree.add(new User(2));
        tree.add(new User(6));
        tree.add(new User(1));
        User user = new User(5, "tom");
        tree.add(user);
        user.setAge(100);
        BinaryTrees.print(tree);
    }
}
