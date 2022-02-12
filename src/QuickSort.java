import java.util.LinkedList;

public class QuickSort {


    /**
     * Swaps the elements in the provided positions.
     *
     * @param list   list to sort
     * @param first  first index
     * @param second second index
     */
    public void swap(LinkedList<Integer> list, int first, int second) {
        int temporary = list.get(first);
        list.add(first, list.get(second));
        list.add(second, temporary);
    }

    /**
     * This function takes last element as pivot, places the pivot element at its correct position in sorted array,
     * and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot
     *
     * @param list list to sort
     * @param low  first index
     * @param high last index
     * @return pivot element index
     */
    public int partitionArray(LinkedList<Integer> list, int low, int high) {

        // pivot
        int p = list.get(high);
        int j = (low - 1);

        for (int i = low; i <= high - 1; i++) {

            // Check if current element is less than the pivot element
            if (list.get(i) < p) {
                // If current element is smaller than the pivot element then
                // Increment index of smaller element
                j++;
                swap(list, j, i);
            }
        }
        swap(list, j + 1, high);
        return (j + 1);
    }

    /**
     * Function that implements QuickSort.
     *
     * @param list list to sort
     * @param low  first index
     * @param high last index
     */
    public void quickSort(LinkedList<Integer> list, int low, int high) {
        if (low < high) {

            // pivot is partitioning index, arr[p] is now at right place
            int pivot = partitionArray(list, low, high);

            // Separately sort elements before partition and after partition
            quickSort(list, low, pivot - 1);
            quickSort(list, pivot + 1, high);
        }
    }

    public LinkedList<Integer> sort(LinkedList<Integer> list) {
        quickSort(list, 0, list.size() - 1);
        return list;
    }
}