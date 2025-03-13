package com.itba.eda.Soundex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SoundexTest {
    @Test
    @DisplayName("Soundex: Returns expected codes for test strings")
    public void testSoundexStrings() {
        assertEquals("T624", new Soundex("threshold").toString());
        assertEquals("H430", new Soundex("hold").toString());
        assertEquals("P500", new Soundex("phone").toString());
        assertEquals("F500", new Soundex("foun").toString());
    }

    @Test
    @DisplayName("Soundex: Ignores non-letter characters in the middle of the string")
    public void testSoundexNonLetters() {
        var baseline = new Soundex("threshold").toString();
        assertEquals(baseline, new Soundex("thr423esh99old50").toString());
        assertEquals(baseline, new Soundex("th$#!  resh(old)").toString());
    }

    @Test
    @DisplayName("Soundex: Ignores non-letter characters at the beginning of the string")
    public void testSoundexNonLettersBegin() {
        var baseline = new Soundex("threshold").toString();
        assertEquals(baseline, new Soundex("1234thresh,,,old").toString());
    }

    @Test
    @DisplayName("Soundex: Throws on invalid input (empty string)")
    public void testSoundexEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> new Soundex(""));
    }

    @Test
    @DisplayName("Soundex: Throws on invalid input (no letters)")
    public void testSoundexInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Soundex("12345678"));
    }

    @Test
    @DisplayName("Soundex: Similarity returns expected values for test strings")
    public void testSoundexSimilarity() {
        assertEquals(0.0, Soundex.similarity("threshold", "hold"));
        assertEquals(0.75, new Soundex("phone").similarity(new Soundex("foun")));
    }
}
