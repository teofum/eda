package com.itba.eda.IndexService;

import java.util.Arrays;

import com.itba.eda.Sorting.Sorting;

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

        // Sort the values
        Sorting.quickSort(storage, 0, count - 1);
    }

    public boolean search(int key) {
        int closest = getClosestPosition(key, false);
        return storage[closest] == key;
    }

    public void insert(int key) {
        if (count == storage.length)
            storage = Arrays.copyOf(storage, storage.length + chunkSize);

        int insertPos = getClosestPosition(key, false);

        // Shift all elements after key by one position
        for (int i = count; i > insertPos; i--)
            storage[i] = storage[i - 1];

        // Insert new element
        storage[insertPos] = key;
        count++;

    }

    public void delete(int key) {
        int deletePos = getClosestPosition(key, false);
        if (storage[deletePos] != key)
            return;

        // Delete the key and shift all subsequent values back one position
        for (int i = deletePos; i < count - 1; i++)
            storage[i] = storage[i + 1];
        count--;
    }

    public int occurrences(int key) {
        int left = getClosestPosition(key, false);
        if (storage[left] != key)
            return 0;

        int right = getClosestPosition(key, true);

        return right - left + 1;
    }

    public int getCount() {
        return count;
    }

    public int[] range(int left, int right, boolean includeLeft, boolean includeRight) {
        // Find boundaries
        int leftPos = getClosestPosition(left, !includeLeft);
        int rightPos = getClosestPosition(right, includeRight);

        if (!includeLeft && storage[leftPos] == left)
            leftPos++;
        if (!includeRight && storage[rightPos] == right)
            rightPos--;

        if (storage[rightPos] > right)
            rightPos--;

        if (leftPos >= count)
            return new int[] {};
        return Arrays.copyOfRange(storage, leftPos, rightPos + 1);
    }

    public int max() {
        if (count == 0)
            throw new RuntimeException("Index has no elements");

        return storage[count - 1];
    }

    public int min() {
        if (count == 0)
            throw new RuntimeException("Index has no elements");

        return storage[0];
    }

    private int getClosestPosition(int key, boolean last) {
        // Binary search on storage
        int left = 0, right = count - 1;
        int idx = -1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (key == storage[mid]) {
                idx = mid;

                if (last)
                    left = mid + 1;
                else
                    right = mid - 1;
            } else if (key < storage[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        if (idx == -1)
            idx = left;

        return idx;
    }

    private int align(int n, int to) {
        return to * ((n + to - 1) / to);
    }
}
