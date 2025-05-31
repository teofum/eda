package com.itba.eda.BST;

public class BST<T extends Comparable<? super T>> implements BinaryTree<T, BSTNode<T>> {
    private BSTNode<T> root;
    private Order order = Order.ByLevel;

    public BSTNode<T> root() {
        return root;
    }

    public void insert(T data) {
        if (root == null)
            root = new BSTNode<T>(data);
        else
            root.insert(data);
    }

    public void delete(T data) {
        if (root != null)
            root = root.delete(data);
    }

    public Order order() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
