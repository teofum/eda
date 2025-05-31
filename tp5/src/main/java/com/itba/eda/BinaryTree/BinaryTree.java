package com.itba.eda.BinaryTree;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.function.UnaryOperator;

public class BinaryTree implements BinaryTreeService {
    private Node root;

    public BinaryTree(String filename) throws FileNotFoundException {
        var is = getClass().getClassLoader().getResourceAsStream(filename);
        if (is == null)
            throw new FileNotFoundException(filename);

        var scanner = new Scanner(is).useDelimiter("\\s+");
        buildTree(scanner);
        scanner.close();
    }

    public String preOrder() {
        return root != null ? root.preOrder() : null;
    }

    public String postOrder() {
        return root != null ? root.postOrder() : null;
    }

    public String hierarchy() {
        return root != null ? root.hierarchy("") : null;
    }

    public String byLevel() {
        if (root == null)
            return null;

        var sb = new StringBuilder();
        Queue<Node> pending = new LinkedList<>();
        pending.add(root);

        int nonNull = 1;
        int iLevel = 0, levelSize = 1;
        while (nonNull > 0) {
            var node = pending.remove();
            if (node != null) {
                nonNull--;

                sb.append(node.data + " ");

                pending.add(node.left);
                if (node.left != null)
                    nonNull++;
                pending.add(node.right);
                if (node.right != null)
                    nonNull++;
            } else {
                sb.append("? ");

                pending.add(null);
                pending.add(null);
            }

            iLevel++;
            if (iLevel == levelSize) {
                iLevel = 0;
                levelSize *= 2;
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private void buildTree(Scanner scanner) {
        Queue<NodeOperation> pending = new LinkedList<>();

        root = new Node();
        pending.add(new NodeOperation(root, NodeOperation.consume));

        while (scanner.hasNext()) {
            var nextPending = pending.remove();
            var current = nextPending.node;

            switch (scanner.next()) {
                case "?" -> {
                    // Add dummy operations for (non existent) left and right nodes
                    pending.add(new NodeOperation(null, NodeOperation.consume));
                    pending.add(new NodeOperation(null, NodeOperation.consume));
                }
                case String token -> {
                    current = nextPending.action.apply(current);
                    current.data = token;

                    pending.add(new NodeOperation(current, NodeOperation.left));
                    pending.add(new NodeOperation(current, NodeOperation.right));
                }
            }
        }

        if (root.data == null)
            root = null;
    }

    static class Node {
        private String data;
        private Node left, right;

        public Node() {
        }

        public Node setLeft(Node node) {
            return left = node;
        }

        public Node setRight(Node node) {
            return right = node;
        }

        private boolean leaf() {
            return left == null && right == null;
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
    }

    static class NodeOperation {
        public static final UnaryOperator<Node> left = (n) -> n.setLeft(new Node());
        public static final UnaryOperator<Node> right = (n) -> n.setRight(new Node());
        public static final UnaryOperator<Node> consume = (n) -> n;

        private Node node;
        private UnaryOperator<Node> action;

        public NodeOperation(Node node, UnaryOperator<Node> action) {
            this.node = node;
            this.action = action;
        }
    }
}
