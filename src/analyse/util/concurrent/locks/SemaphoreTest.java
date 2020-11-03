package analyse.util.concurrent.locks;

import java.util.concurrent.Semaphore;

/**
 * Semaphore，信号量，它保存了一系列的许可（permits），每次调用acquire()都将消耗一个许可，每次调用release()都将归还一个许可。
 * 通常用于限制同一时间对共享资源的访问次数上，也就是常说的限流。
 * <p>
 * tryAcquire的区别
 * 非公平模式下，直接调用父类的nonfairTryAcquireShared()尝试获取许可。
 * 公平模式下，先检测前面是否有排队的，如果有排队的则获取许可失败，进入队列排队，否则尝试原子更新state的值。
 * <p>
 * Semaphore与锁相似，只不过它的许可更多，而锁只有一个 不过他们在逻辑上是相似的
 * 获取失败后加入队列阻塞  释放后会唤醒队列头
 */
public class SemaphoreTest {
    static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) throws InterruptedException {
        //获取信号
        semaphore.acquire();
        //尝试获取
        semaphore.tryAcquire();
        //释放
        semaphore.release();
    }
}
