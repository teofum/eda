package com.itba.eda.BinaryTree;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class BinaryTree<T> implements BinaryTreeService {
    private final UnaryOperator<Node<T>> left = (n) -> n.setLeft(new Node<T>());
    private final UnaryOperator<Node<T>> right = (n) -> n.setRight(new Node<T>());
    private final UnaryOperator<Node<T>> consume = (n) -> n;

    private Node<T> root;
    private Function<String, T> parser;

    public BinaryTree(String filename, Function<String, T> parser) throws FileNotFoundException {
        var is = getClass().getClassLoader().getResourceAsStream(filename);
        if (is == null)
            throw new FileNotFoundException(filename);

        this.parser = parser;

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

        /*
         * Do a BFS with dummy nodes where nodes are null. We keep a queue of
         * nodes, which can be null (dummy) as we traverse the tree by level.
         */
        var sb = new StringBuilder();
        Queue<Node<T>> pending = new LinkedList<>();
        pending.add(root);

        /*
         * Keep track of the number of non-null nodes in the queue. We need this
         * because we're adding dummy nodes wven if there's nothing there, so we
         * can't use an empty queue as an end condition (the tree would have
         * infinite levels full of dummy nodes). If the entire queue is null, we
         * reached the end of the tree and can exit.
         */
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

    public void toFile(String filename) {
        try (var pw = new PrintWriter(filename)) {
            pw.print(byLevel());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int height() {
        return root == null ? -1 : root.height();
    }

    public int heightIter() {
        if (root == null)
            return -1;

        Queue<Entry<Node<T>, Integer>> pending = new LinkedList<>();
        pending.add(Map.entry(root, 0));

        int level = 0;
        while (!pending.isEmpty()) {
            var e = pending.remove();
            level = Math.max(level, e.getValue());

            if (e.getKey().left != null)
                pending.add(Map.entry(e.getKey().left, e.getValue() + 1));
            if (e.getKey().right != null)
                pending.add(Map.entry(e.getKey().right, e.getValue() + 1));
        }

        return level;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BinaryTree bt))
            return false;

        if (root == null)
            return bt.root == null;
        return root.equals(bt.root);
    }

    private void buildTree(Scanner scanner) {
        Queue<NodeOperation<T>> pending = new LinkedList<>();

        root = new Node<T>();
        pending.add(new NodeOperation<T>(root, consume));

        while (scanner.hasNext()) {
            var nextPending = pending.remove();
            var current = nextPending.node;

            switch (scanner.next()) {
                case "?" -> {
                    // Add dummy operations for (non existent) left and right nodes
                    pending.add(new NodeOperation<T>(null, consume));
                    pending.add(new NodeOperation<T>(null, consume));
                }
                case String token -> {
                    current = nextPending.action.apply(current);
                    current.data = parser.apply(token);

                    pending.add(new NodeOperation<T>(current, left));
                    pending.add(new NodeOperation<T>(current, right));
                }
            }
        }

        if (root.data == null)
            root = null;
    }

    static class Node<T> {
        private T data;
        private Node<T> left, right;

        public Node() {
        }

        public Node<T> setLeft(Node<T> node) {
            return left = node;
        }

        public Node<T> setRight(Node<T> node) {
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

        public int height() {
            int leftHeight = left == null ? 0 : left.height() + 1;
            int rightHeight = right == null ? 0 : right.height() + 1;

            return Math.max(leftHeight, rightHeight);
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Node n))
                return false;

            if (!data.equals(n.data))
                return false;

            if (left != null) {
                if (!left.equals(n.left))
                    return false;
            } else {
                if (n.left != null)
                    return false;
            }

            if (right != null) {
                if (!right.equals(n.right))
                    return false;
            } else {
                if (n.right != null)
                    return false;
            }

            return true;
        }
    }

    static class NodeOperation<T> {
        private Node<T> node;
        private UnaryOperator<Node<T>> action;

        public NodeOperation(Node<T> node, UnaryOperator<Node<T>> action) {
            this.node = node;
            this.action = action;
        }
    }
}
