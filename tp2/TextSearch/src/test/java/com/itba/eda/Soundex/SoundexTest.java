package com.itba.eda.Soundex;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SoundexTest {
    @Test
    @DisplayName("Returns expected codes for test strings")
    public void testSoundexStrings() {
        assertEquals("T624", new Soundex("threshold").toString());
        assertEquals("H430", new Soundex("hold").toString());
        assertEquals("P500", new Soundex("phone").toString());
        assertEquals("F500", new Soundex("foun").toString());
    }

    @Test
    @DisplayName("Ignores non-letter characters in the middle of the string")
    public void testSoundexNonLetters() {
        var baseline = new Soundex("threshold").toString();
        assertEquals(baseline, new Soundex("thr423esh99old50").toString());
        assertEquals(baseline, new Soundex("th$#!  resh(old)").toString());
    }

    @Test
    @DisplayName("Ignores non-letter characters at the beginning of the string")
    public void testSoundexNonLettersBegin() {
        var baseline = new Soundex("threshold").toString();
        assertEquals(baseline, new Soundex("1234thresh,,,old").toString());
    }

    @Test
    @DisplayName("Throws on invalid input (empty string)")
    public void testSoundexEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> new Soundex(""));
    }

    @Test
    @DisplayName("Throws on invalid input (no letters)")
    public void testSoundexInvalidArgument() {
        assertThrows(IllegalArgumentException.class, () -> new Soundex("12345678"));
    }
}
