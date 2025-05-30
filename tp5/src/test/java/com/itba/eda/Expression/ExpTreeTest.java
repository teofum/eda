package com.itba.eda.Expression;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExpTreeTest {
    @Test
    @DisplayName("Builds an expression")
    public void testExpression() {
        String expr = "( 1 + 2 )";
        assertDoesNotThrow(() -> new ExpTree(expr));
    }

    @Test
    @DisplayName("Builds a nested expression")
    public void testNestedExpression() {
        String expr = "( 1 + ( 5 * 3 ) )";
        assertDoesNotThrow(() -> new ExpTree(expr));
    }

    @Test
    @DisplayName("Throws on invalid expression")
    public void testBadExpression() {
        String expr = "(1 + 2 )";
        assertThrows(RuntimeException.class, () -> new ExpTree(expr));
    }

    @Test
    @DisplayName("Throws on missing )")
    public void testMissingParentheses() {
        String expr = "( 1 + 2";
        assertThrows(RuntimeException.class, () -> new ExpTree(expr));
    }
}
