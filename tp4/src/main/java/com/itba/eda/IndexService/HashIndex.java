package com.itba.eda.IndexService;

import java.util.function.Function;

public class HashIndex<K, V> {
    final private int INITIAL_SIZE = 256;
    final private double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    private Entry<K, V>[] storage = (Entry<K, V>[]) new Entry[INITIAL_SIZE];
    private int size = 0;

    private final Function<K, Integer> prehash;

    public HashIndex(Function<K, Integer> prehash) {
        this.prehash = prehash;
    }

    public HashIndex() {
        this(null);
    }

    public void put(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        if (value == null)
            throw new IllegalArgumentException("Value cannot be null");

        var idx = hash(key);
        int firstLogical = -1;

        // Increment idx until we find the element or a physical deletion, storing
        // the first logical deletion if any
        while (storage[idx] != null && !key.equals(storage[idx].key)) {
            if (storage[idx].key == null && firstLogical == -1)
                firstLogical = idx;

            idx = (idx + 1) % storage.length;
        }

        var putIdx = firstLogical != -1 ? firstLogical : idx;
        if (storage[putIdx] == null || storage[putIdx].key == null)
            size++;

        storage[putIdx] = new Entry<>(key, value);

        if ((double) size / (double) storage.length >= LOAD_FACTOR)
            grow();
    }

    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        var idx = hash(key);
        Entry<K, V> entry;
        while ((entry = storage[idx]) != null && !key.equals(entry.key))
            idx = (idx + 1) % storage.length;

        return entry == null ? null : entry.value;
    }

    public boolean remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        var idx = hash(key);
        Entry<K, V> entry;
        while ((entry = storage[idx]) != null && !key.equals(entry.key))
            idx = (idx + 1) % storage.length;

        if (entry == null)
            return false;

        if (storage[idx + 1] == null)
            storage[idx] = null; // Physical deletion
        else
            storage[idx] = new Entry<>(null, null); // Logical deletion, represented by an entry with null key and value

        size--;
        return true;
    }

    public int size() {
        return size;
    }

    static private final record Entry<K, V>(K key, V value) {
        @Override
        public String toString() {
            return String.format("[%s: %s]", key, value);
        }
    }

    private int hash(K key) {
        int pre = prehash != null ? prehash.apply(key) : key.hashCode();
        return Integer.remainderUnsigned(pre, storage.length);
    }

    private void grow() {
        @SuppressWarnings("unchecked")
        var newStorage = (Entry<K, V>[]) new Entry[storage.length * 2];

        for (int i = 0; i < storage.length; i++) {
            var entry = storage[i];
            if (entry != null && entry.key != null) {
                var idx = hash(entry.key);
                while (newStorage[idx] != null)
                    idx++;

                newStorage[idx] = entry;
            }
        }

        storage = newStorage;
    }
}
