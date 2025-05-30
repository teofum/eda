package com.itba.eda.Calculator;

import java.util.Scanner;

import com.itba.eda.Expression.ExpTree;

public class Calculator {
    public static void main(String[] args) {
        System.out.print("Input expression > ");

        Scanner is = new Scanner(System.in).useDelimiter("\\n");
        String line = is.nextLine();
        is.close();

        var tree = new ExpTree(line);

        System.out.println("preOrder: " + tree.preOrder());
        System.out.println("postOrder: " + tree.postOrder());
        System.out.println("inOrder: " + tree.inOrder());

        System.out.println("\nresult: " + tree.eval());
    }
}
