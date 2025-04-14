package com.itba.eda.Calculator;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BinaryOperator;

import static java.util.Map.entry;

import java.util.HashMap;

public class Evaluator {
    private static final String VAR_REGEX = "^[A-Za-z][A-Za-z0-9_]*$";

    private final Stack<Double> stack = new Stack<>();
    private final Map<String, Double> variables = new HashMap<>();

    private final Map<String, BinaryOperator<Double>> operators = Map.ofEntries(
            entry("+", (a, b) -> a + b),
            entry("-", (a, b) -> a - b),
            entry("*", (a, b) -> a * b),
            entry("/", (a, b) -> a / b),
            entry("^", (a, b) -> Math.pow(a, b)));

    private final Map<Map.Entry<String, String>, Boolean> precedence = Map.ofEntries(
            entry(entry("+", "+"), true),
            entry(entry("+", "-"), true),
            entry(entry("+", "*"), false),
            entry(entry("+", "/"), false),
            entry(entry("+", "^"), false),
            entry(entry("+", "("), false),
            entry(entry("+", ")"), true),
            entry(entry("-", "+"), true),
            entry(entry("-", "-"), true),
            entry(entry("-", "*"), false),
            entry(entry("-", "/"), false),
            entry(entry("-", "^"), false),
            entry(entry("-", "("), false),
            entry(entry("-", ")"), true),
            entry(entry("*", "+"), true),
            entry(entry("*", "-"), true),
            entry(entry("*", "*"), true),
            entry(entry("*", "/"), true),
            entry(entry("*", "^"), false),
            entry(entry("*", "("), false),
            entry(entry("*", ")"), true),
            entry(entry("/", "+"), true),
            entry(entry("/", "-"), true),
            entry(entry("/", "*"), true),
            entry(entry("/", "/"), true),
            entry(entry("/", "^"), false),
            entry(entry("/", "("), false),
            entry(entry("/", ")"), true),
            entry(entry("^", "+"), true),
            entry(entry("^", "-"), true),
            entry(entry("^", "*"), true),
            entry(entry("^", "/"), true),
            entry(entry("^", "^"), false),
            entry(entry("^", "("), false),
            entry(entry("^", ")"), true),
            entry(entry("(", "+"), false),
            entry(entry("(", "-"), false),
            entry(entry("(", "*"), false),
            entry(entry("(", "/"), false),
            entry(entry("(", "^"), false),
            entry(entry("(", "("), false),
            entry(entry("(", ")"), false));

    public double evaluate(String expr) {
        // Absolute hack for cheap assignments
        String assignTo = null;
        if (expr.contains("=")) {
            var parts = expr.split("=");
            if (parts.length != 2)
                throw new RuntimeException("Syntax error in assignment expression");

            assignTo = parts[0].trim();
            expr = parts[1].trim();

            if (!assignTo.matches(VAR_REGEX))
                throw new RuntimeException("Bad variable name");
        }

        var scanner = new Scanner(infixToPostfix(expr)).useDelimiter("\\s+");

        stack.clear();

        while (scanner.hasNext()) {
            var token = scanner.next();

            var op = operators.get(token);
            if (op != null) {
                operate(op);
            } else {
                var value = Double.valueOf(token);
                stack.push(value);
            }
        }

        scanner.close();

        if (stack.size() != 1)
            throw new RuntimeException("Invalid stack size at evaluation end, bad expression");
        var result = stack.pop();

        if (assignTo != null) {
            variables.put(assignTo, result);
        }

        return result;
    }

    private void operate(BinaryOperator<Double> op) {
        if (stack.size() < 2)
            throw new RuntimeException("Not enough operands in stack");

        double b = stack.pop();
        double a = stack.pop();
        stack.push(op.apply(a, b));
    }

    private String infixToPostfix(String expr) {
        var scanner = new Scanner(expr).useDelimiter("\\s+");
        var postfixExpr = new StringBuilder();
        var opStack = new Stack<String>();

        while (scanner.hasNext()) {
            var token = scanner.next();

            var isOperator = "()".contains(token) || operators.containsKey(token);
            if (isOperator) {
                // Check precedence
                while (!opStack.isEmpty() && precedence.get(entry(opStack.peek(), token))) {
                    postfixExpr.append(opStack.pop()).append(" ");
                }

                // Handle closing parentheses
                if (token.equals(")")) {
                    if (!opStack.empty() && opStack.peek().equals("("))
                        opStack.pop(); // Remove closing parens without appending
                    else
                        throw new RuntimeException("No matching opening parentheses found");
                } else {
                    opStack.push(token);
                }
            } else if (token.matches("[0-9]*(.[0-9]+)?")) {
                postfixExpr.append(token).append(" ");
            } else if (token.matches(VAR_REGEX)) {
                var value = variables.get(token);
                if (value == null)
                    throw new RuntimeException("Variable " + token + " is undefined");
                postfixExpr.append(value).append(" ");
            } else {
                throw new RuntimeException("Invalid token " + token);
            }
        }

        scanner.close();

        while (!opStack.isEmpty()) {
            if (opStack.peek().equals("("))
                throw new RuntimeException("No matching closing parentheses found");

            postfixExpr.append(opStack.pop()).append(" ");
        }

        return postfixExpr.toString().trim(); // Remove the trailing whitespace
    }
}
