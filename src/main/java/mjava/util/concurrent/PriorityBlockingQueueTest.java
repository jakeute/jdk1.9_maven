package mjava.util.concurrent;

/**
 * PriorityBlockingQueue是java并发包下的优先级阻塞队列，它是线程安全的
 * 元素可以默认自然排序或者通过提供的Comparator（比较器）
 * offer
 * 入队的整个操作跟PriorityQueue几乎一致：
 * <p>
 * （1）加锁；
 * <p>
 * （2）判断是否需要扩容；
 * <p>
 * （3）添加元素并做自下而上的堆化；
 * <p>
 * （4）元素个数加1并唤醒notEmpty条件，唤醒取元素的线程；
 * <p>
 * （5）解锁；
 * <p>
 * <p>
 * 扩容
 * <p>
 * （1）解锁，解除offer()方法中加的锁；
 * <p>
 * （2）使用allocationSpinLock变量的CAS操作进行上锁；
 * <p>
 * （3）旧容量小于64则翻倍，旧容量大于64则增加一半；
 * <p>
 * （4）创建新数组；
 * <p>
 * （5）修改allocationSpinLock为0，相当于解锁；
 * <p>
 * （6）其它线程在扩容的过程中要让出CPU；
 * <p>
 * （7）再次lock加锁；
 * <p>
 * （8）新数组创建成功，把旧数组元素拷贝过来，并返回到offer()方法中继续添加元素操作；
 * <p>
 * 出队
 * <p>
 * （1）加锁；
 * <p>
 * （2）判断是否出队成功，未成功就阻塞在notEmpty条件上；
 * <p>
 * （3）出队时弹出堆顶元素，并把堆尾元素拿到堆顶；
 * <p>
 * （4）再做自上而下的堆化；
 * <p>
 * （5）解锁；
 */
public class PriorityBlockingQueueTest {
}
