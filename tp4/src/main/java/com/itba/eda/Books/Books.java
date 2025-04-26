package com.itba.eda.Books;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.itba.eda.IndexService.HashIndex;

public class Books {
    private record Book(String title, String category, int rank) {
    }

    public static void main(String[] args) {
        var filename = "amazon-categories30.txt";
        var is = HashIndex.class.getClassLoader().getResourceAsStream(filename);

        var in = new InputStreamReader(is);
        var br = new BufferedReader(in);

        var map1 = new HashIndex<String, Book>((String s) -> s.codePointAt(0));
        var map2 = new HashIndex<String, Book>((String s) -> s.codePoints().reduce(0, (sum, cp) -> sum + cp));
        var map3 = new HashIndex<String, Book>(); // Using java hashcode

        int collisions1 = 0;
        int collisions2 = 0;
        int collisions3 = 0;

        String line;
        try {
            while ((line = br.readLine()) != null) {
                var scanner = new Scanner(line).useDelimiter("#");
                var title = scanner.next();
                var category = scanner.next();
                var rank = scanner.next();
                scanner.close();

                var book = new Book(title, category, Integer.parseInt(rank));

                try {
                    map1.put(title, book);
                } catch (RuntimeException e) {
                    collisions1++;
                }

                try {
                    map2.put(title, book);
                } catch (RuntimeException e) {
                    collisions2++;
                }

                try {
                    map3.put(title, book);
                } catch (RuntimeException e) {
                    collisions3++;
                }
            }
        } catch (IOException e) {
            System.err.println("oh no");
        }

        System.out.println(String.format("First char: %d collisions", collisions1));
        System.out.println(String.format("Sum: %d collisions", collisions2));
        System.out.println(String.format("hashCode: %d collisions", collisions3));
    }
}
