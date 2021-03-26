package mjava.util.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * ConcurrentLinkedQueue只实现了Queue接口，并没有实现BlockingQueue接口，所以它不是阻塞队列，无法实现如果队列为空则等待元素到来的操作
 * 不能用于线程池中，但是它是线程安全的，可用于多线程环境中。
 * 内部一个无界的单链表实现的队列
 * 因为它不是阻塞队列，所以只有两个入队的方法，add(e)和offer(e)。
 * <p>
 * 入队 出队都利用CAS操作
 * 但是在更新head与tail时延迟更新
 * 如果让tail永远作为队列的队尾节点，实现的代码量会更少，而且逻辑更易懂。但是，这样做有一个缺点，如果大量的入队操作，每次都要执行CAS进行tail的更新，
 * 汇总起来对性能也会是大大的损耗。如果能减少CAS更新的操作，无疑可以大大提升入队的操作效率，所以doug lea大师每间隔1次（tail和队尾节点的距离为1）
 * 进行才利用CAS更新tail。对head的更新也是同样的道理，虽然，这样设计会多出在循环中定位队尾节点，但总体来说读的操作效率要远远高于写的性能，
 * 因此，多出来的在循环中定位尾节点的操作的性能损耗相对而言是很小的。
 */
public class ConcurrentLinkedQueueTest {
    public static void main(String[] args) {

        ConcurrentLinkedQueue<String> strings = new ConcurrentLinkedQueue<>();
        strings.add("156");
        strings.add("156");
    }
}
