package com.itba.eda.IndexService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IndexRecordTest {
    @Test
    @DisplayName("CompareTo")
    public void testCompareTo() {
        var r1 = new IndexRecord<>(1, "One");
        var r2 = new IndexRecord<>(1, "Uno");
        var r3 = new IndexRecord<>(2, "Two");

        assertEquals(0, r1.compareTo(r2));
        assertEquals(-1, r1.compareTo(r3));
        assertEquals(1, r3.compareTo(r2));
    }

    @Test
    @DisplayName("Equals")
    public void testEquals() {
        var r1 = new IndexRecord<>(1, "One");
        var r2 = new IndexRecord<>(1, "Uno");
        var r3 = new IndexRecord<>(2, "Two");

        assertEquals(true, r1.equals(r2));
        assertEquals(false, r1.equals(r3));
        assertEquals(false, r1.equals(null));
    }
}
