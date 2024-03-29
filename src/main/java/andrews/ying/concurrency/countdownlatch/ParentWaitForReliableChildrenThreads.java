package andrews.ying.concurrency.countdownlatch;

import java.util.ArrayList;
import static java.util.Collections.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Use CountDownLatch to guarantee that all the children threads are complete and then parent thread can move forward.
 * Caution!!! If the children thread throws an exception and stopped before it calls the countDown() method on the
 * CountDownLatch object, then the parent thread will be stuck forever. To avoid this use the wait() method on the
 * CountDownLatch object with a time limit parameter in the parent thread. See @link andrews.ying.concurrency.countdownlatch.ParentWaitForUnreliableChildrenThreads.
 */
public class ParentWaitForReliableChildrenThreads {
    public static void main(String[] args) throws InterruptedException {
        List<String> sharedResource = synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new Worker(sharedResource, countDownLatch)))
                .limit(5).collect(Collectors.toList());

        workers.forEach(Thread::start);
        countDownLatch.await();
        System.out.println("All threads are done. The content of the shared resource is below: ");
        sharedResource.forEach(System.out::println);
    }
}
