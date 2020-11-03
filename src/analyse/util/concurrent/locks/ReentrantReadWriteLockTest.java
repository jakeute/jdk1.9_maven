package analyse.util.concurrent.locks;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁是一种特殊的锁，它把对共享资源的访问分为读访问和写访问，多个线程可以同时对共享资源进行读访问，
 * 但是同一时间只能有一个线程对共享资源进行写访问，使用读写锁可以极大地提高并发量。
 * <p>
 * 读锁加锁
 * （1）先尝试获取读锁；
 * （2）如果成功了直接结束，共享锁总共被获取的次数加1(只要没有其他线程获得写锁就行）；
 * （3）如果失败了，进入doAcquireShared()方法；
 * （4）doAcquireShared()方法中首先会生成一个新节点并进入AQS队列中；
 * （5）如果头节点正好是当前节点的上一个节点，再次尝试获取锁；
 * （6）如果成功了，则设置头节点为新节点，并传播；
 * （7）传播即唤醒下一个读节点（如果下一个节点是读节点的话）；
 * （8）如果头节点不是当前节点的上一个节点或者（5）失败，则阻塞当前线程等待被唤醒；
 * （9）唤醒之后继续走（5）的逻辑；
 * 在整个逻辑中是在哪里连续唤醒读节点的呢？
 * 答案是在doAcquireShared()方法中，在这里一个节点A被唤醒（8）并且获取了读锁后，会唤醒下一个读节点B，这时候B也会重复A的路程，然后B继续唤醒C，依次往复，
 * 也就是说这里的节点是一个唤醒一个这样的形式，而不是一个节点获取了读锁后一次性唤醒后面所有的读节点。
 * <p>
 * 解锁的大致流程如下：
 * （1）将当前线程重入的次数减1；
 * （2）将共享锁总共被获取的次数减1；
 * （3）如果共享锁获取的次数减为0了，说明共享锁完全释放了，那就唤醒下一个节点；
 * <p>
 * <p>
 * <p>
 * 写锁加锁
 * （1）尝试获取锁；
 * （2）如果有读者占有着读锁，尝试获取写锁失败；
 * （3）如果有其它线程占有着写锁，尝试获取写锁失败；
 * （4）如果是当前线程占有着写锁，尝试获取写锁成功，state值加1；
 * （5）如果没有线程占有着锁（state==0），当前线程尝试更新state的值，成功了表示尝试获取锁成功，否则失败；
 * （6）尝试获取锁失败以后，进入队列排队，等待被唤醒；
 * （7）后续逻辑跟ReentrantLock是一致；
 * <p>
 * 解锁
 * （1）先尝试释放锁，即状态变量state的值减1；
 * （2）如果减为0了，说明完全释放了锁；
 * （3）完全释放了锁才唤醒下一个等待的节点；
 * <p>
 * <p>
 * 同一个线程先读后写和先写后读是完全不一样的，为什么不一样呢？
 * 先读后写，一个线程占有读锁后，其它线程还是可以占有读锁的，这时候如果在其它线程占有读锁之前让自己占有了写锁，其它线程又不能占有读锁了，这段程序会非常难实现，逻辑也很奇怪，所以，设计成只要一个线程占有了读锁，其它线程包括它自己都不能再获取写锁。
 * 先写后读，一个线程占有写锁后，其它线程是不能占有任何锁的，这时候，即使自己占有一个读锁，对程序的逻辑也不会有任何影响，所以，一个线程占有写锁后是可以再占有读锁的，只是这个时候其它线程依然无法获取读锁。
 * 如果你仔细思考上面的逻辑，你会发现一个线程先占有读锁后占有写锁，会有一个很大的问题——锁无法被释放也无法被获取了。这个线程先占有了读锁，然后自己再占有写锁的时候会阻塞，然后它就自己把自己搞死了，进而把其它线程也搞死了，它无法释放锁，其它线程也无法获得锁了。
 */

public class ReentrantReadWriteLockTest {
    public static void main(String[] args) {
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        Runnable read = new Runnable() {

            @Override
            public void run() {
                try {
                    readLock.lock();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    readLock.unlock();
                }
            }
        };
        Runnable write = new Runnable() {

            @Override
            public void run() {
                try {
                    writeLock.lock();
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    writeLock.unlock();
                }
            }
        };


        Thread thread1 = new Thread(read);
        Thread thread2 = new Thread(read);
        Thread thread3 = new Thread(write);
    }
}
