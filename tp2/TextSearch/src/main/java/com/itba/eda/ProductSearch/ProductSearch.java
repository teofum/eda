package com.itba.eda.ProductSearch;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.itba.eda.Levenshtein.Levenshtein;
import com.itba.eda.Metaphone.Metaphone;
import com.itba.eda.QGrams.QGrams;
import com.itba.eda.Soundex.Soundex;

public class ProductSearch {
    private static record ProductResults(String product, Map<String, Double> result) {
    }

    private static record ProductResult(String product, Map.Entry<String, Double> result) {
    }

    public static void main(String[] args) {
        try {
            var productsPath = args[0];
            var path = Paths.get(productsPath);
            System.out.println(path);
            var products = Files.readAllLines(path);

            var scanner = new Scanner(System.in);
            while (true) {
                var input = scanner.nextLine();
                var query = normalizeQuery(input);

                if (query.equals("exit"))
                    break;

                System.out.println("Search results for \"" + query + "\"");

                products.stream()
                        .map((product) -> new ProductResults(product, similarity(product, query)))
                        .map((presults) -> {
                            var bestResult = presults.result().entrySet().stream().reduce((best, res) -> {
                                if (best == null || res.getValue() > best.getValue())
                                    return res;
                                return best;
                            }).get();
                            return new ProductResult(presults.product(), bestResult);
                        })
                        .sorted(Comparator.comparing((ProductResult r) -> r.result().getValue()).reversed())
                        .limit(5)
                        .forEach((result) -> {
                            var bestAlgo = result.result().getKey();
                            double bestScore = result.result().getValue();

                            System.out.println("\"" + result.product() + "\": " + bestScore + " (" + bestAlgo + ")");
                        });
            }

            scanner.close();
        } catch (Exception e) {
            System.err.println("File could not be opened: " + e.getMessage());
        }
    }

    private static String normalizeQuery(String q) {
        return q.trim().toLowerCase()
                .replace('ñ', 'n')
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replaceAll("\\s+", " ");
    }

    private static Map<String, Double> similarity(String a, String b) {
        var results = new HashMap<String, Double>();

        results.put("Soundex", Soundex.similarity(a, b));
        results.put("Metaphone + Levenshtein", Metaphone.levenshteinSimilarity(a, b));
        results.put("Levenshtein", Levenshtein.similarity(a, b));
        results.put("QGrams <N=3>", (new QGrams(3, a)).similarity(new QGrams(3, b)));

        return results;
    }
}
