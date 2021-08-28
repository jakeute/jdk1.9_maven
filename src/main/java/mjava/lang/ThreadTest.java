package mjava.lang;

/**
 * 首先 Java多线程建立在Thread类的基础上 通过Thread.start() 我们可以开启另一个线程
 * <p>
 * 守护线程在非守护线程结束后自动结束  线程也具有优先级
 * 关于线程的基本操作：Object.wait(释放锁 等待唤醒，但是唤醒后想要继续执行需要重新获得锁)/notify(唤醒等待线程，将线程加入到AQS队列)
 * Thread.sleep(不释放锁休眠)/join(等待线程结束)/interrupt(中断线程)/yield(谦让CPU)
 * <p>
 * NEW           新建状态，线程还未开始
 * RUNNABLE      可运行状态，正在运行或者在等待系统资源，比如CPU时间片
 * BLOCKED       阻塞状态，在等待一个监视器锁（也就是我们常说的synchronized）
 * WAITING      等待状态，在调用了以下方法后进入此状态
 * 1. Object.wait()无超时的方法后且未被notify()前，如果被notify()了会进入BLOCKED状态
 * 2. Thread.join()无超时的方法后
 * 3. LockSupport.park()无超时的方法后
 * TIMED_WAITING      超时等待状态，在调用了以下方法后会进入超时等待状态
 * 1. Thread.sleep()方法后
 * 2. Object.wait(timeout)方法后且未到超时时间前，如果达到超时了或被notify()了会进入BLOCKED状态
 * 3. Thread.join(timeout)方法后
 * 4. LockSupport.parkNanos(nanos)方法后
 * 5. LockSupport.parkUntil(deadline)方法后
 * TERMINATED      终止状态，线程已经执行完毕
 * <p>
 * （1）为了方便讲解，我们把锁分成两大类，一类是synchronized锁，一类是基于AQS的锁（我们拿重入锁举例），也就是内部使用了LockSupport.park()/parkNanos()/parkUntil()几个方法的锁；
 * （2）不管是synchronized锁还是基于AQS的锁，内部都是分成两个队列，一个是同步队列（AQS的队列），一个是等待队列（Condition的队列）；
 * （3）对于内部调用了object.wait()/wait(timeout)或者condition.await()/await(timeout)方法，线程都是先进入等待队列，被notify()/signal()或者超时后，才会进入同步队列；
 * （4）明确声明，BLOCKED状态只有线程处于synchronized的同步队列的时候才会有这个状态，其它任何情况都跟这个状态无关；
 * （5）对于synchronized，线程执行synchronized的时候，如果立即获得了锁（没有进入同步队列），线程处于RUNNABLE状态；
 * （6）对于synchronized，线程执行synchronized的时候，如果无法获得锁（直接进入同步队列），线程处于BLOCKED状态；
 * （5）对于synchronized内部，调用了object.wait()之后线程处于WAITING状态（进入等待队列）；
 * （6）对于synchronized内部，调用了object.wait(timeout)之后线程处于TIMED_WAITING状态（进入等待队列）；
 * （7）对于synchronized内部，调用了object.wait()之后且被notify()了，如果线程立即获得了锁（也就是没有进入同步队列），线程处于RUNNABLE状态；
 * （8）对于synchronized内部，调用了object.wait(timeout)之后且被notify()了，如果线程立即获得了锁（也就是没有进入同步队列），线程处于RUNNABLE状态；
 * （9）对于synchronized内部，调用了object.wait(timeout)之后且超时了，这时如果线程正好立即获得了锁（也就是没有进入同步队列），线程处于RUNNABLE状态；
 * （10）对于synchronized内部，调用了object.wait()之后且被notify()了，如果线程无法获得锁（也就是进入了同步队列），线程处于BLOCKED状态；
 * （11）对于synchronized内部，调用了object.wait(timeout)之后且被notify()了或者超时了，如果线程无法获得锁（也就是进入了同步队列），线程处于BLOCKED状态；
 * （12）对于重入锁，线程执行lock.lock()的时候，如果立即获得了锁（没有进入同步队列），线程处于RUNNABLE状态；
 * （13）对于重入锁，线程执行lock.lock()的时候，如果无法获得锁（直接进入同步队列），线程处于WAITING状态；
 * （14）对于重入锁内部，调用了condition.await()之后线程处于WAITING状态（进入等待队列）；
 * （15）对于重入锁内部，调用了condition.await(timeout)之后线程处于TIMED_WAITING状态（进入等待队列）；
 * （16）对于重入锁内部，调用了condition.await()之后且被signal()了，如果线程立即获得了锁（也就是没有进入同步队列），线程处于RUNNABLE状态；
 * （17）对于重入锁内部，调用了condition.await(timeout)之后且被signal()了，如果线程立即获得了锁（也就是没有进入同步队列），线程处于RUNNABLE状态；
 * （18）对于重入锁内部，调用了condition.await(timeout)之后且超时了，这时如果线程正好立即获得了锁（也就是没有进入同步队列），线程处于RUNNABLE状态；
 * （19）对于重入锁内部，调用了condition.await()之后且被signal()了，如果线程无法获得锁（也就是进入了同步队列），线程处于WAITING状态；
 * （20）对于重入锁内部，调用了condition.await(timeout)之后且被signal()了或者超时了，如果线程无法获得锁（也就是进入了同步队列），线程处于WAITING状态；
 * （21）对于重入锁，如果内部调用了condition.await()之后且被signal()之后依然无法获取锁的，其实经历了两次WAITING状态的切换，一次是在等待队列，一次是在同步队列；
 * （22）对于重入锁，如果内部调用了condition.await(timeout)之后且被signal()或超时了的，状态会有一个从TIMED_WAITING切换到WAITING的过程，也就是从等待队列进入到同步队列
 *
 * <p>
 * Thread类实现了Runnable接口，在Thread类中，有一些比较关键的属性，比如name是表示Thread的名字，可以通过Thread类的构造器中的参数来指定线程名字，
 * priority表示线程的优先级（最大值为10，最小值为1，默认值为5），daemon表示线程是否是守护线程(守护线程在main线程结束后就消亡)，target表示要执行的任务。
 * 以下是关系到线程属性的几个方法：要在start之前设置
 * Thread.run默认实现是调用内部属性Runnable target的run
 * 总体而言 创建线程一共有两种方式，一种是继承Thread类并重写其run()方法，一种是传入实现Runnable接口的run()方法
 * <p>
 * 1）getId
 * <p>
 * 　　用来得到线程ID
 * <p>
 * 2）getName和setName
 * <p>
 * 　　用来得到或者设置线程名称。
 * <p>
 * 3）getPriority和setPriority
 * <p>
 * 　　用来获取和设置线程优先级。
 * <p>
 * 4）setDaemon和isDaemon
 * <p>
 * 　　用来设置线程是否成为守护线程和判断线程是否是守护线程。
 * <p>
 * 1）start方法
 * <p>
 * 　　start()用来启动一个线程，当调用start方法后，系统才会开启一个新的线程来执行用户定义的子任务，在这个过程中，会为相应的线程分配需要的资源。
 * <p>
 * 2）run方法
 * <p>
 * 　　run()方法是不需要用户来调用的，当通过start方法启动一个线程之后，当线程获得了CPU执行时间，便进入run方法体去执行具体的任务。
 * 注意，继承Thread类必须重写run方法，在run方法中定义具体要执行的任务。
 * <p>
 * 3）static sleep方法
 * sleep相当于让线程睡眠，交出CPU，让CPU去执行其他的任务，自然也就不会释放锁
 * <p>
 * 　　但是有一点要非常注意，sleep方法不会释放锁，也就是说如果当前线程持有对某个对象的锁，则即使调用sleep方法，
 * 其他线程也无法访问这个对象。看下面这个例子就清楚了：
 * <p>
 * 5）join方法
 * <p>
 * 　　join方法有三个重载版本：
 * <p>
 * join()
 * join(long millis)     //参数为毫秒
 * join(long millis,int nanoseconds)    //第一参数为毫秒，第二个参数为纳秒
 * 假如在main线程中，调用thread.join方法，则main方法会等待thread线程执行完毕或者等待一定的时间。
 * 如果调用的是无参join方法，则等待thread执行完毕，如果调用的是指定了时间参数的join方法，则等待一定的事件。
 * <p>
 * 6）interrupt方法
 * <p>
 * 　　interrupt，顾名思义，即中断的意思。单独调用interrupt方法可以使得处于阻塞状态的线程抛出一个异常，也就说，它可以用来中断一个正处于阻塞状态的线程；
 * 但它并不会对运行中的线程有什么影响，只是在线程上打一个中断标志，具体需要根据这个中断标志干些什么，用户自己去决定
 * 另外，isInterrupted()方法判断是否被中断 interrupted 判断并且清除中断标志
 * 线程中断，
 * 7）yield  让出CPU
 */
public class ThreadTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //获取当前线程
                Thread thread = Thread.currentThread();

                System.out.println(thread.getName() + " : " + thread.getId() + " start");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(thread.getName() + " : " + thread.getId() + " end");

            }
        });

        thread.start();


        thread = Thread.currentThread();
        System.out.println(thread.getName() + " : " + thread.getId() + " start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(thread.getName() + " : " + thread.getId() + " end");

    }
}
