package andrews.ying.concurrency.countdownlatch;

import java.sql.PseudoColumnUsage;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {
    private List<String> sharedResource;
    private CountDownLatch countDownLatch;

    public Worker(List<String> sharedResource, CountDownLatch countDownLatch) {
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
        int secondsToSleep = new Random().nextInt(10);
        System.out.println("Doing some work for " + secondsToSleep + " seconds");
        try {
            Thread.sleep(secondsToSleep * 1000l);
        } catch (InterruptedException e) {
            System.out.println("Thread sleep interrupted");
        }
    }
}