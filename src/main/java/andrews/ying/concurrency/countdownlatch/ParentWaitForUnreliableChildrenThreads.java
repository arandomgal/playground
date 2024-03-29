package andrews.ying.concurrency.countdownlatch;

import java.util.ArrayList;
import static java.util.Collections.synchronizedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Use CountDownLatch to guarantee that all the children threads are complete and then parent thread can move forward.
 * This example uses a time limit in the wait() method so that if a child thread is terminated unexpectedly and failed
 * to have call the countDown() method on the CountDownLatch object, the parent class won't wait forever.
 */
public class ParentWaitForUnreliableChildrenThreads {
    public static void main(String[] args) throws InterruptedException {
        List<String> sharedResource = synchronizedList(new ArrayList<>());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new UnreliableWorker(sharedResource, countDownLatch)))
                .limit(5).collect(Collectors.toList());

        workers.forEach(Thread::start);
        countDownLatch.await(10L, TimeUnit.SECONDS);
        System.out.println("All threads are done. The content of the shared resource is below: ");
        sharedResource.forEach(System.out::println);
    }
}
