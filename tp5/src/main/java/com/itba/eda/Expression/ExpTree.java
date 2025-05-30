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
    }
}
