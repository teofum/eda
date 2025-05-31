package com.itba.eda.BST;

public class BSTTester {
    public static void main(String[] args) {
        System.out.println("\n|========= TREE 1 =========|\n");

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
        System.out.println("byLevel: " + tree.byLevel());
        System.out.println("height: " + tree.height());

        tree.delete(44);
        System.out.println(tree.hierarchy());
        tree.delete(80);
        System.out.println(tree.hierarchy());
        tree.delete(20);
        System.out.println(tree.hierarchy());
        tree.delete(50);
        System.out.println(tree.hierarchy());
        tree.delete(50);
        System.out.println(tree.hierarchy());

        System.out.println("\n|========= TREE 2 =========|\n");

        var tree2 = new BST<Integer>();

        tree2.insert(35);
        tree2.insert(74);
        tree2.insert(20);
        tree2.insert(22);
        tree2.insert(55);
        tree2.insert(15);
        tree2.insert(8);
        tree2.insert(27);
        tree2.insert(25);

        System.out.println(tree2.hierarchy());
        System.out.println("preOrder: " + tree2.preOrder());
        System.out.println("postOrder: " + tree2.postOrder());
        System.out.println("inOrder: " + tree2.inOrder());
        System.out.println("byLevel: " + tree2.byLevel());
        System.out.println("height: " + tree2.height());

        var sb = new StringBuilder("iterator: ");
        for (var v : tree2)
            sb.append(v + " ");
        System.out.println(sb);

        System.out.println("\n|========= TREE 3 =========|\n");

        var tree3 = new BST<Integer>();

        tree3.insert(5);
        tree3.insert(70);
        tree3.insert(30);
        tree3.insert(35);
        tree3.insert(20);
        tree3.insert(40);
        tree3.insert(80);
        tree3.insert(90);
        tree3.insert(85);

        System.out.println(tree3.hierarchy());

        System.out.println("\n|========= TREE 4 =========|\n");

        var tree4 = new BST<Integer>();

        tree4.insert(5);
        tree4.insert(70);
        tree4.insert(30);
        tree4.insert(70);
        tree4.insert(20);
        tree4.insert(40);
        tree4.insert(80);
        tree4.insert(90);
        tree4.insert(85);

        System.out.println(tree4.hierarchy());

        System.out.println("\n|========= AVL 1 =========|\n");

        var avl = new AVL<Integer>();

        Integer[] values = { 1, 2, 4, 7, 15, 3, 10, 17, 19, 16 };
        for (var i : values) {
            System.out.println("Insert " + i);
            avl.insert(i);
            System.out.println(avl.hierarchy());
        }

        Integer[] values2 = { 19, 4, 15, 7, 1, 3, 16 };
        for (var i : values2) {
            System.out.println("Delete " + i);
            avl.delete(i);
            System.out.println(avl.hierarchy());
        }
    }
}
