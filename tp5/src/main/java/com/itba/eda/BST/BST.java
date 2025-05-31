package com.itba.eda.BST;

import java.util.LinkedList;
import java.util.Queue;

public class BST<T extends Comparable<? super T>> {
    private Node root;

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

        private boolean leaf() {
            return left == null && right == null;
        }
    }
}
