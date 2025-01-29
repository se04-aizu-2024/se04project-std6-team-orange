import java.util.ArrayList;
import java.util.List;

public class BubbleSort implements ArraylistSorter {

    public List<List<Integer>> sort(List<Integer> list) {
        List<List<Integer>> steps = new ArrayList<>();
        int n = list.size();
        
        // Bubble Sortアルゴリズムの実装
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                // 隣接要素を比較して、順番が逆なら交換
                if (list.get(j) > list.get(j + 1)) {
                    // 値を交換
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    
                    // 手順を記録
                    steps.add(List.of(j, j + 1));
                }
            }
        }
        return steps;
    }
}
