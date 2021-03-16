package mjava.util.concurrent;

/**
 * （1）LinkedBlockingQueue采用单链表的形式实现；
 * <p>
 * （2）LinkedBlockingQueue采用两把锁的锁分离技术实现入队出队互不阻塞，可同时读写；
 * <p>
 * （3）LinkedBlockingQueue是有界队列，不传入容量时默认为最大int值；
 * <p>
 * put
 * （1）使用putLock加锁；
 * <p>
 * （2）如果队列满了就阻塞在notFull条件上；
 * <p>
 * （3）否则就入队；
 * <p>
 * （4）如果入队后元素数量小于容量，唤醒其它阻塞在notFull条件上的线程；
 * <p>
 * （5）释放锁；
 * <p>
 * （6）如果放元素之前队列长度为0，就唤醒notEmpty条件；
 * <p>
 * <p>
 * take
 * （1）使用takeLock加锁；
 * <p>
 * （2）如果队列空了就阻塞在notEmpty条件上；
 * <p>
 * （3）否则就出队；
 * <p>
 * （4）如果出队前元素数量大于1，唤醒其它阻塞在notEmpty条件上的线程；
 * <p>
 * （5）释放锁；
 * <p>
 * （6）如果取元素之前队列长度等于容量，就唤醒notFull条件；
 */
public class LinkedBlockingQueueTest {
}
