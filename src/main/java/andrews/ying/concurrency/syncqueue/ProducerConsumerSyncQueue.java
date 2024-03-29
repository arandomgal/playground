package andrews.ying.concurrency.syncqueue;

import java.util.concurrent.*;


/**
 * Synchronous queues are similar to rendezvous channels used in CSP and Ada.
 * They are well suited for handoff designs, in which an object running in one thread
 * must sync up with an object running in another thread in order to hand it some
 * information,event, or task.
 */
public class ProducerConsumerSyncQueue {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        SynchronousQueue<Integer> queue = new SynchronousQueue<>();

        Runnable producer = () -> {
            Integer producedElement = ThreadLocalRandom
                    .current()
                    .nextInt();
            try {
                queue.put(producedElement);
                System.out.println("Producer just put number [" + producedElement + "] in the synchronous queue.");
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        };

        Runnable consumer = () -> {
            try {
                Integer consumedElement = queue.take();
                System.out.println("Consumer just retrieved number [" + consumedElement + "] from the synchronous queue.");
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
