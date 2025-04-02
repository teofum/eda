package com.itba.eda.QGrams;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class QGrams {
    private final int N;

    private Map<String, Integer> qgrams;
    private int qgramCount;

    public QGrams(int N, String s) {
        this.N = N;
        this.qgrams = new HashMap<>();

        var padding = "#".repeat(N - 1);
        var paddedString = padding + s.toLowerCase() + padding;
        qgramCount = 0;

        for (int i = 0; i <= paddedString.length() - N; i++) {
            var qgram = paddedString.substring(i, i + N);
            qgrams.compute(qgram, (_, v) -> (v == null) ? 1 : v + 1);
            qgramCount++;
        }
    }

    public double similarity(QGrams other) {
        if (N != other.N)
            throw new IllegalArgumentException("QGram length does not match");

        var matching = 0;
        for (var e : qgrams.entrySet()) {
            var v = other.qgrams.get(e.getKey());
            if (v != null)
                matching += Math.min(v, e.getValue());
        }

        return (double) matching / (double) (qgramCount + other.qgramCount);
    }

    public Map<String, Integer> tokens() {
        return qgrams;
    }
}
