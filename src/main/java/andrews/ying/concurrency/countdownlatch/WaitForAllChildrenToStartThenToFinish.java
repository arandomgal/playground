package andrews.ying.concurrency.countdownlatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Use CountDownLatch to simulate massive parallel threads.
 * Sometimes we desire to run thousands of threads in parallel to
 * test performance of concurrency issues. We desire for these
 * threads to all start at the exact the same time. We can use
 * 3 CountDownLatch objects to control (1) the wait for all child
 * threads to get ready, (2) the release for all the threads to go,
 * and (3) the wait for all the child threads to finish.
 */
public class WaitForAllChildrenToStartThenToFinish {
    public static void main(String[] args) throws InterruptedException {
        List<String> sharedResource = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch readyThreadCounter = new CountDownLatch(5);
        CountDownLatch callingThreadBlocker = new CountDownLatch(1);
        CountDownLatch completedThreadCounter = new CountDownLatch(5);
        List<Thread> workers = Stream
                .generate(() -> new Thread(new WaitingWorker(
                        sharedResource, readyThreadCounter, callingThreadBlocker, completedThreadCounter)))
                .limit(5).collect(Collectors.toList());

        workers.forEach(Thread::start);
        System.out.println("Main thread: wait for all workers to get ready...");
        readyThreadCounter.await(30L, TimeUnit.SECONDS);
        System.out.println("Main thread: all workers are ready to start, now release the blocker latch.");
        callingThreadBlocker.countDown();
        System.out.println("Main thread: wait for all workers to finish...");
        completedThreadCounter.await(30L, TimeUnit.SECONDS);
        System.out.println("All threads are done. The content of the shared resource is below: ");
        sharedResource.forEach(System.out::println);
    }
}
