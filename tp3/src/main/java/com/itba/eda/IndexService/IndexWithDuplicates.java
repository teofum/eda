package com.itba.eda.IndexService;

import java.util.Arrays;

public class IndexWithDuplicates implements IndexService {
    private final static int chunkSize = 256;

    private int count; // Actual item count, storage.length is the capacity
    private int[] storage;

    public IndexWithDuplicates() {
        storage = new int[chunkSize];
        count = 0;
    }

    public void initialize(int[] elements) {
        if (elements == null)
            throw new NullPointerException("Elements array is null");

        // Align storage size to chunk size
        storage = Arrays.copyOf(elements, align(elements.length, chunkSize));
        count = elements.length;
    }

    public boolean search(int key) {
        int closest = getClosestPosition(key);
        return storage[closest] == key;
    }

    public void insert(int key) {
        if (count == storage.length)
            storage = Arrays.copyOf(storage, storage.length + chunkSize);

        int insertPos = getClosestPosition(key);

        // Shift all elements after key by one position
        for (int i = count; i > insertPos; i--)
            storage[i] = storage[i - 1];

        // Insert new element
        storage[insertPos] = key;
        count++;

    }

    public void delete(int key) {
        int deletePos = getClosestPosition(key);
        if (storage[deletePos] != key)
            return;

        // Delete the key and shift all subsequent values back one position
        for (int i = deletePos; i < count - 1; i++)
            storage[i] = storage[i + 1];
        count--;
    }

    public int occurrences(int key) {
        // Find *some* occurrence, not guaranteed to be the first
        int pos = getClosestPosition(key);
        if (storage[pos] != key)
            return 0;

        // Move left and right to find all occurrences
        // This is still faster than falling back to a linear search,
        // although worst case (entire array is the same value) is also O(n)
        int left = pos, right = pos;
        while (left > 0 && storage[left - 1] == key)
            left--;
        while (right < count - 1 && storage[right + 1] == key)
            right++;

        return right - left + 1;
    }

    public int getCount() {
        return count;
    }

    private int getClosestPosition(int key) {
        // Binary search on storage
        int left = 0, right = count - 1;

        while (left < right && storage[left] < key) {
            int idx = (left + right) / 2;

            if (storage[idx] > key)
                right = idx;
            else if (storage[idx] < key)
                left = idx + 1;
            else
                left = idx;
        }

        return left;
    }

    private int align(int n, int to) {
        return to * ((n + to - 1) / to);
    }
}
