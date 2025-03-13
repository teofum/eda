package com.itba.eda.Soundex;

public class Soundex {
    private static final int CODE_LENGTH = 4;
    private static final int[] CODES = {
            0, 1, 2, 3, 0, 1, 2, 0, 0, 2, 2, 4, 5, 5, 0, 1, 2, 6, 2, 3, 0, 1, 0, 2, 0, 2
    };

    private final String encoded;

    Soundex(String text) {
        encoded = encode(text);
    }

    public static double similarity(String text1, String text2) {
        return (new Soundex(text1)).similarity(new Soundex(text2));
    }

    public double similarity(Soundex other) {
        int matches = 0;
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (encoded.charAt(i) == other.encoded.charAt(i)) matches++;
        }

        return (double) matches / (double) CODE_LENGTH;
    }

    @Override
    public String toString() {
        return encoded;
    }

    private static String encode(String text) {
        if (text.isEmpty()) throw new IllegalArgumentException("Argument must not be empty");

        var in = text.toCharArray();
        var out = new char[CODE_LENGTH];

        // Skip until the first alphabetic character
        int i = 0;
        while (i < in.length && !Character.isLetter(in[i])) i++;

        // If we reached the end of the string, throw — input is supposed to contain at least one letter
        if (i == in.length) throw new IllegalArgumentException("Argument must contain at least one letter");

        // Output the first character as-is, converting to uppercase
        out[0] = Character.toUpperCase(in[i]);
        int last = getCode(in[i]);

        int current = 0;
        int w = 1;

        // Iterate the array, skipping characters with code 0 and repeats and outputting the rest
        // until a max length of three numbers is reached
        for (i++; i < text.length() && w < out.length; i++, last = current) {
            if (Character.isLetter(in[i])) {
                current = getCode(in[i]);
                if (current != 0 && current != last) {
                    out[w++] = (char) ('0' + current);
                }
            }
        }

        // Fill the output with zeros
        while (w < 4) out[w++] = '0';

        return new String(out);
    }

    private static int getCode(char c) {
        return CODES[Character.toUpperCase(c) - 'A'];
    }
}
