package com.itba.eda.IndexService;

import java.util.Arrays;

import com.itba.eda.Sorting.Sorting;

public class IndexWithDuplicates<T extends Comparable<? super T>> implements IndexService<T> {
    private final static int chunkSize = 256;

    private int count; // Actual item count, storage.length is the capacity
    private Object[] storage;

    public IndexWithDuplicates() {
        storage = new Object[chunkSize];
        count = 0;
    }

    @SuppressWarnings("unchecked")
    public void initialize(T[] elements) {
        if (elements == null)
            throw new NullPointerException("Elements array is null");

        // Align storage size to chunk size
        storage = Arrays.copyOf(elements, align(elements.length, chunkSize));
        count = elements.length;

        // Sort the values
        Sorting.quickSort((T[]) storage, 0, count - 1);
    }

    public boolean search(T key) {
        int closest = getClosestPosition(key, false);
        return storage[closest] == key;
    }

    public void insert(T key) {
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

    public void delete(T key) {
        int deletePos = getClosestPosition(key, false);
        if (storage[deletePos] != key)
            return;

        // Delete the key and shift all subsequent values back one position
        for (int i = deletePos; i < count - 1; i++)
            storage[i] = storage[i + 1];
        count--;
    }

    public int occurrences(T key) {
        int left = getClosestPosition(key, false);
        if (storage[left] != key)
            return 0;

        int right = getClosestPosition(key, true);

        return right - left + 1;
    }

    public int getCount() {
        return count;
    }

    @SuppressWarnings("unchecked")
    public T[] range(T left, T right, boolean includeLeft, boolean includeRight) {
        // Find boundaries
        int leftPos = getClosestPosition(left, !includeLeft);
        int rightPos = getClosestPosition(right, includeRight);

        if (!includeLeft && left.compareTo((T) storage[leftPos]) == 0)
            leftPos++;
        if (!includeRight && right.compareTo((T) storage[rightPos]) == 0)
            rightPos--;

        if (rightPos >= count || right.compareTo((T) storage[rightPos]) < 0)
            rightPos--;

        if (leftPos >= count)
            return (T[]) Arrays.copyOfRange(storage, 0, 0);
        return (T[]) Arrays.copyOfRange(storage, leftPos, rightPos + 1);
    }

    @SuppressWarnings("unchecked")
    public T max() {
        if (count == 0)
            throw new RuntimeException("Index has no elements");

        return (T) storage[count - 1];
    }

    @SuppressWarnings("unchecked")
    public T min() {
        if (count == 0)
            throw new RuntimeException("Index has no elements");

        return (T) storage[0];
    }

    @SuppressWarnings("unchecked")
    private int getClosestPosition(T key, boolean last) {
        // Binary search on storage
        int left = 0, right = count - 1;
        int idx = -1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int comp = key.compareTo((T) storage[mid]);

            if (comp == 0) {
                idx = mid;

                if (last)
                    left = mid + 1;
                else
                    right = mid - 1;
            } else if (comp < 0) {
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
