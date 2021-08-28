package mjava.util.concurrent.locks;

/**
 * AQS的全称是AbstractQueuedSynchronizer，它的定位是为Java中几乎所有的锁和同步器提供一个基础框架。
 * AQS使用LockSupport将线程处于等待状态模拟阻塞
 * <p>
 * AQS是基于FIFO的队列实现的，并且内部维护了一个状态变量state，通过原子更新这个状态变量state即可以实现加锁解锁操作。
 * <p>
 * 主要内部类
 * static final class Node {
 * // 标识一个节点是共享模式
 * static final Node SHARED = new Node();
 * // 标识一个节点是互斥模式
 * static final Node EXCLUSIVE = null;
 * <p>
 * // 标识线程已取消
 * static final int CANCELLED =  1;
 * // 标识后继节点需要唤醒
 * static final int SIGNAL    = -1;
 * // 标识线程等待在一个条件上
 * static final int CONDITION = -2;
 * // 标识后面的共享锁需要无条件的传播（共享锁需要连续唤醒读的线程）
 * static final int PROPAGATE = -3;
 * <p>
 * // 当前节点保存的线程对应的等待状态
 * volatile int waitStatus;
 * <p>
 * // 前一个节点
 * volatile Node prev;
 * <p>
 * // 后一个节点
 * volatile Node next;
 * <p>
 * // 当前节点保存的线程
 * volatile Thread thread;
 * <p>
 * // 下一个等待在条件上的节点（Condition锁时使用）
 * Node nextWaiter;
 * <p>
 * // 是否是共享模式
 * final boolean isShared() {
 * return nextWaiter == SHARED;
 * }
 * <p>
 * // 获取前一个节点
 * final Node predecessor() throws NullPointerException {
 * Node p = prev;
 * if (p == null)
 * throw new NullPointerException();
 * else
 * return p;
 * }
 * <p>
 * // 节点的构造方法
 * Node() {    // Used to establish initial head or SHARED marker
 * }
 * <p>
 * // 节点的构造方法
 * Node(Thread thread, Node mode) {     // Used by addWaiter
 * // 把共享模式还是互斥模式存储到nextWaiter这个字段里面了
 * this.nextWaiter = mode;
 * this.thread = thread;
 * }
 * <p>
 * // 节点的构造方法
 * Node(Thread thread, int waitStatus) { // Used by Condition
 * // 等待的状态，在Condition中使用
 * this.waitStatus = waitStatus;
 * this.thread = thread;
 * }
 * }
 * 典型的双链表结构，节点中保存着当前线程、前一个节点、后一个节点以及线程的状态等信息。
 * <p>
 * <p>
 * 主要属性
 * // 队列的头节点
 * private transient volatile Node head;
 * // 队列的尾节点
 * private transient volatile Node tail;
 * // 控制加锁解锁的状态变量
 * private volatile int state;
 * 定义了一个状态变量和一个队列，状态变量用来控制加锁解锁，队列用来放置等待的线程。
 * 注意，这几个变量都要使用volatile关键字来修饰，因为是在多线程环境下操作，要保证它们的值修改之后对其它线程立即可见。
 * 这几个变量的修改是直接使用的Unsafe这个类来操作的：
 * <p>
 * 子类需要实现的主要方法
 * 我们可以看到AQS的全称是AbstractQueuedSynchronizer，它本质上是一个抽象类，说明它本质上应该是需要子类来实现的，那么子类实现一个同步器需要实现哪些方法呢？
 * // 互斥模式下使用：尝试获取锁
 * protected boolean tryAcquire(int arg) {
 * throw new UnsupportedOperationException();
 * }
 * // 互斥模式下使用：尝试释放锁
 * protected boolean tryRelease(int arg) {
 * throw new UnsupportedOperationException();
 * }
 * // 共享模式下使用：尝试获取锁
 * protected int tryAcquireShared(int arg) {
 * throw new UnsupportedOperationException();
 * }
 * // 共享模式下使用：尝试释放锁
 * protected boolean tryReleaseShared(int arg) {
 * throw new UnsupportedOperationException();
 * }
 * // 如果当前线程独占着锁，返回true
 * protected boolean isHeldExclusively() {
 * throw new UnsupportedOperationException();
 * }
 */
