package com.itba.eda.BST;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.itba.eda.BST.BST.Order;

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

    @Test
    @DisplayName("DisplayByLevel")
    public void testDisplayByLevel() {
        var tree = makeTestTree2();

        assertEquals("35 20 74 15 22 55 8 27 25", tree.byLevel().trim());
    }

    @Test
    @DisplayName("Deletion")
    public void testDeletion() {
        var tree = makeTestTree();

        // R1
        assertEquals(true, tree.contains(44));
        tree.delete(44);
        assertEquals(false, tree.contains(44));

        // R2
        assertEquals(true, tree.contains(80));
        tree.delete(80);
        assertEquals(false, tree.contains(80));
        assertEquals(70, tree.max());

        // R3
        assertEquals(true, tree.contains(20));
        tree.delete(20);
        assertEquals(false, tree.contains(20));
        assertEquals(10, tree.min());

        assertEquals(true, tree.contains(50));
        tree.delete(50);
        assertEquals(true, tree.contains(50));
        tree.delete(50);
        assertEquals(false, tree.contains(50));
        assertEquals(70, tree.max());
        assertEquals(10, tree.min());

        var empty = new BST<Integer>();
        assertDoesNotThrow(() -> empty.delete(50));
    }

    @Test
    @DisplayName("IteratorByLevel")
    public void testIteratorByLevel() {
        var tree = makeTestTree2();

        var sb = new StringBuilder();
        for (var v : tree)
            sb.append(v + " ");
        assertEquals("35 20 74 15 22 55 8 27 25", sb.toString().trim());
    }

    @Test
    @DisplayName("IteratorInOrder")
    public void testIteratorInOrder() {
        var tree = makeTestTree2();
        tree.setOrder(Order.InOrder);

        var sb = new StringBuilder();
        for (var v : tree)
            sb.append(v + " ");
        assertEquals("8 15 20 22 25 27 35 55 74", sb.toString().trim());
    }

    @Test
    @DisplayName("Count")
    public void testCount() {
        var tree = makeTestTree();

        assertEquals(1, tree.count(60));
        assertEquals(1, tree.count(44));
        assertEquals(2, tree.count(50));
        assertEquals(2, tree.count(40));
        assertEquals(0, tree.count(69420));

        tree.delete(50);
        assertEquals(1, tree.count(50));
        assertEquals(2, tree.count(40));

        tree.delete(40);
        assertEquals(1, tree.count(50));
        assertEquals(1, tree.count(40));

        tree.delete(40);
        assertEquals(1, tree.count(50));
        assertEquals(0, tree.count(40));
    }

    @Test
    @DisplayName("Nth")
    public void testNth() {
        var tree = makeTestTree2();

        assertEquals(8, tree.nth(1));
        assertEquals(15, tree.nth(2));
        assertEquals(20, tree.nth(3));
        assertEquals(35, tree.nth(7));
        assertEquals(55, tree.nth(8));
        assertEquals(74, tree.nth(9));
        assertNull(tree.nth(10));
        assertNull(tree.nth(0));

        tree.delete(55);
        assertEquals(35, tree.nth(7));
        assertEquals(74, tree.nth(8));
        assertNull(tree.nth(9));

        tree.delete(8);
        assertEquals(15, tree.nth(1));
        assertEquals(74, tree.nth(7));
        assertNull(tree.nth(8));
        assertNull(tree.nth(9));
    }

    @Test
    @DisplayName("Closest Common Ancestor")
    public void testClosestCommonAncestor() {
        var tree = makeTestTree3();

        // Should return null if a = b
        assertNull(tree.closestCommonAncestor(35, 35));

        // 0 is not in the tree
        assertNull(tree.closestCommonAncestor(0, 85));

        assertEquals(70, tree.closestCommonAncestor(35, 70));
        assertEquals(70, tree.closestCommonAncestor(70, 35));

        assertEquals(70, tree.closestCommonAncestor(40, 80));
    }

    @Test
    @DisplayName("Closest Common Ancestor With Repeated")
    public void testClosestCommonAncestorRepeat() {
        var tree = makeTestTree4();

        // 40 only appears once
        assertNull(tree.closestCommonAncestorRepeat(40, 40));

        // 0 is not in the tree
        assertNull(tree.closestCommonAncestorRepeat(0, 85));

        assertEquals(70, tree.closestCommonAncestorRepeat(70, 70));
        assertEquals(70, tree.closestCommonAncestorRepeat(40, 80));
        assertEquals(80, tree.closestCommonAncestorRepeat(85, 80));
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

    private BST<Integer> makeTestTree2() {
        var tree = new BST<Integer>();

        tree.insert(35);
        tree.insert(74);
        tree.insert(20);
        tree.insert(22);
        tree.insert(55);
        tree.insert(15);
        tree.insert(8);
        tree.insert(27);
        tree.insert(25);

        return tree;
    }

    private BST<Integer> makeTestTree3() {
        var tree = new BST<Integer>();

        tree.insert(5);
        tree.insert(70);
        tree.insert(30);
        tree.insert(35);
        tree.insert(20);
        tree.insert(40);
        tree.insert(80);
        tree.insert(90);
        tree.insert(85);

        return tree;
    }

    private BST<Integer> makeTestTree4() {
        var tree = new BST<Integer>();

        tree.insert(5);
        tree.insert(70);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(80);
        tree.insert(90);
        tree.insert(85);

        return tree;
    }
}
