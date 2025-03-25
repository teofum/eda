package com.itba.eda.QGrams;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

public class QGramsTest {
    @Test
    @DisplayName("QGrams: Tokens match expected values")
    public void testQGramsTokens() {
        var qGrams2 = new QGrams(2, "Threshold");

        var tokens = qGrams2.tokens();
        assertEquals(1, tokens.get("#t"));
        assertEquals(1, tokens.get("hr"));
        assertEquals(1, tokens.get("re"));
        assertEquals(1, tokens.get("es"));
        assertEquals(1, tokens.get("sh"));
        assertEquals(1, tokens.get("ho"));
        assertEquals(1, tokens.get("ol"));
        assertEquals(1, tokens.get("ld"));
        assertEquals(1, tokens.get("d#"));

        var qGrams3 = new QGrams(3, "Threshold");

        tokens = qGrams3.tokens();
        assertEquals(1, tokens.get("##t"));
        assertEquals(1, tokens.get("#th"));
        assertEquals(1, tokens.get("thr"));
        assertEquals(1, tokens.get("hre"));
        assertEquals(1, tokens.get("res"));
        assertEquals(1, tokens.get("esh"));
        assertEquals(1, tokens.get("sho"));
        assertEquals(1, tokens.get("hol"));
        assertEquals(1, tokens.get("old"));
        assertEquals(1, tokens.get("ld#"));
        assertEquals(1, tokens.get("d##"));
    }

    @Test
    @DisplayName("QGrams: Similarity returns expected values")
    public void testQGramsSimilarity() {
        var q1 = new QGrams(2, "salesal"); // #s sa al le es sa al l#
        var q2 = new QGrams(2, "alale"); // #a al la al le e#

        assertEquals(3.0 / 14.0, q1.similarity(q2));
    }

    @Test
    @DisplayName("QGrams: Similarity is symmetrical")
    public void testQGramsSymmetry() {
        var q1 = new QGrams(2, "salesal"); // #s sa al le es sa al l#
        var q2 = new QGrams(2, "alale"); // #a al la al le e#

        assertEquals(q1.similarity(q2), q2.similarity(q1));
    }
}
