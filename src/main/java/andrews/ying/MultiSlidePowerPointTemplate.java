package andrews.ying;

import java.util.ArrayList;
import java.util.List;

public class MultiSlidePowerPointTemplate {
    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);
        integers.add(3);
        System.out.println("before: " + integers);

//        for (Integer integer : integers) {
//            integers.remove(1);
//        }
        for (int i = 0; i < integers.size(); i++) {
            integers.remove(1);
        }

        System.out.println("after: " + integers);
    }
}
