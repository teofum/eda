package com.itba.eda.Levenshtein;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevenshteinTest {
    @Test
    @DisplayName("Levenshtein: Returns expected distances for test strings")
    public void testLevenshteinStrings() {
        assertEquals(4, Levenshtein.distance("", "1234"));
        assertEquals(0, Levenshtein.distance("hello", "hello"));
        assertEquals(1, Levenshtein.distance("hello", "helo"));
        assertEquals(1, Levenshtein.distance("hello", "henlo"));
        assertEquals(1, Levenshtein.distance("hello", "helllo"));
    }

    @Test
    @DisplayName("Levenshtein: Returns expected similarities for test strings")
    public void testLevenshteinSimilarity() {
        assertEquals(0.0, Levenshtein.similarity("", "1234"));
        assertEquals(1.0, Levenshtein.similarity("hello", "hello"));
        assertEquals(0.8, Levenshtein.similarity("hello", "helo"));
        assertEquals(0.8, Levenshtein.similarity("hello", "henlo"));
        assertEquals(5.0 / 6.0, Levenshtein.similarity("hello", "helllo"));
    }
}
