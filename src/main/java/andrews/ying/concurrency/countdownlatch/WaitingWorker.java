package andrews.ying.concurrency.countdownlatch;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class WaitingWorker implements Runnable {

    private List<String> sharedResource;
    private CountDownLatch readyThreadCounter;
    private CountDownLatch callingThreadBlocker;
    private CountDownLatch completedThreadCounter;

    public WaitingWorker(
            List<String> sharedResource,
            CountDownLatch readyThreadCounter,
            CountDownLatch callingThreadBlocker,
            CountDownLatch completedThreadCounter) {

        this.sharedResource = sharedResource;
        this.readyThreadCounter = readyThreadCounter;
        this.callingThreadBlocker = callingThreadBlocker;
        this.completedThreadCounter = completedThreadCounter;
    }

    @Override
    public void run() {
        System.out.println("Count down readyThreadCounter in worker thread: " + Thread.currentThread().getName());
        readyThreadCounter.countDown();
        try {
            System.out.println("Wait for other workers in worker thread: " + Thread.currentThread().getName());
            callingThreadBlocker.await();
            doSomeWork();
            sharedResource.add("Did some work in thread: " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Count down completedThreadCounter in worker thread: " + Thread.currentThread().getName());
            completedThreadCounter.countDown();
        }
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