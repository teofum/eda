package com.itba.eda.BST;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public interface BinaryTree<T extends Comparable<? super T>, N extends Node<T>> extends Iterable<T> {
    public static enum Order {
        ByLevel, InOrder
    };

    N root();

    Order order();

    void setOrder(Order order);

    void insert(T data);

    void delete(T data);

    default boolean contains(T data) {
        return root() == null ? false : root().contains(data);
    }

    default T max() {
        return root() == null ? null : root().max();
    }

    default T min() {
        return root() == null ? null : root().min();
    }

    default int count(T data) {
        return root() == null ? 0 : root().count(data);
    }

    default int height() {
        return root() == null ? -1 : root().height();
    }

    default T nth(int n) {
        var iter = new BSTIteratorInOrder<T>(root());
        T data = null;
        for (int i = 0; i < n; i++) {
            data = iter.hasNext() ? iter.next() : null;
            if (data == null)
                break;
        }

        return data;
    }

    default String preOrder() {
        return root() != null ? root().preOrder() : null;
    }

    default String postOrder() {
        return root() != null ? root().postOrder() : null;
    }

    default String inOrder() {
        return root() != null ? root().inOrder() : null;
    }

    default String hierarchy() {
        return root() != null ? root().hierarchy("") : null;
    }

    @SuppressWarnings("unchecked")
    default String byLevel() {
        if (root() == null)
            return null;

        // shrimple bfs
        var sb = new StringBuilder();
        Queue<N> pending = new LinkedList<>();
        pending.add(root());

        while (!pending.isEmpty()) {
            var node = pending.remove();
            if (node != null) {
                sb.append(node.data() + " ");

                if (node.left() != null)
                    pending.add((N) node.left());
                if (node.right() != null)
                    pending.add((N) node.right());
            }
        }

        return sb.toString();
    }

    default T closestCommonAncestor(T a, T b) {
        return root() == null ? null : switch ((Integer) a.compareTo(b)) {
            case 0 -> null;
            case Integer n when n > 0 -> root().closestCommonAncestor(b, a);
            default -> root().closestCommonAncestor(a, b);
        };
    }

    default T closestCommonAncestorRepeat(T a, T b) {
        return root() == null ? null : switch ((Integer) a.compareTo(b)) {
            case Integer n when n > 0 -> root().closestCommonAncestor(b, a);
            default -> root().closestCommonAncestor(a, b);
        };
    }

    default Iterator<T> iterator() {
        return switch (order()) {
            case Order.ByLevel -> new BSTIteratorByLevel<T>(root());
            case Order.InOrder -> new BSTIteratorInOrder<T>(root());
        };
    }

    public class BSTIteratorByLevel<E extends Comparable<? super E>> implements Iterator<E> {
        Queue<Node<E>> pending = new LinkedList<>();

        public BSTIteratorByLevel(Node<E> root) {
            if (root != null)
                pending.add(root);
        }

        public boolean hasNext() {
            return !pending.isEmpty();
        }

        public E next() {
            var node = pending.remove();

            if (node.left() != null)
                pending.add(node.left());
            if (node.right() != null)
                pending.add(node.right());

            return node.data();
        }
    }

    public class BSTIteratorInOrder<E extends Comparable<? super E>> implements Iterator<E> {
        Stack<Node<E>> stack = new Stack<>();
        Node<E> current;

        public BSTIteratorInOrder(Node<E> root) {
            current = root;
        }

        public boolean hasNext() {
            return !stack.isEmpty() || current != null;
        }

        public E next() {
            E value = null;
            while (value == null) {
                value = switch (current) {
                    case null -> {
                        var next = stack.pop();
                        current = next.right();
                        yield next.data();
                    }
                    case Node<E> n -> {
                        stack.push(n);
                        current = n.left();
                        yield null;
                    }
                };
            }

            return value;
        }
    }
}
