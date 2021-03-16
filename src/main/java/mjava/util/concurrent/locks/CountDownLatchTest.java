package mjava.util.concurrent.locks;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch，可以翻译为倒计时器，但是似乎不太准确，它的含义是允许一个或多个线程等待其它线程的操作执行完毕后再执行后续的操作。
 * <p>
 * CountDownLatch的通常用法和Thread.join()有点类似，等待其它线程都完成后再执行主任务。
 * <p>
 * 构造方法需要传入一个count，也就是初始次数。
 * <p>
 * await()方法是等待其它线程完成的方法，它会先尝试获取一下共享锁，如果失败则进入AQS的队列中排队等待被唤醒。
 * 根据上面Sync的源码，我们知道，state不等于0的时候 tryAcquireShared()返回的是-1，也就是说count未减到0的时候所有调用await()方法的线程都要排队。
 * <p>
 * countDown()方法，会释放一个共享锁，也就是count的次数会减1。
 * 根据上面Sync的源码，我们知道，tryReleaseShared()每次会把count的次数减1，当其减为0的时候返回true，这时候才会唤醒所有等待的线程。
 * <p>
 * 分析ReentrantReadWriteLock的时候学习过AQS的共享锁模式，比如当前锁是由一个线程获取为互斥锁，那么这时候所有需要获取共享锁的线程都要进入AQS队列中进行排队，当这个互斥锁释放的时候，
 * 会一个接着一个地唤醒这些连续的排队的等待获取共享锁的线程，注意，这里的用语是“一个接着一个地唤醒”，也就是说这些等待获取共享锁的线程不是一次性唤醒的。
 * 说到这里，是不是很明白了？因为CountDownLatch的await()多个线程可以调用多次，当调用多次的时候这些线程都要进入AQS队列中排队，当count次数减为0的时候，
 * 它们都需要被唤醒，继续执行任务，如果使用互斥锁则不行，互斥锁在多个线程之间是互斥的，一次只能唤醒一个（队列头），不能保证当count减为0的时候这些调用了await()方法等待的线程都被唤醒。
 * 因为互斥锁每次都是CAS 0->1 ，信号量并不会超出1
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Aid thread is waiting for starting.");
                    startSignal.await();
                    // do sth
                    System.out.println("Aid thread is doing something.");
                    doneSignal.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // main thread do sth
        Thread.sleep(2000);
        System.out.println("Main thread is doing something.");
        startSignal.countDown();

        // main thread do sth else
        System.out.println("Main thread is waiting for aid threads finishing.");
        doneSignal.await();

        System.out.println("Main thread is doing something after all threads have finished.");

    }

}
