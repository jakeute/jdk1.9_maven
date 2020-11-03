package analyse.util.concurrent.locks;

import org.junit.Test;

/**
 * synchronized关键字是Java里面最基本的同步手段，它经过编译之后，会在同步块的前后分别生成 monitorenter 和 monitorexit 字节码指令，
 * 这两个字节码指令都需要一个引用类型的参数来指明要锁定和解锁的对象。
 * 而 synchronized 就是使用 monitorenter 和 monitorexit 这两个指令来实现的。
 * <p>
 * 根据JVM规范的要求，在执行monitorenter指令的时候，首先要去尝试获取对象的锁，如果这个对象没有被锁定，或者当前线程已经拥有了这个对象的锁，就把锁的计数器加1，
 * 相应地，在执行monitorexit的时候会把计数器减1，当计数器减小为0时，锁就释放了。所以一个线程可以多次Synchronized同一个对象
 * <p>
 * <p>
 * 多个synchronized只有锁的是同一个对象，它们之间的代码才是同步的，这一点在使用synchronized的时候一定要注意。
 * wait(释放锁等待)/notify(唤醒等待线程) 是只有synchronized 获取锁后才能进行的操作
 * <p>
 * synchronized ReentrantLock
 * （1）synchronized是Java原生关键字锁；它更改的是JVM堆中的对象头，没有花里胡哨的一些操作，其他线程没有被指向时就会阻塞
 * （2）ReentrantLock是Java语言层面提供的锁；它内部维持着一个队列，通过LockSupport来阻塞唤醒线程
 * （3）ReentrantLock的功能非常丰富，解决了很多synchronized的局限性；
 */
public class SynchronizedTest {
    /**
     * 默认对象为 this
     */
    public synchronized void show() {

    }

    /**
     * 默认对象为 SynchronizedProblem.class
     */
    static synchronized void look() {

    }

    public static void main(String[] args) throws InterruptedException {
//        Runnable runnable = new Runnable() {
//            volatile int v;
//
//            @Override
//            public void run() {
//
//                String name = Thread.currentThread().getName();
//
//                synchronized (this) {
//
//                    for (int i = 0; i < 10; i++) {
//                        //notify();
//                        v++;
//                        System.out.println(name + "  " + v);
//                        try {
//                            wait(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//            }
//        };
//
//        Thread thread1 = new Thread(runnable);
//        Thread thread2 = new Thread(runnable);
//
//        thread1.setName("one");
//        thread2.setName("two");
//
//        thread1.start();
//        thread2.start();


        Object lock = new Object();
        Thread one_end = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("one start");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println(".....");
                        //e.printStackTrace();
                    }
                    System.out.println("one end");
                }
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("two start");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                    System.out.println("two end");
                }
            }
        };

        one_end.start();
        Thread.sleep(100);
        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        one_end.interrupt();
    }

    @Test
    public void show2() throws InterruptedException {
        Object lock = new Object();
        Thread one_end = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("one start");
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("one end");
                }
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                synchronized (lock) {
                    System.out.println("two start");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.notify();
                    System.out.println("two end");
                }
            }
        };

        one_end.start();
        Thread.sleep(100);
        thread.start();
    }
}
