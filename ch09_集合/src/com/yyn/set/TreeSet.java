package com.yyn.set;

import com.yyn.tree.BinaryTree;
import com.yyn.tree.RBTree;

public class TreeSet<E> implements Set<E> {

    RBTree<E> tree = new RBTree<>();

    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return tree.contains(element);
    }

    @Override
    public void add(E element) {
        tree.add(element);
    }

    @Override
    public E remove(E element) {
        return tree.remove(element);
    }

    @Override
    public void traversal(Visitor visitor) {
        tree.inorderTraver(new BinaryTree.Visitor<E>() {
            @Override
            public void visit(E element) {
                visitor.visit(element);
            }
        });
    }
}
