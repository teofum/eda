package com.itba.eda.Metaphone;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MetaphoneTest {
    @Test
    @DisplayName("Metaphone: Returns expected codes for test strings")
    public void testMetaphoneStrings() {
        assertEquals("0RXLT", new Metaphone("threshold").toString());
        assertEquals("HLT", new Metaphone("hold").toString());
        assertEquals("FN", new Metaphone("phone").toString());
        assertEquals("FN", new Metaphone("foun").toString());
        assertEquals("EKSPNT", new Metaphone("expand").toString());
    }

    @Test
    @DisplayName("Metaphone: Ignores non-letter characters in the middle of the string")
    public void testMetaphoneNonLetters() {
        var baseline = new Metaphone("threshold").toString();
        assertEquals(baseline, new Metaphone("thr423esh99old50").toString());
        assertEquals(baseline, new Metaphone("th$#!  resh(old)").toString());
    }

    @Test
    @DisplayName("Metaphone: Ignores non-letter characters at the beginning of the string")
    public void testMetaphoneNonLettersBegin() {
        var baseline = new Metaphone("threshold").toString();
        assertEquals(baseline, new Metaphone("1234thresh,,,old").toString());
    }

    @Test
    @DisplayName("Metaphone: Handles output strings larger than input")
    public void testMetaphoneOutputExpand() {
        assertDoesNotThrow(() -> new Metaphone("jxjxjxjxjx"));
    }

    @Test
    @DisplayName("Metaphone: Throws on invalid input (empty string)")
    public void testMetaphoneEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> new Metaphone(""));
    }

    @Test
    @DisplayName("Metaphone: Throws on invalid input (no letters)")
    public void testMetaphoneInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Metaphone("12345678"));
    }

//    @Test
//    @DisplayName("Metaphone: Similarity returns expected values for test strings")
//    public void testMetaphoneSimilarity() {
//        assertEquals(0.0, Metaphone.similarity("threshold", "hold"));
//        assertEquals(0.75, new Metaphone("phone").similarity(new Metaphone("foun")));
//    }
}
