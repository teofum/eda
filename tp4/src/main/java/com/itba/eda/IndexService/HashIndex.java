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

        var keyHash = hash(key);
        if (storage[keyHash] == null)
            size++;
        else if (!storage[keyHash].key.equals(key))
            throw new RuntimeException("Not implemented"); // Collision

        storage[keyHash] = new Entry<>(key, value);

        if ((double) size / (double) storage.length >= LOAD_FACTOR)
            grow();
    }

    public V get(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        var entry = storage[hash(key)];
        return entry == null ? null : entry.value;
    }

    public boolean remove(K key) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");

        var keyHash = hash(key);
        if (storage[keyHash] == null)
            return false;

        storage[keyHash] = null;
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
        System.out.println(String.format("pre: %d, hash: %d", pre, pre % storage.length));
        return Integer.remainderUnsigned(pre, storage.length);
    }

    private void grow() {
        @SuppressWarnings("unchecked")
        var newStorage = (Entry<K, V>[]) new Entry[storage.length * 2];

        for (int i = 0; i < storage.length; i++) {
            var entry = storage[i];
            if (entry != null)
                newStorage[hash(entry.key)] = entry;
        }

        storage = newStorage;
    }
}
