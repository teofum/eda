package com.itba.eda.BST;

public class BSTTester {
    public static void main(String[] args) {
        var tree = new BST<Integer>();

        System.out.println(tree.hierarchy());
        System.out.println("preOrder: " + tree.preOrder());
        System.out.println("postOrder: " + tree.postOrder());
        System.out.println("inOrder: " + tree.inOrder());
        System.out.println("height: " + tree.height());

        tree.insert(50);
        tree.insert(60);
        tree.insert(80);
        tree.insert(20);
        tree.insert(50);
        tree.insert(70);
        tree.insert(40);
        tree.insert(44);
        tree.insert(10);
        tree.insert(40);

        System.out.println(tree.hierarchy());
        System.out.println("preOrder: " + tree.preOrder());
        System.out.println("postOrder: " + tree.postOrder());
        System.out.println("inOrder: " + tree.inOrder());
        System.out.println("height: " + tree.height());
    }
}
