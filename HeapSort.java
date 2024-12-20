import java.util.List;

public class HeapSort implements ArraylistSorter {
    
    HeapSort() {}

    public List<Integer> sort(List<Integer> list) {
        buildHeap(list);


        for (int i = list.size() - 1; i > 0; i--) {
            swap(list, 0, i);
            heapify(list, 0, i);
        }
        return list;
    }


    private void buildHeap(List<Integer> list) {

        for (int i = list.size() / 2 - 1; i >= 0; i--) {
            heapify(list, i, list.size());
        }
    }

    private void heapify(List<Integer> list, int root, int heapSize) {
        int largest = root;
        int left = 2 * root + 1; 
        int right = 2 * root + 2; 


        if (left < heapSize && list.get(left) > list.get(largest)) {
            largest = left;
        }


        if (right < heapSize && list.get(right) > list.get(largest)) {
            largest = right;
        }

        if (largest != root) {
            swap(list, root, largest);
            heapify(list, largest, heapSize);
        }
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
