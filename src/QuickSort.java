import java.util.ArrayList;
import java.util.List;

public class QuickSort implements ArraylistSorter {
    List<List<Integer>> procedureList = new ArrayList<>();    

    public List<List<Integer>> sort(List<Integer> list) {
        sorter(list, 0, list.size());
        return procedureList;
    }


    private void sorter(List<Integer> list, int first, int last) {
        // If the number of elements in the target range is 1 or less, do nothing
        if ((last - first) <= 1 || last < first) {
            return;
        }
        
        // Use the last element as the pivot
        int pivot = list.get(last-1);
        
        int temp;  

        // i: The boundary between elements smaller and larger than the pivot
        int i = first;

        for(int j = first; j < last-1; j++) {
            // If an element smaller than the pivot is found, swap and update i
            if (list.get(j) < pivot) {
                temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
                List<Integer> tempList = new ArrayList<>();
                tempList.add(i);
                tempList.add(j);
                procedureList.add(tempList);
                i++;
            }
        }

        // Place the pivot in its correct position
        temp = list.get(i);
        list.set(i, list.get(last-1));
        list.set(last-1, temp);
        List<Integer> tempList = new ArrayList<>();
        tempList.add(i);
        tempList.add(last-1);
        procedureList.add(tempList);

        // Sort each partition excluding the pivot
        sorter(list, first, i);
        sorter(list, i+1, last);
    }
}
