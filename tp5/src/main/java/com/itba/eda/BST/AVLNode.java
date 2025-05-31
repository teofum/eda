package com.itba.eda.BST;

public class AVLNode<T extends Comparable<? super T>> implements Node<T> {
    T data;
    int height;
    AVLNode<T> left, right;

    public AVLNode(T data) {
        this.data = data;
        this.height = 1;
    }

    public AVLNode<T> left() {
        return left;
    }

    public AVLNode<T> right() {
        return right;
    }

    public T data() {
        return data;
    }

    public String valueString() {
        return data.toString() + " : " + balance();
    }

    public AVLNode<T> insert(T data) {
        if (data.compareTo(this.data) > 0) {
            right = switch (right) {
                case null -> new AVLNode<T>(data);
                case AVLNode<T> right -> right.insert(data);
            };
        } else {
            left = switch (left) {
                case null -> new AVLNode<T>(data);
                case AVLNode<T> left -> left.insert(data);
            };
        }

        updateHeight();

        return switch ((Integer) balance()) {
            case Integer b when b > 1 -> {
                if (data.compareTo(left.data) > 0)
                    left = left.rotateLeft();
                yield rotateRight();
            }
            case Integer b when b < -1 -> {
                if (data.compareTo(right.data) < 0)
                    right = right.rotateRight();
                yield rotateLeft();
            }
            default -> this;
        };
    }

    public AVLNode<T> delete(T data) {
        throw new RuntimeException("Not implemented");
    }

    private AVLNode<T> rotateLeft() {
        var oldRight = right;
        right = oldRight.left;
        oldRight.left = this;

        updateHeight();
        oldRight.updateHeight();

        return oldRight;
    }

    private AVLNode<T> rotateRight() {
        var oldLeft = left;
        left = oldLeft.right;
        oldLeft.right = this;

        updateHeight();
        oldLeft.updateHeight();

        return oldLeft;
    }

    private int balance() {
        return getHeight(left) - getHeight(right);
    }

    private void updateHeight() {
        height = 1 + Math.max(getHeight(left), getHeight(right));
    }

    private static <T extends Comparable<? super T>> int getHeight(AVLNode<T> node) {
        return node == null ? 0 : node.height;
    }
}
