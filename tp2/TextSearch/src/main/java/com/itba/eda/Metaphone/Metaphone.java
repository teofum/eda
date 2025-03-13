package com.itba.eda.Metaphone;

public class Metaphone {
    private final String encoded;

    Metaphone(String text) {
        encoded = encode(text);
    }

    @Override
    public String toString() {
        return encoded;
    }

    private static String encode(String text) {
        if (text.isEmpty()) throw new IllegalArgumentException("Argument must not be empty");

        var in = text.toCharArray();
        var out = new char[in.length * 2]; // Output is never longer than input * 2 (worst case: input string is all Xs)

        int r = 0, w = 0; // Read/write indices

        // Pre process string by removing all non-alphabetic characters and converting to uppercase
        // This makes lookahead simpler, and constant time
        while (r < in.length) {
            char c = in[r++];

            // Skip non-alphabetic characters, otherwise convert to uppercase
            if (!Character.isLetter(c)) continue;
            in[w++] = Character.toUpperCase(c);
        }

        if (w == 0) throw new IllegalArgumentException("Argument must contain at least one letter");
        while (w < in.length) in[w++] = 0; // Fill in with zeros if we removed any characters

        // Apply Metaphone (1990) transformations as we traverse the string
        // We can now assume the string only contains uppercase letters

        // 2. Drop first letter if beginning with: WR, AE, GN, KN, PN
        char c0 = in[0], c1 = in.length > 1 ? in[1] : 0, c2;
        var skipFirst = (c1 == 'R' && c0 == 'W') || (c1 == 'E' && c0 == 'A') || (c1 == 'N' && (c0 == 'G' || c0 == 'K' || c0 == 'P'));

        for (r = skipFirst ? 1 : 0, w = 0; r < in.length; r++) {
            char c = in[r];
            c1 = r + 1 < in.length ? in[r + 1] : 0;
            c2 = r + 2 < in.length ? in[r + 2] : 0;
            char b = r > 0 ? in[r - 1] : 0;

            if (c != 'C' && c1 == c) continue; // 1. Drop duplicates except C
            switch (c) {
                // 4. C -> X, S, K
                case 'C' -> {
                    if (c1 == 'H' || (c1 == 'I' && c2 == 'A')) c = 'X';
                    else if (c1 == 'I' || c1 == 'E' || c1 == 'Y') c = 'S';
                    else c = 'K';
                }

                // 5. D -> J, T
                case 'D' -> {
                    if (c1 == 'G' && (c2 == 'I' || c2 == 'E' || c2 == 'Y')) c = 'J';
                    else c = 'T';
                }

                // 6/7. G -> nil, J, K
                case 'G' -> {
                    // 6. G -> nil
                    if (c1 == 'H' && !(c2 == 0 || vowel(c2))) continue;
                    if (c1 == 'N' && (c2 == 0 || (in.length == r + 4 && c2 == 'E' && in[r + 3] == 'D'))) continue;

                    // 7. G -> J, K
                    if (b != 'G' && (c1 == 'I' || c1 == 'E' || c1 == 'Y')) c = 'J';
                    else c = 'K';
                }

                // 8. H -> nil
                case 'H' -> {
                    if (vowel(b) && !vowel(c1)) continue;
                    if (b == 'C' || b == 'S' || b == 'P' || b == 'T' || b == 'G') continue;
                }

                // 9. K -> nil
                case 'K' -> {
                    if (b == 'C') continue;
                }

                // 10. PH -> F
                case 'P' -> {
                    if (c1 == 'H') {
                        c = 'F';
                        r++; // Skip next letter
                    }
                }

                // 11. Q -> K
                case 'Q' -> c = 'K';

                // 12. S -> X
                case 'S' -> {
                    if (c1 == 'H' || (c1 == 'I' && (c2 == 'A' || c2 == 'O'))) c = 'X';
                }

                // 13-15. T -> X, nil, 0
                case 'T' -> {
                    // 15. T -> nil
                    if (c1 == 'C' && c2 == 'H') continue;

                    // 13. T -> X
                    if (c1 == 'I' && (c2 == 'A' || c2 == 'O')) {
                        c = 'X';
                    }

                    // 14. TH -> 0
                    else if (c1 == 'H') {
                        c = '0'; // "th" sound
                        r++; // Skip next letter
                    }
                }

                // 16. V -> F
                case 'V' -> c = 'F';

                // 17/18.
                case 'W' -> {
                    // 17. W -> nil
                    if (!vowel(c1)) continue;

                    // 18. WH -> W
                    if (r == 0 && c1 == 'H') r++; // Skip H in WH at begin
                }

                // 19. X -> S/KS
                case 'X' -> {
                    // Append a K before the S if not at beginning, this is the only rule that
                    // can actually make the string longer
                    if (r > 0) out[w++] = 'K';
                    c = 'S';
                }

                // 20. Y -> nil
                case 'Y' -> {
                    if (!vowel(c1)) continue;
                }

                // 21. Z -> S
                case 'Z' -> c = 'S';

                // 22. Drop vowels if not at begin
                default -> {
                    if (vowel(c) && r > 0) continue;
                }
            }

            out[w++] = c;
        }

        return new String(out, 0, w);
    }

    private static boolean vowel(char c) {
        return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}
