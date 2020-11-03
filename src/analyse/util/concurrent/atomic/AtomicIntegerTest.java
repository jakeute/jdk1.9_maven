package analyse.util.concurrent.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * VALUE - 是value在对象中的偏移量
 * value - 存储值
 * <p>
 * <p>
 * 对于原子类的操作而言，其底层调用为Unsafe的CompareAndSwapXXX函数
 * 这个函数可以看作是原子操作，比较更新 无法被中断 也就无需担心比较完还未更新时被修改
 * <p>
 * 所以对于原子类无需担心多线程操作造成的数据紊乱问题
 */
public class AtomicIntegerTest {

    static volatile int count;
    static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        //compareAndSet   当希望值与真实值不相等时 返回false

        //在使用CAS操作时候应该用一个值Current去保存原值   比较更新的时候Current作为当前值
        //不能再从新获取新值去当作当前值
        int current = atomicInteger.get();
        atomicInteger.compareAndSet(current, current + 1);

        //getAndIncrement  返回原值，value++
        int andIncrement = atomicInteger.getAndIncrement();
        //getAndSet
        atomicInteger.getAndSet(5);
        //getAndDecrement
        //getAndAdd
        //incrementAndGet
        //addAndGet

    }

    @Test
    public void test1() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++)
                    count++;
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++)
                    count++;
            }
        };

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

        System.out.println(count);

//        volatile无法解决这个问题，因为volatile仅有两个作用：
//
//（1）保证可见性，即一个线程对变量的修改另一个线程立即可见；
//
//（2）禁止指令重排序；
//
//        这里有个很重要的问题，count++实际上是两步操作，第一步是获取count的值，第二步是对它的值加1。
//
//        使用volatile是无法保证这两步不被其它线程调度打断的，所以无法保证原子性。
    }

    @Test
    public void test2() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++)
                    atomicInteger.incrementAndGet();
            }
        };
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++)
                    atomicInteger.incrementAndGet();
            }
        };

        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

        System.out.println(atomicInteger.get());
    }
}
