package mjava.util.concurrent.locks;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock与synchronized有异曲同工之妙 但它对逻辑的灵活性更高  可以在一个方法中上锁，通过传参在另外的类的方法中释放锁
 * 但是切记 要释放锁，否则该线程即使结束后也不会自动的释放锁
 * 可以使用构造函数实现公平锁,默认是非公平锁
 *
 * 主要内部类
 * ReentrantLock中主要定义了三个内部类：Sync、NonfairSync、FairSync。
 * abstract static class Sync extends AbstractQueuedSynchronizer {}
 *
 * static final class NonfairSync extends Sync {}
 *
 * static final class FairSync extends Sync {}
 * （1）抽象类Sync实现了AQS的部分方法；
 * （2）NonfairSync实现了Sync，主要用于非公平锁的获取；
 * （3）FairSync实现了Sync，主要用于公平锁的获取
 *
 * <p>
 * 获取锁
 * void lock();
 * <p>
 * 获取锁（可中断）
 * void lockInterruptibly() throws InterruptedException;
 * 支持线程中断，它与lock()方法的主要区别在于lockInterruptibly()获取锁的时候如果线程中断了，
 * 会抛出一个异常，而lock()不会管线程是否中断都会一直尝试获取锁，获取锁之后把自己标记为已中断，继续执行自己的逻辑，后面也会正常释放锁。
 * 题外话：
 * 线程中断，只是在线程上打一个中断标志，并不会对运行中的线程有什么影响，具体需要根据这个中断标志干些什么，用户自己去决定。
 * 比如，如果用户在调用lock()获取锁后，发现线程中断了，就直接返回了，而导致没有释放锁，这也是允许的，但是会导致这个锁一直得不到释放，就出现了死锁。
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
 * lock的 过程
 *
 * // ReentrantLock.lock()
 * public void lock() {
 *     // 调用的sync属性的lock()方法
 *     // 这里的sync是公平锁，所以是FairSync的实例
 *     sync.lock();
 * }
 * // ReentrantLock.FairSync.lock()
 * final void lock() {
 *     // 调用AQS的acquire()方法获取锁
 *     // 注意，这里传的值为1
 *     acquire(1);
 * }
 * // AbstractQueuedSynchronizer.acquire()
 * public final void acquire(int arg) {
 *     // 尝试获取锁
 *     // 如果失败了，就排队
 *     if (!tryAcquire(arg) &&
 *         // 注意addWaiter()这里传入的节点模式为独占模式
 *         acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
 *         selfInterrupt();
 * }
 * // ReentrantLock.FairSync.tryAcquire()
 * protected final boolean tryAcquire(int acquires) {
 *     // 当前线程
 *     final Thread current = Thread.currentThread();
 *     // 查看当前状态变量的值
 *     int c = getState();
 *     // 如果状态变量的值为0，说明暂时还没有人占有锁
 *     if (c == 0) {
 *         // 如果没有其它线程在排队，那么当前线程尝试更新state的值为1
 *         // 如果成功了，则说明当前线程获取了锁
 *         if (!hasQueuedPredecessors() &&
 *             compareAndSetState(0, acquires)) {
 *             // 当前线程获取了锁，把自己设置到exclusiveOwnerThread变量中
 *             // exclusiveOwnerThread是AQS的父类AbstractOwnableSynchronizer中提供的变量
 *             setExclusiveOwnerThread(current);
 *             // 返回true说明成功获取了锁
 *             return true;
 *         }
 *     }
 *     // 如果当前线程本身就占有着锁，现在又尝试获取锁
 *     // 那么，直接让它获取锁并返回true
 *     else if (current == getExclusiveOwnerThread()) {
 *         // 状态变量state的值加1
 *         int nextc = c + acquires;
 *         // 如果溢出了，则报错
 *         if (nextc < 0)
 *             throw new Error("Maximum lock count exceeded");
 *         // 设置到state中
 *         // 这里不需要CAS更新state
 *         // 因为当前线程占有着锁，其它线程只会CAS把state从0更新成1，是不会成功的
 *         // 所以不存在竞争，自然不需要使用CAS来更新
 *         setState(nextc);
 *         // 当线程获取锁成功
 *         return true;
 *     }
 *     // 当前线程尝试获取锁失败
 *     return false;
 * }
 * // AbstractQueuedSynchronizer.addWaiter()
 * // 调用这个方法，说明上面尝试获取锁失败了
 * private Node addWaiter(Node mode) {
 *     // 新建一个节点
 *     Node node = new Node(Thread.currentThread(), mode);
 *     // 这里先尝试把新节点加到尾节点后面
 *     // 如果成功了就返回新节点
 *     // 如果没成功再调用enq()方法不断尝试
 *     Node pred = tail;
 *     // 如果尾节点不为空
 *     if (pred != null) {
 *         // 设置新节点的前置节点为现在的尾节点
 *         node.prev = pred;
 *         // CAS更新尾节点为新节点
 *         if (compareAndSetTail(pred, node)) {
 *             // 如果成功了，把旧尾节点的下一个节点指向新节点
 *             pred.next = node;
 *             // 并返回新节点
 *             return node;
 *         }
 *     }
 *     // 如果上面尝试入队新节点没成功，调用enq()处理
 *     enq(node);
 *     return node;
 * }
 * // AbstractQueuedSynchronizer.enq()
 * private Node enq(final Node node) {
 *     // 自旋，不断尝试
 *     for (;;) {
 *         Node t = tail;
 *         // 如果尾节点为空，说明还未初始化
 *         if (t == null) { // Must initialize
 *             // 初始化头节点和尾节点
 *             if (compareAndSetHead(new Node()))
 *                 tail = head;
 *         } else {
 *             // 如果尾节点不为空
 *             // 设置新节点的前一个节点为现在的尾节点
 *             node.prev = t;
 *             // CAS更新尾节点为新节点
 *             if (compareAndSetTail(t, node)) {
 *                 // 成功了，则设置旧尾节点的下一个节点为新节点
 *                 t.next = node;
 *                 // 并返回旧尾节点
 *                 return t;
 *             }
 *         }
 *     }
 * }
 * // AbstractQueuedSynchronizer.acquireQueued()
 * // 调用上面的addWaiter()方法使得新节点已经成功入队了
 * // 这个方法是尝试让当前节点来获取锁的
 * final boolean acquireQueued(final Node node, int arg) {
 *     // 失败标记
 *     boolean failed = true;
 *     try {
 *         // 中断标记
 *         boolean interrupted = false;
 *         // 自旋
 *         for (;;) {
 *             // 当前节点的前一个节点
 *             final Node p = node.predecessor();
 *             // 如果当前节点的前一个节点为head节点，则说明轮到自己获取锁了
 *             // 调用ReentrantLock.FairSync.tryAcquire()方法再次尝试获取锁
 *             if (p == head && tryAcquire(arg)) {
 *                 // 尝试获取锁成功
 *                 // 这里同时只会有一个线程在执行，所以不需要用CAS更新
 *                 // 把当前节点设置为新的头节点
 *                 setHead(node);
 *                 // 并把上一个节点从链表中删除
 *                 p.next = null; // help GC
 *                 // 未失败
 *                 failed = false;
 *                 return interrupted;
 *             }
 *             // 是否需要阻塞
 *             if (shouldParkAfterFailedAcquire(p, node) &&
 *                 // 真正阻塞的方法
 *                 parkAndCheckInterrupt())
 *                 // 如果中断了
 *                 interrupted = true;
 *         }
 *     } finally {
 *         // 如果失败了
 *         if (failed)
 *             // 取消获取锁
 *             cancelAcquire(node);
 *     }
 * }
 * // AbstractQueuedSynchronizer.shouldParkAfterFailedAcquire()
 * // 这个方法是在上面的for()循环里面调用的
 * // 第一次调用会把前一个节点的等待状态设置为SIGNAL，并返回false
 * // 第二次调用才会返回true
 * private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
 *     // 上一个节点的等待状态
 *     // 注意Node的waitStatus字段我们在上面创建Node的时候并没有指定
 *     // 也就是说使用的是默认值0
 *     // 这里把各种等待状态再贴出来
 *     //static final int CANCELLED =  1;
 *     //static final int SIGNAL    = -1;
 *     //static final int CONDITION = -2;
 *     //static final int PROPAGATE = -3;
 *     int ws = pred.waitStatus;
 *     // 如果等待状态为SIGNAL(等待唤醒)，直接返回true
 *     if (ws == Node.SIGNAL)
 *         return true;
 *     // 如果前一个节点的状态大于0，也就是已取消状态
 *     if (ws > 0) {
 *         // 把前面所有取消状态的节点都从链表中删除
 *         do {
 *             node.prev = pred = pred.prev;
 *         } while (pred.waitStatus > 0);
 *         pred.next = node;
 *     } else {
 *         // 如果前一个节点的状态小于等于0，则把其状态设置为等待唤醒
 *         // 这里可以简单地理解为把初始状态0设置为SIGNAL
 *         // CONDITION是条件锁的时候使用的
 *         // PROPAGATE是共享锁使用的
 *         compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
 *     }
 *     return false;
 * }
 * // AbstractQueuedSynchronizer.parkAndCheckInterrupt()
 * private final boolean parkAndCheckInterrupt() {
 *     // 阻塞当前线程
 *     // 底层调用的是Unsafe的park()方法
 *     LockSupport.park(this);
 *     // 返回是否已中断
 *     return Thread.interrupted();
 * }
 *
 * ReentrantLock#lock()
 * ->ReentrantLock.FairSync#lock() // 公平模式获取锁
 *   ->AbstractQueuedSynchronizer#acquire() // AQS的获取锁方法
 *     ->ReentrantLock.FairSync#tryAcquire() // 尝试获取锁
 *     ->AbstractQueuedSynchronizer#addWaiter()  // 添加到队列
 *       ->AbstractQueuedSynchronizer#enq()  // 入队
 *     ->AbstractQueuedSynchronizer#acquireQueued() // 里面有个for()循环，唤醒后再次尝试获取锁
 *       ->AbstractQueuedSynchronizer#shouldParkAfterFailedAcquire() // 检查是否要阻塞
 *       ->AbstractQueuedSynchronizer#parkAndCheckInterrupt()  // 真正阻塞的地方
 *
 * （1）尝试CAS改变状态量来获取锁，如果获取到了就直接返回；
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
 *
 *
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
 * （2）完全释放当前线程占有的锁,唤醒AQS队列头的线程；
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
        Condition condition = lock.newCondition();
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
