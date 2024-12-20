import java.util.List;

public abstract class ArraylistSorter{
    MainFrame frame;

    ArraylistSorter(MainFrame f) {
        frame = f;
    }

    abstract List<Integer> sort(List<Integer> list);
}
