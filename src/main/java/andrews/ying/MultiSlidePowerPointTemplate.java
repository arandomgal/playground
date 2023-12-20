package andrews.ying;

import java.util.ArrayList;
import java.util.List;


/**
 * The heart of the issue is Java Collection traversing
 *
 * https://rollbar.com/blog/java-concurrentmodificationexception/#:~:text=The%20ConcurrentModificationException%20generally%20occurs%20when,it%2C%20a%20ConcurrentModificationException%20is%20thrown.
 */

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
