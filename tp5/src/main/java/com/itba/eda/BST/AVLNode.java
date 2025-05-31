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
        int cmp = data.compareTo(this.data);
        if (cmp == 0) {
            return this;
        } else if (cmp > 0) {
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
                    left = left.rotateLeft(); // LR
                yield rotateRight(); // LL
            }
            case Integer b when b < -1 -> {
                if (data.compareTo(right.data) < 0)
                    right = right.rotateRight(); // RL
                yield rotateLeft(); // RR
            }
            default -> this;
        };
    }

    public AVLNode<T> delete(T data) {
        var self = switch ((Integer) data.compareTo(this.data)) {
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

        if (self == null)
            return null;

        self.updateHeight();

        return switch ((Integer) self.balance()) {
            case Integer b when b > 1 -> {
                if (self.left.balance() < 0)
                    self.left = self.left.rotateLeft(); // LR
                yield self.rotateRight(); // LL
            }
            case Integer b when b < -1 -> {
                if (self.right.balance() > 0)
                    self.right = self.right.rotateRight(); // RL
                yield self.rotateLeft(); // RR
            }
            default -> self;
        };
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
