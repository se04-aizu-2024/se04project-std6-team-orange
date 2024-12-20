import java.util.List;
import java.util.ArrayList;

public class SortTest {

    public static void main(String[] args) {
        List<Integer> randomList = ListUtils.generateRandomList(10);
        System.out.println("元のリスト: " + randomList);

        BubbleSort bubbleSort = new BubbleSort();
        HeapSort heapSort = new HeapSort();

        testSortAlgorithm(bubbleSort, randomList, "BubbleSort");
        testSortAlgorithm(heapSort, randomList, "HeapSort");
    }


    private static void testSortAlgorithm(ArraylistSorter sorter, List<Integer> list, String algorithmName) {
        List<Integer> listCopy = new ArrayList<>(list);  
        
        List<Integer> sortedList = sorter.sort(listCopy);

        boolean isSortedBefore = ListUtils.isSortedAscending(list);
        System.out.println("ソート前: " + (isSortedBefore ? "昇順" : "昇順ではない"));

        System.out.println(algorithmName + "でソート後のリスト: " + sortedList);
        boolean isSorted = ListUtils.isSortedAscending(sortedList);
        System.out.println(algorithmName + " の結果: " + (isSorted ? "昇順" : "昇順ではない"));
        System.out.println();
    }
}
