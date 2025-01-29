import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUtils {

    public static boolean isSortedAscending(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false; 
            }
        }
        return true; 
    }

    public static List<Integer> generateRandomList(int size) {
        List<Integer> randomList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            randomList.add(random.nextInt(100) + 1); 
        }

        return randomList; 
    }
}
