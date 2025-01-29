import java.util.ArrayList;
import java.util.List;

public class MergeSort implements ArraylistSorter {

    
    public List<List<Integer>> sort(List<Integer> list) {
        // 手順を格納するリスト
        List<List<Integer>> steps = new ArrayList<>();
        // ソート処理の呼び出し
        mergeSort(list, 0, list.size() - 1, steps);
        return steps;
    }

    private void mergeSort(List<Integer> list, int left, int right, List<List<Integer>> steps) {
        if (left >= right) {
            return;
        }

        // 中央のインデックスを計算
        int mid = (left + right) / 2;

        // 左半分を再帰的にソート
        mergeSort(list, left, mid, steps);
        // 右半分を再帰的にソート
        mergeSort(list, mid + 1, right, steps);
        // 左右の半分をマージ
        merge(list, left, mid, right, steps);
    }

    private void merge(List<Integer> list, int left, int mid, int right, List<List<Integer>> steps) {
        // 左半分と右半分の要素を保持する一時リスト
        List<Integer> temp = new ArrayList<>();

        int i = left;        // 左半分のポインタ
        int j = mid + 1;     // 右半分のポインタ

        // 左右をマージして一時リストに追加
        while (i <= mid && j <= right) {
            if (list.get(i) <= list.get(j)) {
                temp.add(list.get(i));
                i++;
            } else {
                temp.add(list.get(j));
                j++;
            }
        }

        // 残りの要素を追加
        while (i <= mid) {
            temp.add(list.get(i));
            i++;
        }
        while (j <= right) {
            temp.add(list.get(j));
            j++;
        }

        // 元のリストにマージした結果を反映
        for (int k = 0; k < temp.size(); k++) {
            int index = left + k;   
            int newValue = temp.get(k); 
            if (!list.get(index).equals(newValue)){
                steps.add(List.of(index, newValue));
            }
            list.set(index, newValue); // 値更新
        }
    }
}