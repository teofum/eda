package com.itba.eda.BST;

public interface Node<T extends Comparable<? super T>> {
    Node<T> left();

    Node<T> right();

    T data();

    Node<T> insert(T data);

    Node<T> delete(T data);

    default String valueString() {
        return data().toString();
    }

    default boolean leaf() {
        return left() == null && right() == null;
    }

    default boolean contains(T data) {
        return switch ((Integer) data.compareTo(this.data())) {
            case 0 -> true;
            case Integer cmp when cmp > 0 -> right() == null ? false : right().contains(data);
            default -> left() == null ? false : left().contains(data);
        };
    }

    default T max() {
        return right() == null ? data() : right().max();
    }

    default T min() {
        return left() == null ? data() : left().min();
    }

    default int height() {
        int leftHeight = left() == null ? 0 : left().height() + 1;
        int rightHeight = right() == null ? 0 : right().height() + 1;

        return Math.max(leftHeight, rightHeight);
    }

    // O(log2 n) if tree is balanced
    // O(n) worst case (degenerate tree)
    default int count(T data) {
        Integer cmp = data.compareTo(this.data());

        var count = switch (cmp) {
            case Integer n when n > 0 -> right() == null ? 0 : right().count(data);
            default -> left() == null ? 0 : left().count(data);
        };
        return count + (cmp == 0 ? 1 : 0);
    }

    default T closestCommonAncestor(T a, T b) {
        // Both values are in the right subtree
        if (a.compareTo(data()) > 0)
            return right() == null ? null : right().closestCommonAncestor(a, b);

        // Both values are in the left subtree
        if (b.compareTo(data()) < 0)
            return left() == null ? null : left().closestCommonAncestor(a, b);

        // One of the values is equal to this node, then it is the closest common
        // ancestor
        if (b.compareTo(data()) == 0)
            return left() == null ? null : (left().contains(a) ? data() : null); // if a == b it's in the left() subtree
        if (a.compareTo(data()) == 0)
            return right() == null ? null : (right().contains(b) ? data() : null);

        // One value is in either subtree, this must be the closest common ancestor
        return (left() != null && right() != null && left().contains(a) && right().contains(b)) ? data() : null;
    }

    default String preOrder() {
        var sb = new StringBuilder();
        sb.append(data()).append(" ");
        if (left() != null)
            sb.append(left().preOrder());
        if (right() != null)
            sb.append(right().preOrder());

        return sb.toString();
    }

    default String postOrder() {
        var sb = new StringBuilder();
        if (left() != null)
            sb.append(left().postOrder());
        if (right() != null)
            sb.append(right().postOrder());
        sb.append(" ").append(data());

        return sb.toString();
    }

    default String inOrder() {
        var sb = new StringBuilder();
        sb.append("(");
        if (left() != null)
            sb.append(left().inOrder() + " ");
        sb.append(data());
        if (right() != null)
            sb.append(" " + right().inOrder());
        sb.append(")");

        return sb.toString();
    }

    default String hierarchy(String prefix) {
        var sb = new StringBuilder();

        if (prefix.length() > 0) {
            sb.append("\n" + prefix);
        }

        sb.append("[" + valueString() + "]");
        if (!leaf()) {
            prefix = prefix.replace("├", "│").replace("─", " ").replace("╰", " ");
            sb.append(left() == null ? "\n" + prefix + "├──(null)" : left().hierarchy(prefix + "├──"));
            sb.append(right() == null ? "\n" + prefix + "╰──(null)"
                    : right().hierarchy(prefix + "╰──"));
        }
        return sb.toString();
    }
}
