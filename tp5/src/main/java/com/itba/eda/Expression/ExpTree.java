package com.itba.eda.Expression;

import java.util.Scanner;

public class ExpTree implements ExpressionService {
    private Node root;

    public ExpTree(String expr) {
        Scanner ls = new Scanner(expr).useDelimiter("\\s+");
        root = new Node(ls);
        ls.close();
    }

    static final class Node {
        private String data;
        private Node left, right;

        public Node(Scanner ls) {
            buildExpression(ls);
            if (ls.hasNext())
                throw new RuntimeException("Bad Expression");
        }

        private void buildExpression(Scanner ls) {
            data = null;
            left = null;
            right = null;
        }
    }
}
