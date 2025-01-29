import java.util.ArrayList;
import java.util.List;

public class HeapSort implements ArraylistSorter {

    public List<List<Integer>> sort(List<Integer> list) {
        List<List<Integer>> steps = new ArrayList<>();
        int n = list.size();
        
        // Build a max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i, steps);
        }
        
        // Extract elements from heap one by one
        for (int i = n - 1; i >= 1; i--) {
            // Move current root to end
            swap(list, 0, i);
            steps.add(List.of(0, i));
            
            // Call max heapify on the reduced heap
            heapify(list, i, 0, steps);
        }
        
        return steps;
    }

    private void heapify(List<Integer> list, int n, int i, List<List<Integer>> steps) {
        int largest = i;  // Initialize largest as root
        int left = 2 * i + 1;  // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2
        
        // If left child is larger than root
        if (left < n && list.get(left) > list.get(largest)) {
            largest = left;
        }
        
        // If right child is larger than largest so far
        if (right < n && list.get(right) > list.get(largest)) {
            largest = right;
        }
        
        // If largest is not root
        if (largest != i) {
            swap(list, i, largest);
            steps.add(List.of(i, largest));
            
            // Recursively heapify the affected sub-tree
            heapify(list, n, largest, steps);
        }
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
