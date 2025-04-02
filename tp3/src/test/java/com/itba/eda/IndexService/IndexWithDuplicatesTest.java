package com.itba.eda.IndexService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IndexWithDuplicatesTest {
    @Test
    @DisplayName("Initialization gives correct count")
    public void testInitializationCount() {
        var index = new IndexWithDuplicates();

        index.initialize(new int[] { 1, 3, 7 });
        assertEquals(3, index.getCount());
    }

    @Test
    @DisplayName("Initialization throws on null input")
    public void testInitializationThrows() {
        var index = new IndexWithDuplicates();

        assertThrows(NullPointerException.class, () -> index.initialize(null));
    }

    @Test
    @DisplayName("Initialization sorts input correctly")
    public void testInitializationSort() {
        var index = new IndexWithDuplicates();

        index.initialize(new int[] { 98, 56, 75, 3, 98, 120, 7 });

        assertEquals(true, index.search(98));
        assertEquals(true, index.search(56));
        assertEquals(true, index.search(3));

        assertEquals(2, index.occurrences(98));
        assertEquals(1, index.occurrences(120));
    }

    @Test
    @DisplayName("Search")
    public void testSearch() {
        var index = new IndexWithDuplicates();

        index.initialize(new int[] { 1, 3, 7 });
        assertEquals(true, index.search(1));
        assertEquals(true, index.search(3));
        assertEquals(true, index.search(7));
        assertEquals(false, index.search(2));
    }

    @Test
    @DisplayName("Occurrences")
    public void testOccurrences() {
        var index = new IndexWithDuplicates();

        index.initialize(new int[] { 1, 3, 3, 3, 7 });
        assertEquals(1, index.occurrences(1));
        assertEquals(3, index.occurrences(3));
        assertEquals(1, index.occurrences(7));
        assertEquals(0, index.occurrences(2));
    }

    @Test
    @DisplayName("Insert")
    public void testInsertion() {
        var index = new IndexWithDuplicates();

        index.initialize(new int[] { 1, 3, 7 });
        index.insert(80);
        index.insert(37);

        assertEquals(true, index.search(80));
        assertEquals(true, index.search(37));

        assertEquals(1, index.occurrences(37));
        assertEquals(1, index.occurrences(80));

        index.insert(37);
        index.insert(37);
        assertEquals(3, index.occurrences(37));
        assertEquals(1, index.occurrences(80));
    }

    @Test
    @DisplayName("Delete")
    public void testDeletion() {
        var index = new IndexWithDuplicates();

        index.initialize(new int[] { 1, 3, 3, 3, 7, 37, 80 });
        assertEquals(true, index.search(3));
        assertEquals(true, index.search(37));

        assertEquals(3, index.occurrences(3));
        assertEquals(1, index.occurrences(37));

        index.delete(3);
        index.delete(37);
        assertEquals(true, index.search(3));
        assertEquals(false, index.search(37));

        assertEquals(2, index.occurrences(3));
        assertEquals(0, index.occurrences(37));
    }
}
