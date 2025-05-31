package com.itba.eda.BinaryTree;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

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

    private void buildTree(Scanner scanner) {
        Queue<NodeOperation> pending = new LinkedList<>();

        root = new Node();
        pending.add(new NodeOperation(root, NodeOperation.Action.CONSUME));

        while (scanner.hasNext()) {
            var nextPending = pending.remove();
            var current = nextPending.node;

            switch (scanner.next()) {
                case "?" -> {
                    // Add dummy operations for (non existent) left and right nodes
                    pending.add(new NodeOperation(null, NodeOperation.Action.CONSUME));
                    pending.add(new NodeOperation(null, NodeOperation.Action.CONSUME));
                }
                case String token -> {
                    current = switch (nextPending.action) {
                        case NodeOperation.Action.LEFT -> current.setLeft(new Node());
                        case NodeOperation.Action.RIGHT -> current.setRight(new Node());
                        case NodeOperation.Action.CONSUME -> current;
                    };

                    current.data = token;

                    pending.add(new NodeOperation(current, NodeOperation.Action.LEFT));
                    pending.add(new NodeOperation(current, NodeOperation.Action.RIGHT));
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
    }

    static class NodeOperation {
        static enum Action {
            LEFT, RIGHT, CONSUME
        };

        private Node node;
        private Action action;

        public NodeOperation(Node node, Action action) {
            this.node = node;
            this.action = action;
        }
    }
}
