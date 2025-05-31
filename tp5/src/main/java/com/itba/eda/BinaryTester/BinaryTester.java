package com.itba.eda.BinaryTester;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.itba.eda.BinaryTree.BinaryTree;

public class BinaryTester {
    public static void main(String[] args) {
        System.out.print("Input filename > ");

        Scanner is = new Scanner(System.in).useDelimiter("\\n");
        String filename = is.nextLine();
        is.close();

        try {
            var tree = new BinaryTree(filename);

            System.out.println("preOrder: " + tree.preOrder());
            System.out.println("postOrder: " + tree.postOrder());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