public class AbstractQueuedSynchronizerTest {
    /**
     * 自己实现AQS
     * <p>
     * 一个变量
     * 这个变量只支持同时只有一个线程能把它修改为1，所以它修改完了一定要让其它线程可见，因此，这个变量需要使用volatile来修饰。
     * private volatile int state;
     * CAS
     * 这个变量的修改必须是原子操作，所以我们需要CAS更新它，我们这里使用Unsafe来直接CAS更新int类型的state。
     * 当然，这个变量如果直接使用AtomicInteger也是可以的，不过，既然我们学习了更底层的Unsafe类那就应该用（浪）起来。
     * private boolean compareAndSetState(int expect, int update) {
     * return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
     * }
     * <p>
     * 一个队列
     * 队列的实现有很多，数组、链表都可以，我们这里采用链表，毕竟链表实现队列相对简单一些，不用考虑扩容等问题。
     * 这个队列的操作很有特点：
     * 放元素的时候都是放到尾部，且可能是多个线程一起放，所以对尾部的操作要CAS更新；
     * 唤醒一个元素的时候从头部开始，但同时只有一个线程在操作，即获得了锁的那个线程，所以对头部的操作不需要CAS去更新。
     * private static class Node {
     * // 存储的元素为线程
     * Thread thread;
     * // 前一个节点（可以没有，但实现起来很困难）
     * Node prev;
     * // 后一个节点
     * Node next;
     * <p>
     * public Node() {
     * }
     * <p>
     * public Node(Thread thread, Node prev) {
     * this.thread = thread;
     * this.prev = prev;
     * }
     * }
     * // 链表头
     * private volatile Node head;
     * // 链表尾
     * private volatile Node tail;
     * // 原子更新tail字段
     * private boolean compareAndSetTail(Node expect, Node update) {
     * return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
     * }
     * 这个队列很简单，存储的元素是线程，需要有指向下一个待唤醒的节点，前一个节点可有可无，但是没有实现起来很困难，不信学完这篇文章你试试。
     * 加锁
     * public void lock() {
     * // 尝试更新state字段，更新成功说明占有了锁
     * if (compareAndSetState(0, 1)) {
     * return;
     * }
     * // 未更新成功则入队
     * Node node = enqueue();
     * Node prev = node.prev;
     * // 再次尝试获取锁，需要检测上一个节点是不是head，按入队顺序加锁
     * while (node.prev != head || !compareAndSetState(0, 1)) {
     * // 未获取到锁，阻塞
     * unsafe.park(false, 0L);
     * }
     * // 下面不需要原子更新，因为同时只有一个线程访问到这里
     * // 获取到锁了且上一个节点是head
     * // head后移一位
     * head = node;
     * // 清空当前节点的内容，协助GC
     * node.thread = null;
     * // 将上一个节点从链表中剔除，协助GC
     * node.prev = null;
     * prev.next = null;
     * }
     * // 入队
     * private Node enqueue() {
     * while (true) {
     * // 获取尾节点
     * Node t = tail;
     * // 构造新节点
     * Node node = new Node(Thread.currentThread(), t);
     * // 不断尝试原子更新尾节点
     * if (compareAndSetTail(t, node)) {
     * // 更新尾节点成功了，让原尾节点的next指针指向当前节点
     * t.next = node;
     * return node;
     * }
     * }
     * }
     * （1）尝试获取锁，成功了就直接返回；
     * （2）未获取到锁，就进入队列排队；
     * （3）入队之后，再次尝试获取锁；
     * （4）如果不成功，就阻塞；
     * （5）如果成功了，就把头节点后移一位，并清空当前节点的内容，且与上一个节点断绝关系；
     * （6）加锁结束；
     * 解锁
     * // 解锁
     * public void unlock() {
     * // 把state更新成0，这里不需要原子更新，因为同时只有一个线程访问到这里
     * state = 0;
     * // 下一个待唤醒的节点
     * Node next = head.next;
     * // 下一个节点不为空，就唤醒它
     * if (next != null) {
     * unsafe.unpark(next.thread);
     * }
     * }
     * （1）把state改成0，这里不需要CAS更新，因为现在还在加锁中，只有一个线程去更新，在这句之后就释放了锁；
     * （2）如果有下一个节点就唤醒它；
     * （3）唤醒之后就会接着走上面lock()方法的while循环再去尝试获取锁；
     * （4）唤醒的线程不是百分之百能获取到锁的，因为这里state更新成0的时候就解锁了，之后可能就有线程去尝试加锁了。
     * 测试
     * 上面完整的锁的实现就完了，是不是很简单，但是它是不是真的可靠呢，敢不敢来试试？！
     * 直接上测试代码：
     * private static int count = 0;
     * <p>
     * public static void main(String[] args) throws InterruptedException {
     * MyLock lock = new MyLock();
     * <p>
     * CountDownLatch countDownLatch = new CountDownLatch(1000);
     * <p>
     * IntStream.range(0, 1000).forEach(i -> new Thread(() -> {
     * lock.lock();
     * <p>
     * try {
     * IntStream.range(0, 10000).forEach(j -> {
     * count++;
     * });
     * } finally {
     * lock.unlock();
     * }
     * //            System.out.println(Thread.currentThread().getName());
     * countDownLatch.countDown();
     * }, "tt-" + i).start());
     * <p>
     * countDownLatch.await();
     * <p>
     * System.out.println(count);
     * }
     * 运行这段代码的结果是总是打印出10000000（一千万），说明我们的锁是正确的、可靠的、完美的。
     * 总结
     * （1）自己动手写一个锁需要做准备：一个变量、一个队列、Unsafe类。
     * （2）原子更新变量为1说明获得锁成功；
     * （3）原子更新变量为1失败说明获得锁失败，进入队列排队；
     * （4）更新队列尾节点的时候是多线程竞争的，所以要使用原子更新；
     * （5）更新队列头节点的时候只有一个线程，不存在竞争，所以不需要使用原子更新；
     * （6）队列节点中的前一个节点prev的使用很巧妙，没有它将很难实现一个锁，只有写过的人才明白，不信你试试^^
     *
     * @param args
     */
}
