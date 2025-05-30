package tfumagalli;

import java.util.Iterator;

public class SortedCompactedList<T extends Comparable<? super T>> implements Iterable<T> {
    private Node root;

    public void insert(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        if (root == null) {
            root = new Node(data);
            return;
        }

        root = root.insert(data);
        return;
    }

    public boolean remove(T data) {
        if (root == null) {
            return false;
        }

        boolean[] rta = new boolean[1];
        root = root.remove(data, rta);
        return rta[0];
    }

    public int size() {
        if (root == null)
            return 0;
        return root.size();
    }

    public void dump() {
        Node current = root;

        System.out.println("Dump");

        while (current != null) {
            for (int i = 0; i < current.count; i++)
                System.out.println(current.data);
            current = current.next;
        }
    }

    public void dumpNodes() {
        // No modificar
        Node current = root;

        System.out.println("DumpNodes");

        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    private final class Node {
        private T data;
        private Node next;
        private int count;

        private Node(T data) {
            this.data = data;
            this.count = 1;
        }

        private Node(T data, Node next) {
            this.data = data;
            this.count = 1;
            this.next = next;
        }

        private Node insert(T data) {
            var cmp = this.data.compareTo(data);
            if (cmp < 0) {
                if (next == null) {
                    next = new Node(data);
                    return this;
                }

                next = next.insert(data);
                return this;
            } else if (cmp == 0) {
                count++;
                return this;
            }

            return new Node(data, this);
        }

        private Node remove(T data, boolean[] rta) {
            if (this.data.compareTo(data) == 0) {
                rta[0] = true;
                count--;
                return count > 0 ? this : next;
            }

            if (next != null && this.data.compareTo(data) < 0) {
                next = next.remove(data, rta);
                return this;
            }

            rta[0] = false;
            return this;
        }

        private int size() {
            if (next == null)
                return count;
            return count + next.size();
        }
    }

    public Iterator<T> iterator() {
        return new SortedCompactedListIterator();
    }

    private class SortedCompactedListIterator implements Iterator<T> {
        private Node current;
        private Node last;
        private int remainingCount;

        public SortedCompactedListIterator() {
            current = root;
            remainingCount = current != null ? current.count : 0;
        }

        public boolean hasNext() {
            return remainingCount > 0;
        }

        public T next() {
            last = current;

            remainingCount--;
            if (remainingCount == 0) {
                current = current.next;
                remainingCount = current != null ? current.count : 0;
            }

            return last.data;
        }

        public void remove() {
            System.out.println("removing " + last.data);

            // need to remove from the root, as we might need to change "next" for the node
            // before last
            root = root.remove(last.data, new boolean[1]);
        }
    }

    public static void main(String[] args) {
        System.out.println("**** TEST 1 ****");
        test1();
        System.out.println("**** TEST 2 ****");
        test2();
        System.out.println("**** TEST 3 ****");
        test3();
        System.out.println("**** TEST 4 ****");
        test4();
        System.out.println("**** TEST 5 ****");
        test5();
    }

    private static void initializeList(SortedCompactedList<String> l) {
        l.insert("hola");
        l.insert("tal");
        l.insert("ah");
        l.insert("veo");
        l.insert("ah");
        l.insert("bio");
        l.insert("ah");
        l.insert("veo");
        l.insert("ah");
        l.insert("tal");
    }

    private static void test1() {
        SortedCompactedList<String> l = new SortedCompactedList<>();
        initializeList(l);

        System.out.print("Size: ");
        System.out.println(l.size());
        System.out.println();

        l.dump();
        System.out.println();
        l.dumpNodes();
        System.out.println();
    }

    private static void test2() {
        SortedCompactedList<String> l = new SortedCompactedList<>();
        initializeList(l);

        l.remove("hola");
        l.remove("tal");
        l.remove("ah");
        l.remove("veo");

        System.out.println("After Removing");

        System.out.print("Size: ");
        System.out.println(l.size());
        System.out.println();

        l.dump();
        System.out.println();
        l.dumpNodes();
        System.out.println();
    }

    private static void test3() {
        SortedCompactedList<String> l = new SortedCompactedList<>();
        initializeList(l);

        System.out.println("Dump with Iterator");
        for (String s : l) {
            System.out.println(s);
        }
    }

    private static void test4() {
        SortedCompactedList<String> l = new SortedCompactedList<>();
        initializeList(l);

        // borro items pares
        for (Iterator<String> it = l.iterator(); it.hasNext();) {
            String currData = it.next();
            System.out.println("Salteo: " + currData);
            if (it.hasNext()) {
                currData = it.next();
                System.out.println("Borro: " + currData);
                it.remove();
            }
        }

        System.out.println("After Removing");

        System.out.print("Size: ");
        System.out.println(l.size());
        System.out.println();

        l.dump();
        System.out.println();
        l.dumpNodes();
        System.out.println();

        System.out.println("Dump with Iterator");

        for (String s : l) {
            System.out.println(s);
        }
    }

    private static void test5() {
        SortedCompactedList<String> l = new SortedCompactedList<>();
        initializeList(l);

        // borro items inpares
        for (Iterator<String> it = l.iterator(); it.hasNext();) {
            String currData = it.next();
            System.out.println("Borro: " + currData);
            it.remove();
            if (it.hasNext()) {
                currData = it.next();
                System.out.println("Salteo: " + currData);
            }
        }

        System.out.println("After Removing");

        System.out.print("Size: ");
        System.out.println(l.size());
        System.out.println();

        l.dump();
        System.out.println();
        l.dumpNodes();
        System.out.println();

        System.out.println("Dump with Iterator");

        for (String s : l) {
            System.out.println(s);
        }
    }
}
