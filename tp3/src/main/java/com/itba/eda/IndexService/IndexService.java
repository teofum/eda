package com.itba.eda.IndexService;

public interface IndexService<T extends Comparable<? super T>> {
    /*
     * Initialize the index with specified values. Existing values are discarded.
     * Throws if elements is null.
     */
    void initialize(T[] elements);

    /*
     * Search for a specific key.
     */
    boolean search(T key);

    /*
     * Insert a new key into the index.
     */
    void insert(T key);

    /*
     * Delete a key, if it exists in the index. If the key is not present, do
     * nothing.
     */
    void delete(T key);

    /*
     * Return the number of ocurrences of a key in the index.
     */
    int occurrences(T key);

    /*
     * Returns an array containing all keys between left and right. Ends
     * may or may not be inclusive.
     */
    T[] range(T left, T right, boolean includeLeft, boolean includeRight);

    /*
     * Returns the maximum key.
     */
    T max();

    /*
     * Returns the minimum key.
     */
    T min();
}
