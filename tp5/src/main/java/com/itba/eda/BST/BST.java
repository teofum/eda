package com.itba.eda.BST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<T extends Comparable<? super T>> implements Iterable<T> {
    public static enum Order {
        ByLevel, InOrder
    };

    private Node root;
    private Order order = Order.ByLevel;

    public void insert(T data) {
        if (root == null)
            root = new Node(data);
        else
            root.insert(data);
    }

    public boolean contains(T data) {
        return root == null ? false : root.contains(data);
    }

    public T max() {
        return root == null ? null : root.max();
    }

    public T min() {
        return root == null ? null : root.min();
    }

    public void delete(T data) {
        if (root != null)
            root = root.delete(data);
    }

    public String preOrder() {
        return root != null ? root.preOrder() : null;
    }

    public String postOrder() {
        return root != null ? root.postOrder() : null;
    }

    public String inOrder() {
        return root != null ? root.inOrder() : null;
    }

    public String hierarchy() {
        return root != null ? root.hierarchy("") : null;
    }

    public String byLevel() {
        if (root == null)
            return null;

        // shrimple bfs
        var sb = new StringBuilder();
        Queue<Node> pending = new LinkedList<>();
        pending.add(root);

        while (!pending.isEmpty()) {
            var node = pending.remove();
            if (node != null) {
                sb.append(node.data + " ");

                if (node.left != null)
                    pending.add(node.left);
                if (node.right != null)
                    pending.add(node.right);
            }
        }

        return sb.toString();
    }

    public Node root() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height();
    }

    public Iterator<T> iterator() {
        return switch (order) {
            case Order.ByLevel -> new BSTIteratorByLevel();
            case Order.InOrder -> new BSTIteratorInOrder();
        };
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int count(T data) {
        return root == null ? 0 : root.count(data);
    }

    public T nth(int n) {
        var iter = new BSTIteratorInOrder();
        T data = null;
        for (int i = 0; i < n; i++) {
            data = iter.hasNext() ? iter.next() : null;
            if (data == null)
                break;
        }

        return data;
    }

    public class BSTIteratorByLevel implements Iterator<T> {
        Queue<Node> pending = new LinkedList<>();

        public BSTIteratorByLevel() {
            if (root != null)
                pending.add(root);
        }

        public boolean hasNext() {
            return !pending.isEmpty();
        }

        public T next() {
            var node = pending.remove();

            if (node.left != null)
                pending.add(node.left);
            if (node.right != null)
                pending.add(node.right);

            return node.data;
        }
    }

    public class BSTIteratorInOrder implements Iterator<T> {
        Stack<Node> stack = new Stack<>();
        Node current = root;

        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        public T next() {
            T value = null;
            while (value == null) {
                value = switch (current) {
                    case null -> {
                        var next = stack.pop();
                        current = next.right();
                        yield next.data;
                    }
                    case Node n -> {
                        stack.push(n);
                        current = n.left();
                        yield null;
                    }
                };
            }

            return value;
        }
    }

    public class Node {
        T data;
        Node left, right;

        public Node(T data) {
            this.data = data;
        }

        public Node left() {
            return left;
        }

        public Node right() {
            return right;
        }

        public T data() {
            return data;
        }

        public void insert(T data) {
            if (data.compareTo(this.data) > 0) {
                if (right == null)
                    right = new Node(data);
                else
                    right.insert(data);
            } else {
                if (left == null)
                    left = new Node(data);
                else
                    left.insert(data);
            }
        }

        public boolean contains(T data) {
            // eat shit, java
            return switch ((Integer) data.compareTo(this.data)) {
                case 0 -> true;
                case Integer cmp when cmp > 0 -> right == null ? false : right.contains(data);
                default -> left == null ? false : left.contains(data);
            };
        }

        public T max() {
            return right == null ? data : right.max();
        }

        public T min() {
            return left == null ? data : left.min();
        }

        public Node delete(T data) {
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

        public String preOrder() {
            var sb = new StringBuilder();
            sb.append(data).append(" ");
            if (left != null)
                sb.append(left.preOrder());
            if (right != null)
                sb.append(right.preOrder());

            return sb.toString();
        }

        public String postOrder() {
            var sb = new StringBuilder();
            if (left != null)
                sb.append(left.postOrder());
            if (right != null)
                sb.append(right.postOrder());
            sb.append(" ").append(data);

            return sb.toString();
        }

        public String inOrder() {
            var sb = new StringBuilder();
            sb.append("(");
            if (left != null)
                sb.append(left.inOrder() + " ");
            sb.append(data);
            if (right != null)
                sb.append(" " + right.inOrder());
            sb.append(")");

            return sb.toString();
        }

        public String hierarchy(String prefix) {
            var sb = new StringBuilder();

            if (prefix.length() > 0) {
                sb.append("\n" + prefix);
            }

            sb.append("[" + data + "]");
            if (!leaf()) {
                prefix = prefix.replace("├", "│").replace("─", " ").replace("╰", " ");
                sb.append(left == null ? "\n" + prefix + "├──(null)" : left.hierarchy(prefix + "├──"));
                sb.append(right == null ? "\n" + prefix + "╰──(null)"
                        : right.hierarchy(prefix + "╰──"));
            }
            return sb.toString();
        }

        public int height() {
            int leftHeight = left == null ? 0 : left.height() + 1;
            int rightHeight = right == null ? 0 : right.height() + 1;

            return Math.max(leftHeight, rightHeight);
        }

        // O(log2 n) if tree is balanced
        // O(n) worst case (degenerate tree)
        public int count(T data) {
            Integer cmp = data.compareTo(this.data);

            var count = switch (cmp) {
                case Integer n when n > 0 -> right == null ? 0 : right.count(data);
                default -> left == null ? 0 : left.count(data);
            };
            return count + (cmp == 0 ? 1 : 0);
        }

        private boolean leaf() {
            return left == null && right == null;
        }
    }
}
