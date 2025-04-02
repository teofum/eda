package com.itba.eda.IndexService;

public interface IndexService {
    /*
     * Initialize the index with specified values. Existing values are discarded.
     * Throws if elements is null.
     */
    void initialize(int[] elements);

    /*
     * Search for a specific key.
     */
    boolean search(int key);

    /*
     * Insert a new key into the index.
     */
    void insert(int key);

    /*
     * Delete a key, if it exists in the index. If the key is not present, do
     * nothing.
     */
    void delete(int key);

    /*
     * Return the number of ocurrences of a key in the index.
     */
    int occurrences(int key);
}
