package andrews.ying.concurrency.countdownlatch;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class UnreliableWorker implements Runnable {
    private List<String> sharedResource;
    private CountDownLatch countDownLatch;

    public UnreliableWorker(List<String> sharedResource, CountDownLatch countDownLatch) {
        this.sharedResource = sharedResource;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        doSomeWork();
        System.out.println("Counted down in worker thread: " + Thread.currentThread().getName());
        sharedResource.add(Thread.currentThread().getName());
        countDownLatch.countDown();
    }

    private void doSomeWork() {
        boolean isReliable = new Random().nextInt(2) == 1;
        System.out.println("Doing some work for a " + (isReliable ? "reliable" : "unreliable") + " worker...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Thread sleep interrupted");
        }
        if (!isReliable) {
            throw new RuntimeException("An unreliable worker just blew up!!!");
        }
    }
}
