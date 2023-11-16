package exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafetyList {
    // BEGIN
    private Integer[] list;

    public synchronized void add(Integer elem) {
        if (list == null) {
            Integer[] li = new Integer[1];
            li[0] = elem;
            this.list = li;
            return;
        }
        List<Integer> arrList = new ArrayList<>(Arrays.asList(this.list));
        arrList.add(elem);
        this.list = arrList.toArray(this.list);
    }

    public int get(int index) {
        return index >= 0 && list!=null ? list[index] : -1;
    }

    public int getSize() {
        return list!=null ? Arrays.asList(list).size() : 0;
    }
    // END
}
