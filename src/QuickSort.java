public class QuickSort {

    /**
     * Swaps the elements in the provided positions.
     *
     * @param array array to sort
     * @param first first index
     * @param second second index
     */
    public void swap(int[] array, int first, int second) {
        int temporary = array[first];
        array[first] = array[second];
        array[second] = temporary;
    }

    /**
     * This function takes last element as pivot, places the pivot element at its correct position in sorted array,
     * and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot
     *
     * @param array array to sort
     * @param low first index
     * @param high last index
     * @return pivot element index
     */
    public int partitionArray(int[] array, int low, int high) {

        // pivot
        int p = array[high];
        int j = (low - 1);

        for (int i = low; i <= high - 1; i++) {

            // Check if current element is less than the pivot element
            if (array[i] < p) {
                // If current element is smaller than the pivot element then
                // Increment index of smaller element
                j++;
                swap(array, j, i);
            }
        }
        swap(array, j + 1, high);
        return (j + 1);
    }

    /**
     * Function that implements QuickSort.
     *
     * @param array array to sort
     * @param low first index
     * @param high last index
     */
     public void quickSort(int[] array, int low, int high) {
        if (low < high) {

            // pivot is partitioning index, arr[p] is now at right place
            int pivot = partitionArray(array, low, high);

            // Separately sort elements before partition and after partition
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }
}