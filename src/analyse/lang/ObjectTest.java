package analyse.lang;

import org.junit.Test;

/**
 * 理论上Object类是所有类的父类，即直接或间接的继承java.lang.Object类。由于所有的类都继承在Object类，因此省略了extends Object关键字。
 * 该类中主要有以下方法: toString(),getClass(),equals(),clone(),finalize(), 其中toString(),getClass(),equals()是其中最重要的方法。
 * Object类中的getClass(),notify(),notifyAll(),wait()等方法被定义为final类型，因此不能重写。
 */
public class ObjectTest {
    @Test
    public void analyse() {
        //Object()为空方法
        Object t = new Object();

        //getClass() 是本地方法 获取运行时类的Class对象（多态）
        System.out.println(new String().getClass());
        System.out.println(new Object().getClass());

        //hashCode() 是本地方法 获取当前的对象的哈希值（用于HashSet HashMap）
        //默认情况下，该方法会根据对象的地址来计算
        //可以交给IDEA完成
        System.out.println(t.hashCode());

        //toString() 返回对象的描述 默认情况下，该方法会输出对象的类型与哈希值
        //可以交给IDEA完成
        System.out.println(t.toString());

        //equals(Object obj) 比较obj是否与当前对象相等（属性是否相等） 默认情况下，该方法比较两个对象的堆地址
        //可以交给IDEA完成
        System.out.println(t.equals(new Object()));


        //wait(long timeout) 此方法只应由作为此对象监视器的所有者的线程来调用,也就是只能在sunchonrized中使用
        //以下四种条件会被唤醒
        //1.其他某个线程调用此对象的 notify 方法，并且线程 T 碰巧被任选为被唤醒的线程。
        //2、其他某个线程调用此对象的 notifyAll 方法。
        //3、其他某个线程中断线程 T。
        //4.大约已经到达指定的实际时间。但是，如果 timeout 为零，则不考虑实际时间，在获得通知前该线程将一直等待。
        //当线程B访问某个共享资源时，想获取资源的锁对象，发现这个锁已经被线程A拿到了，这个时候，线程B只能被挂起，等待线程A释放锁。
        //但是拿到锁的线程A在执行的过程中，因为某些条件还不满足，暂时不想继续执行下去，想先等待一下(注意：是已经拿到锁的线程A自己想主动等待的)，
        //希望等到某个条件满足后，继续执行任务。在同步代码块里，线程A必须先释放锁(锁对象需要与synchronized(obj)对应)，线程B才有资格获取锁，进入同步代码块，执行代码。
        //当A被唤醒时需要重新获取锁对象才能继续执行
        //如果另一个线程中断了当前wait线程则会抛出InterruptedException异常。当这种异常被抛出当前线程的中断状态被清除。
        synchronized (t) {
            try {
                t.wait(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("await");
        }

        //wait() 此方法只应由作为此对象监视器的所有者的线程来调用  wait(0)
        synchronized (t) {
            try {
                t.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("await");

        }

        //notify() 此方法只应由作为此对象监视器的所有者的线程来调用
        //唤醒一个等待资源对象锁的线程。
        //而且只有退出synchronized块 释放锁后，wait线程才能继续
        // 注意唤醒的是notify()之前wait的线程,对于notify()之后的wait线程是没有效果的。
        synchronized (t) {
            t.notify();
        }

        //notifyAll() 此方法只应由作为此对象监视器的所有者的线程来调用
        //唤醒所有等待资源对象锁的线程。
        // 注意唤醒的是notifyAll()之前wait的线程,对于notifyAll()之后的wait线程是没有效果的。
        synchronized (t) {
            t.notifyAll();
        }
    }
}
