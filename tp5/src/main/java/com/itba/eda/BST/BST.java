package com.itba.eda.BST;

public class BST<T extends Comparable<? super T>> {
    private Node root;

    public void insert(T data) {
        if (root == null)
            root = new Node(data);
        else
            root.insert(data);
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

    public Node root() {
        return root;
    }

    public int height() {
        return root == null ? -1 : root.height();
    }

    private class Node {
        T data;
        Node left, right;

        public Node(T data) {
            this.data = data;
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
