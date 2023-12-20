package andrews.ying;

import java.util.HashMap;
import java.util.Map;

public class AreTwoMapsEqual {
    public static void main(String[] args) {
        Map<Integer, String> map1 = new HashMap<>();
        map1.put(1, "A");
        map1.put(2, "B");
        Map<Integer, String> map2 = new HashMap<>();
        map2.put(1, "A");
        map2.put(2, "B");
        System.out.println("map1 equals map2? " + map1.equals(map2));
        System.out.println("map1 == map2? " + (map1 == map2));
    }
}
