package mjava.util.concurrent.locks;

/**
 * 一个非常实用的线程阻塞工具
 * LockSupport 内部为每一个线程维护着一个许可
 * 当park时 如果许可可用则消费许可，立马返回。 否则会让当前线程处于等待状态  可被中断
 * unpark(Thread) 则会让许可变得可用，唤醒处于等待状态的线程，进入运行态
 * <p>
 * 因此当unpark先于park发生时，许可可用 park不会阻塞
 * <p>
 * 当park等待时 如果线程被中断，并不会抛出异常，它只会默默的返回，但是我们
 * 可以通过Thread.interrupted 检测
 */
public class LockSupportTest {
    public static void main(String[] args) {

    }
}
