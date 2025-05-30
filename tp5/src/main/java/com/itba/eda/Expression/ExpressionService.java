package com.itba.eda.Expression;

public interface ExpressionService {
    String preOrder();

    String postOrder();

    String inOrder();

    double eval();
}
