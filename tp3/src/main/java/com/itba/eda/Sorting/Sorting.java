package com.itba.eda.Sorting;

public class Sorting {
    /*
     * Sort an array in place
     */
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int begin, int end) {
        if (begin >= end)
            return;

        int pivot = array[end], i = begin;
        for (int j = begin; j < end; j++) {
            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }

        swap(array, i, end);
        quickSort(array, begin, i - 1);
        quickSort(array, i + 1, end);
    }

    private static void swap(int[] array, int i0, int i1) {
        int temp = array[i0];
        array[i0] = array[i1];
        array[i1] = temp;
    }
}
