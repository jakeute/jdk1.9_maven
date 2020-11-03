package analyse.util.concurrent;

/**
 * ArrayBlockingQueue是java并发包下一个以循环数组实现的阻塞队列，它是线程安全的，至于是否需要扩容，请看下面的分析。
 * 一个重入锁，两个条件。不能同时进行读写（因为使用的是循环数组，涉及到头尾相撞的问题）
 * <p>
 * （1）ArrayBlockingQueue初始化时必须传入容量，也就是数组的大小；
 * <p>
 * （2）可以通过构造方法控制重入锁的类型是公平锁还是非公平锁；
 * <p>
 * <p>
 * （1）add(e)时如果队列满了则抛出异常；
 * <p>
 * （2）offer(e)时如果队列满了则返回false；
 * <p>
 * （3）put(e)时如果队列满了则使用notFull等待；
 * <p>
 * （4）offer(e, timeout, unit)时如果队列满了则等待一段时间后如果队列依然满就返回false；
 * <p>
 * （5）利用放指针循环使用数组来存储元素；
 * <p>
 * <p>
 * <p>
 * （1）remove()时如果队列为空则抛出异常；
 * <p>
 * （2）poll()时如果队列为空则返回null；
 * <p>
 * （3）take()时如果队列为空则阻塞等待在条件notEmpty上；
 * <p>
 * （4）poll(timeout, unit)时如果队列为空则阻塞等待一段时间后如果还为空就返回null；
 * <p>
 * （5）利用取指针循环从数组中取元素；
 */
public class ArrayBlockingQueueTest {
}
