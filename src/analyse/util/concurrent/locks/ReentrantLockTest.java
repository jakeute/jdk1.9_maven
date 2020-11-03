package analyse.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock与synchronized有异曲同工之妙 但它对逻辑的灵活性更高  可以在一个方法中上锁，通过传参在另外的类的方法中释放锁
 * 但是切记 要释放锁，否则该线程即使结束后也不会自动的释放锁
 * 可以使用构造函数实现公平锁
 * <p>
 * 获取锁
 * void lock();
 * <p>
 * 获取锁（可中断）
 * void lockInterruptibly() throws InterruptedException;
 * <p>
 * 尝试获取锁，如果没获取到锁，就返回false
 * boolean tryLock();
 * <p>
 * 尝试获取锁，如果没获取到锁，就等待一段时间，这段时间内还没获取到锁就返回false
 * boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
 * <p>
 * 释放锁
 * void unlock();  切记：一般放在try...finally...的finally中确保被释放
 * <p>
 * 查看当前锁是否被当前线程占有
 * isHeldByCurrentThread
 * <p>
 * <p>
 * 由于重入锁无法使用wait notify 所以引入了
 * Condition newCondition()
 * await signal
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * lock的 过程
 * （1）尝试获取锁，如果获取到了就直接返回了；
 * （2）尝试获取锁失败，再调用addWaiter()构建新节点并把新节点入队；
 * （3）然后调用acquireQueued()再次尝试获取锁，如果成功了，直接返回；
 * （4）如果再次失败，再调用shouldParkAfterFailedAcquire()将节点的等待状态置为等待唤醒（SIGNAL）；
 * （5）调用parkAndCheckInterrupt()阻塞当前线程；
 * （6）如果被唤醒了，会继续在acquireQueued()的for()循环再次尝试获取锁，如果成功了就返回；
 * （7）如果不成功，再次阻塞，重复（3）（4）（5）直到成功获取到锁。
 * <p>
 * 相对于公平锁，非公平锁加锁的过程主要有两点不同：
 * （1）一开始就尝试CAS更新状态变量state的值，如果成功了就获取到锁了；
 * （2）在tryAcquire()的时候没有检查是否前面有排队的线程，直接上去获取锁才不管别人有没有排队呢；
 * <p>
 * 至于其他的公平锁与非公平锁没有区别 在加入链表后，更是一样，也就是说非公平锁只是狭义上的，
 * <p>
 * 释放锁的过程大致为：
 * <p>
 * （1）将state的值减1；
 * （2）如果state减到了0，说明已经完全释放锁了，唤醒下一个等待着的节点；
 * 下一个节点对应的线程会在（5）处苏醒，再次运行
 * <p>
 * <p>
 * Condition
 * await()
 * （1）新建一个节点加入到条件队列中去；
 * <p>
 * （2）完全释放当前线程占有的锁；
 * <p>
 * （3）阻塞当前线程，并等待条件的出现；
 * <p>
 * （4）条件已出现（此时节点已经移到AQS的队列中），尝试获取锁
 * <p>
 * (5)当阻塞时被外部中断，则无需等待条件（将节点移到AQS的队列中），尝试获取锁，成功后会抛出异常
 * <p>
 * signal
 * （1）从条件队列的头节点开始寻找一个非取消状态的节点；
 * <p>
 * （2）把它从条件队列移到AQS队列；
 * <p>
 * （3）唤醒等待条件的线程；
 */
public class ReentrantLockTest {
    public static void main(String[] args) throws InterruptedException {
//        Runnable runnable = new Runnable() {
//            ReentrantLock lock = new ReentrantLock(true);
//            Condition condition = lock.newCondition();
//            volatile int v;
//
//            @Override
//            public void run() {
//
//                String name = Thread.currentThread().getName();
//
//                try {
//                    lock.lock();
//                    for (int i = 0; i < 10; i++) {
//                        v++;
//                        System.out.println(name + "  " + v);
//                        try {
//                            condition.await(1, TimeUnit.SECONDS);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } finally {
//                    lock.unlock();
//                }
//
//
//            }
//        };

//        Runnable runnable = new Runnable() {
//            ReentrantLock lock = new ReentrantLock();
//            Condition condition = lock.newCondition();
//            @Override
//            public void run() {
//                String name = Thread.currentThread().getName();
//                lock.lock();
//                System.out.println(name+"获得锁");
//                try {
//                    condition.await();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                System.out.println(name+"释放锁");
//                lock.unlock();
//            }
//        };
//
//        Thread thread1 = new Thread(runnable);
//        Thread thread2 = new Thread(runnable);
//        Thread thread3 = new Thread(runnable);
//        Thread thread4 = new Thread(runnable);
//
//        thread1.setName("one");
//        thread2.setName("two");
//        thread3.setName("three");
//        thread4.setName("fore");
//
//        thread1.start();
//        Thread.sleep(120);
//        thread2.start();
//        //Thread.sleep(120);
//        thread3.start();
//        //Thread.sleep(120);
//        thread4.start();


        ReentrantLock lock = new ReentrantLock();
        Thread hhhh = new Thread() {
            @Override
            public void run() {

                try {
                    lock.lockInterruptibly();
                    System.out.println(lock.isHeldByCurrentThread());
                    System.out.println("hhhh");
                    lock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        lock.lock();

        hhhh.start();

        Thread.sleep(5000);
        System.out.println("......");
        hhhh.interrupt();
        System.out.println("......");
        Thread.sleep(5000);

        lock.unlock();
    }

    @Test
    public void stop() {

    }
}
