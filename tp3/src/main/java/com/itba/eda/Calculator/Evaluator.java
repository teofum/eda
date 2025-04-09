package com.itba.eda.Calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.BinaryOperator;

import static java.util.Map.entry;

public class Evaluator {
    private final Stack<Double> stack = new Stack<>();

    private final Map<String, BinaryOperator<Double>> operators = Map.ofEntries(
            entry("+", (a, b) -> a + b),
            entry("-", (a, b) -> a - b),
            entry("*", (a, b) -> a * b),
            entry("/", (a, b) -> a / b));

    private final Map<Map.Entry<String, String>, Boolean> precedence = Map.ofEntries(
            entry(entry("+", "+"), true),
            entry(entry("+", "-"), true),
            entry(entry("+", "*"), false),
            entry(entry("+", "/"), false),
            entry(entry("-", "+"), true),
            entry(entry("-", "-"), true),
            entry(entry("-", "*"), false),
            entry(entry("-", "/"), false),
            entry(entry("*", "+"), true),
            entry(entry("*", "-"), true),
            entry(entry("*", "*"), true),
            entry(entry("*", "/"), true),
            entry(entry("/", "+"), true),
            entry(entry("/", "-"), true),
            entry(entry("/", "*"), true),
            entry(entry("/", "/"), true));

    public double evaluate(String expr) {
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
        return stack.pop();

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

            var isOperator = operators.containsKey(token);
            if (isOperator) {
                // Check precedence
                while (!opStack.isEmpty() && precedence.get(entry(opStack.peek(), token))) {
                    postfixExpr.append(opStack.pop()).append(" ");
                }
                opStack.push(token);
            } else {
                postfixExpr.append(token).append(" ");
            }
        }

        scanner.close();

        while (!opStack.isEmpty()) {
            postfixExpr.append(opStack.pop()).append(" ");
        }

        return postfixExpr.toString().trim(); // Remove the trailing whitespace
    }
}
