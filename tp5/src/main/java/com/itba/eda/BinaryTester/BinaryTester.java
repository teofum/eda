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

            System.out.println(tree.hierarchy());
            System.out.println("preOrder: " + tree.preOrder());
            System.out.println("postOrder: " + tree.postOrder());
            System.out.println("byLevel:\n" + tree.byLevel());

            if (tree.equals(tree)) {
                System.out.println("the tree is equal to itself");
            } else {
                System.out.println("you really fucked up");
            }

            tree.toFile(filename + "_copy");
            var copy = new BinaryTree(filename + "_copy");
            System.out.println("preOrder: " + copy.preOrder());
            System.out.println("postOrder: " + copy.postOrder());
            System.out.println("byLevel:\n" + copy.byLevel());

            if (tree.equals(copy)) {
                System.out.println("the trees are equal");
            } else {
                System.out.println("you fucked up");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
