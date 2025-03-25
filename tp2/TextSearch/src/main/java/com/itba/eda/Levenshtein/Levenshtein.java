package com.itba.eda.Levenshtein;

public class Levenshtein {
    public static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        int[][] distance = new int[a.length() + 1][b.length() + 1];
        for (int j = 1; j <= b.length(); j++) {
            distance[0][j] = j;
        }
        for (int i = 1; i <= a.length(); i++) {
            distance[i][0] = i;

            for (int j = 1; j <= b.length(); j++) {
                var cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;
                distance[i][j] = Math.min(
                        Math.min(distance[i - 1][j], distance[i][j - 1]) + 1,
                        distance[i - 1][j - 1] + cost);
            }
        }

        return distance[a.length()][b.length()];
    }

    public static double similarity(String a, String b) {
        return 1.0 - (double) distance(a, b) / (double) Math.max(a.length(), b.length());
    }
}
