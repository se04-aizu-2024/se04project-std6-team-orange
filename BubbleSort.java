import java.util.List;
public class BubbleSort implements ArraylistSorter {

    public BubbleSort() {}

    @Override
    public List<Integer> sort(List<Integer> list) {
        int n = list.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    swap(list, j, j + 1);
                    swapped = true; 
                }
            }
            if (!swapped) {
                break;
            }
        }
        return list;
    }

    private void swap(List<Integer> list, int i, int j) {
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
