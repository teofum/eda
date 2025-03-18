package com.itba.eda.Levenshtein;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevenshteinTest {
    @Test
    @DisplayName("Levenshtein: Returns expected codes for test strings")
    public void testLevenshteinStrings() {
        assertEquals(4, Levenshtein.distance("", "1234"));
        assertEquals(0, Levenshtein.distance("hello", "hello"));
        assertEquals(1, Levenshtein.distance("hello", "helo"));
        assertEquals(1, Levenshtein.distance("hello", "henlo"));
        assertEquals(1, Levenshtein.distance("hello", "helllo"));
    }
}
