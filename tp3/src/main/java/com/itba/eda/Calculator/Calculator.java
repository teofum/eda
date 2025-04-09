package com.itba.eda.Calculator;

import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        var lineScanner = new Scanner(System.in).useDelimiter("\\n");
        var eval = new Evaluator();

        while (lineScanner.hasNext()) {
            var line = lineScanner.next();
            if (line.toLowerCase().equals("exit"))
                break;

            try {
                var result = eval.evaluate(line);
                System.out.println(result);
            } catch (RuntimeException e) {
                System.out.println("ERROR: " + e);
            }
        }

        lineScanner.close();
    }
}
