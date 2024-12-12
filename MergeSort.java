import java.util.List;

public class MergeSort implements ArraylistSorter{

    public List<Integer> sort(List<Integer> list){

        //If the list is empty or has only one element, it is already sorted.                
        if(list.size() <= 1){
            return list;
        }

        // Split the list into two                                                           
        int mid = list.size() / 2;
        List<Integer> left = list.subList(0, mid);
        List<Integer> right = list.subList(mid, list.size());

        // Sort both halves recursively.                                                     
        List<Integer> sortedLeft = sort(new ArrayList<>(left));
        List<Integer> sortedRight = sort(new ArrayList<>(right));

        // Combine the sorted halves and return the result.                                  
        return merge(sortedLeft, sortedRight);
    }

    private List<Integer> merge(List<Integer> left, List<Integer> right){
        List<Integer> merged = new ArrayList<>();
        int i = 0, j = 0;

        // Combine the elements of both lists in sorted order.                               
        while (i < left.size() && j < right.size()) {
            if (left.get(i) <= right.get(j)) {
                merged.add(left.get(i));
                i++;
            } else {
                merged.add(right.get(j));
                j++;
            }
        }

          // Add the remaining elements from the left list.                                    
        while (i < left.size()) {
            merged.add(left.get(i));
            i++;
        }

        // Add remaining elements from the list on the right.                                
        while (j < right.size()) {
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }
}
