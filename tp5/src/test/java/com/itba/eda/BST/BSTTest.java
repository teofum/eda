package com.itba.eda.BST;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BSTTest {
    @Test
    @DisplayName("Insertion")
    public void testInsertion() {
        var tree = makeTestTree();

        assertEquals("(((10) 20 (((40) 40 (44)) 50)) 50 (60 ((70) 80)))", tree.inOrder().trim());
    }

    @Test
    @DisplayName("Contains")
    public void testContains() {
        var tree = makeTestTree();

        assertEquals(true, tree.contains(50));
        assertEquals(true, tree.contains(44));
        assertEquals(true, tree.contains(70));
        assertEquals(false, tree.contains(420));
        assertEquals(false, tree.contains(1));

        var empty = new BST<Integer>();

        assertEquals(false, empty.contains(50));
    }

    @Test
    @DisplayName("Min + Max")
    public void testMinMax() {
        var tree = makeTestTree();

        assertEquals(10, tree.min());
        assertEquals(80, tree.max());

        tree.insert(99);
        assertEquals(99, tree.max());

        var empty = new BST<Integer>();

        assertNull(empty.min());
        assertNull(empty.max());
    }

    private BST<Integer> makeTestTree() {
        var tree = new BST<Integer>();

        tree.insert(50);
        tree.insert(60);
        tree.insert(80);
        tree.insert(20);
        tree.insert(50);
        tree.insert(70);
        tree.insert(40);
        tree.insert(44);
        tree.insert(10);
        tree.insert(40);

        return tree;
    }
}
