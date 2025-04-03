package com.itba.eda.Sorting;

public class Sorting {
    /*
     * Sort an array in place
     */
    public static <T extends Comparable<? super T>> void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static <T extends Comparable<? super T>> void quickSort(T[] array, int begin, int end) {
        if (begin >= end)
            return;

        T pivot = array[end];
        int i = begin;
        for (int j = begin; j < end; j++) {
            if (pivot.compareTo(array[j]) >= 0) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, end);
        quickSort(array, begin, i - 1);
        quickSort(array, i + 1, end);
    }

    private static <T extends Comparable<? super T>> void swap(T[] array, int i0, int i1) {
        T temp = array[i0];
        array[i0] = array[i1];
        array[i1] = temp;
    }
}
