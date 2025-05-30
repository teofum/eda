package com.itba.eda.Expression;

import java.util.Scanner;

public class ExpTree implements ExpressionService {
    private Node root;

    public ExpTree(String expr) {
        Scanner ls = new Scanner(expr).useDelimiter("\\s+");
        root = new Node(ls);

        if (ls.hasNext())
            throw new RuntimeException("Bad Expression");

        ls.close();
    }

    public String preOrder() {
        if (root == null)
            return "";

        return root.preOrder();
    }

    public String postOrder() {
        if (root == null)
            return "";

        return root.postOrder();
    }

    public String inOrder() {
        if (root == null)
            return "";

        return root.inOrder();
    }

    static final class Node {
        private String data;
        private Node left, right;

        public Node(Scanner ls) {
            switch (ls.next()) {
                case "(" -> {
                    left = new Node(ls); // Parse left expression
                    data = ls.next(); // Store operator
                    right = new Node(ls); // Parse right expression

                    // Expect closing parenthesis
                    if (!ls.next().equals(")"))
                        throw new RuntimeException("Expected )");
                }
                case String token -> data = token;
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
            sb.append(data);

            return sb.append(" ").toString();
        }

        public String inOrder() {
            var sb = new StringBuilder();

            if (left != null)
                sb.append("( ").append(left.inOrder());
            sb.append(data).append(" ");
            if (right != null)
                sb.append(right.inOrder()).append(") ");

            return sb.toString();
        }

    }
}
