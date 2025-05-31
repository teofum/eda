package com.itba.eda.BST;

public class BSTNode<T extends Comparable<? super T>> implements Node<T> {
    T data;
    BSTNode<T> left, right;

    public BSTNode(T data) {
        this.data = data;
    }

    public BSTNode<T> left() {
        return left;
    }

    public BSTNode<T> right() {
        return right;
    }

    public T data() {
        return data;
    }

    public BSTNode<T> insert(T data) {
        if (data.compareTo(this.data) > 0) {
            right = switch (right) {
                case null -> new BSTNode<T>(data);
                case BSTNode<T> right -> right.insert(data);
            };
        } else {
            left = switch (left) {
                case null -> new BSTNode<T>(data);
                case BSTNode<T> left -> left.insert(data);
            };
        }

        return this;
    }

    public BSTNode<T> delete(T data) {
        return switch ((Integer) data.compareTo(this.data)) {
            case 0 -> {
                // R1
                if (left == null && right == null)
                    yield null;

                // R2
                if (left == null)
                    yield right;
                if (right == null)
                    yield left;

                // R3
                T maxLeft = left.max();
                this.data = maxLeft;
                left = left.delete(maxLeft);
                yield this;
            }
            case Integer cmp when cmp > 0 -> {
                // Greater than data, delete from right subtree
                if (right != null)
                    right = right.delete(data);
                yield this;
            }
            default -> {
                // Less than data, delete from left subtree
                if (left != null)
                    left = left.delete(data);
                yield this;
            }
        };
    }
}
