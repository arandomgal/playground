package andrews.ying.concurrency.countdownlatch;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Use CountDownLatch to do a one-time handoff from a producer to a consumer.
 */
public class ProducerConsumerCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        AtomicInteger sharedResource = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Runnable producer = () -> {
            Integer producedElement = ThreadLocalRandom
                    .current()
                    .nextInt();
            sharedResource.set(producedElement);
            System.out.println("Producer just put number [" + producedElement + "] in the shared resource.");
            countDownLatch.countDown();
        };

        Runnable consumer = () -> {
            try {
                countDownLatch.await();
                Integer consumedElement = sharedResource.get();
                System.out.println("Consumer just retrieved number [" + consumedElement + "] from the shared resource.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        executor.execute(producer);
        executor.execute(consumer);

        executor.awaitTermination(500, TimeUnit.MILLISECONDS);
        executor.shutdown();

    }
}
