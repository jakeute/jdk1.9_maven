package mjava.util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier，回环栅栏，它会阻塞一组线程直到这些线程同时达到某个条件才继续执行。它与CountDownLatch很类似（可以await组赛线程，直到count为零），但又不同，
 * CountDownLatch需要调用countDown()方法触发事件，CountDownLatch是一次性的，无法重置其次数，不仅可以实现多个线程等待一个线程条件成立，还能实现一个线程等待多个线程条件成立
 * 而CyclicBarrier不需要，它就像一个栅栏一样，当一组线程都到达了栅栏处才继续往下走。
 * <p>
 * 内部实现为 ReentrantLock 与 Condition
 *
 * （1）最后一个线程:当count减为0的时候，打破栅栏，它调用nextGeneration()方法通知条件队列中的等待线程转移到AQS的队列中等待被唤醒，并进入下一代。
 * <p>
 * （2）非最后一个线程走下面的for循环逻辑，这些线程首先会获取锁，然后阻塞在condition的await()方法处，
 * 它们会加入到条件队列中，等待被通知，当它们唤醒的时候再一个个获取锁，然后释放锁返回。
 */
public class CyclicBarrierTest {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            Thread.sleep(1400);
            new Thread(() -> {
                System.out.println("before");
                try {

                    //等待同步
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("after");
            }).start();
        }
    }

}
