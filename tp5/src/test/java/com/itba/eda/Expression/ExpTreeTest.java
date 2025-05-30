package com.itba.eda.Expression;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    @DisplayName("Pre order traversal")
    public void testPreOrder() {
        String expr = "( ( 2 + 3.5 ) * -10 )";
        assertEquals("* + 2 3.5 -10", new ExpTree(expr).preOrder());
    }

    @Test
    @DisplayName("Post order traversal")
    public void testPostOrder() {
        String expr = "( ( 2 + 3.5 ) * -10 )";
        assertEquals("2 3.5 + -10 *", new ExpTree(expr).postOrder());
    }

    @Test
    @DisplayName("In order traversal")
    public void testInOrder() {
        String expr = "( ( 2 + 3.5 ) * -10 )";
        assertEquals("( ( 2 + 3.5 ) * -10 )", new ExpTree(expr).inOrder());
    }
}
