package com.itba.eda.BST;

public class AVLTree<T extends Comparable<? super T>> implements BinaryTree<T, AVLNode<T>> {
    private AVLNode<T> root;
    private Order order = Order.ByLevel;

    public AVLNode<T> root() {
        return root;
    }

    public void insert(T data) {
        root = switch (root) {
            case null -> new AVLNode<T>(data);
            case AVLNode<T> root -> root.insert(data);
        };
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
