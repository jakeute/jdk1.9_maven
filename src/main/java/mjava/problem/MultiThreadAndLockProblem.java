package mjava.problem;

/**
 * 多线程带来了一个问题：同步控制。所谓同步，就是保证多线程能对共享资源的读写能够安全有效的运行。而控制，则是多个线程能按照我们的意愿去保证执行顺序
 * 所以我们引入了锁的概念，并在锁的基础上开发了一系列的同步控制器与并发集合，来保证安全性
 * <p>
 * 1. 在Java中锁分为好几种类型：
 * 公平锁/非公平锁
 * 可重入锁
 * 独享锁/共享锁
 * 互斥锁/读写锁
 * 乐观锁/悲观锁
 * 分段锁
 * 偏向锁/轻量级锁/重量级锁
 * 自旋锁
 * <p>
 * <p>
 * 公平锁/非公平锁
 * 公平锁是指多个线程按照申请锁的顺序来获取锁。
 * 非公平锁是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁。有可能，会造成优先级反转或者饥饿现象。
 * 对于Java ReentrantLock而言，通过构造函数指定该锁是否是公平锁，默认是非公平锁。非公平锁的优点在于吞吐量比公平锁大。
 * 对于Synchronized而言，也是一种非公平锁。由于其并不像ReentrantLock是通过AQS的来实现线程调度，所以并没有任何办法使其变成公平锁。
 * <p>
 * <p>
 * 可重入锁
 * 广义上的可重入锁指的是可重复可递归调用的锁，在外层使用锁之后，在内层仍然可以使用，并且不发生死锁（前提得是同一个对象或者class），
 * 这样的锁就叫做可重入锁。ReentrantLock和synchronized都是可重入锁。
 * <p>
 * 独享锁/共享锁
 * 独享锁是指该锁一次只能被一个线程所持有。
 * 共享锁是指该锁可被多个线程所持有。
 * 对于Java ReentrantLock而言，其是独享锁。但是对于Lock的另一个实现类ReadWriteLock，其读锁是共享锁，其写锁是独享锁。
 * 读锁的共享锁可保证并发读是非常高效的，读写，写读 ，写写的过程是互斥的。
 * 独享锁与共享锁也是通过AQS来实现的，通过实现不同的方法，来实现独享或者共享。
 * 对于Synchronized而言，当然是独享锁。
 * <p>
 * 互斥锁/读写锁
 * 上面讲的独享锁/共享锁就是一种广义的说法，互斥锁/读写锁就是具体的实现。
 * 互斥锁在Java中的具体实现就是ReentrantLock
 * 读写锁在Java中的具体实现就是ReadWriteLock
 * <p>
 * 乐观锁/悲观锁
 * 乐观锁与悲观锁不是指具体的什么类型的锁，而是指看待并发同步的角度。
 * 悲观锁认为对于同一个数据的并发操作，一定是会发生修改的，哪怕没有修改，也会认为修改。因此对于同一个数据的并发操作，悲观锁采取加锁的形式。悲观的认为，不加锁的并发操作一定会出问题。
 * 乐观锁则认为对于同一个数据的并发操作，是不会发生修改的。在更新数据的时候，会采用尝试更新，不断重新的方式更新数据。乐观的认为，不加锁的并发操作是没有事情的。
 * 从上面的描述我们可以看出，悲观锁适合写操作非常多的场景，乐观锁适合读操作非常多的场景，不加锁会带来大量的性能提升。
 * 悲观锁在Java中的使用，就是利用各种锁。
 * 乐观锁在Java中的使用，原子类：利用原子类 Atomic* 的 CAS 操作，也可以实现同步。需要注意的是CSA的底层也可以看作一个锁，同一时间内只能有一个线程去更新比较
 * 需要注意的是原子类仅仅保证多个线程同时更改数据时，数据不会混乱，但是
 * 更改时的流程是没有前后顺序可言的，所以在更改后获取的数据或许并非想要的结果
 * <p>
 * <p>
 * 分段锁
 * 分段锁其实是一种锁的设计，并不是具体的一种锁，对于ConcurrentHashMap而言，其并发的实现就是通过分段锁的形式来实现高效的并发操作。
 * 我们以ConcurrentHashMap来说一下分段锁的含义以及设计思想，ConcurrentHashMap中的分段锁称为Segment，它即类似于HashMap（JDK7与JDK8中HashMap的实现）的结构，
 * 即内部拥有一个Entry数组，数组中的每个元素又是一个链表；同时又是一个ReentrantLock（Segment继承了ReentrantLock)。
 * 当需要put元素的时候，并不是对整个hashmap进行加锁，而是先通过hashcode来知道他要放在那一个分段中，然后对这个分段进行加锁，所以当多线程put的时候，只要不是放在一个分段中，就实现了真正的并行的插入。
 * 但是，在统计size的时候，可就是获取hashmap全局信息的时候，就需要获取所有的分段锁才能统计。
 * 分段锁的设计目的是细化锁的粒度，当操作不需要更新整个数组的时候，就仅仅针对数组中的一项进行加锁操作。
 * <p>
 * <p>
 * 偏向锁/轻量级锁/重量级锁
 * 这三种锁是指锁的状态，并且是针对Synchronized。在Java 5通过引入锁升级的机制来实现高效Synchronized。这三种锁的状态是通过对象监视器在对象头中的字段来表明的。
 * <p>
 * 初次执行到synchronized代码块的时候，锁对象变成偏向锁（通过CAS修改对象头里的锁标志位），字面意思是“偏向于第一个获得它的线程”的锁。执行完同步代码块后，线程并不会主动释放偏向锁。当第二次到达同步代码块时，
 * 线程会判断此时持有锁的线程是否就是自己（持有锁的线程ID也在对象头里），如果是则正常往下执行。由于之前没有释放锁，这里也就不需要重新加锁。如果自始至终使用锁的线程只有一个，很明显偏向锁几乎没有额外开销，性能极高。
 * 一旦线程ID 不一样也就是说第二个线程也想要获取锁，偏向锁就升级为轻量级锁（自旋锁）。
 * <p>
 * 这里要明确一下什么是锁竞争：如果多个线程轮流获取一个锁，但是每次获取锁的时候都很顺利，
 * 没有发生阻塞，那么就不存在锁竞争。只有当某线程尝试获取锁的时候，发现该锁已经被占用，只能等待其释放，这才发生了锁竞争。
 * <p>
 * 在轻量级锁状态下发生锁竞争，没有抢到锁的线程将自旋，即不停地循环判断锁是否能够被成功获取，并不会挂起。获取锁的操作，其实就是通过CAS修改对象头里的锁标志位。
 * 先比较当前锁标志位是否为“释放”，如果是则将其设置为“锁定”，比较并设置是原子性发生的。这就算抢到锁了，然后线程将当前锁的持有者信息修改为自己。
 * 长时间的自旋操作是非常消耗资源的，一个线程持有锁，其他线程就只能在原地空耗CPU，执行不了任何有效的任务，这种现象叫做忙等（busy-waiting）。
 * 如果多个线程用一个锁，但是没有发生锁竞争，或者发生了很轻微的锁竞争，那么synchronized就用轻量级锁，允许短时间的忙等现象。这是一种折衷的想法，短时间的忙等，换取线程在用户态和内核态之间切换的开销。
 * <p>
 * 显然，此忙等是有限度的（有个计数器记录自旋次数，默认允许循环10次，可以通过虚拟机参数更改）。如果锁竞争情况严重，某个达到最大自旋次数的线程，会将轻量级锁升级为重量级锁（依然是CAS修改锁标志位，但不修改持有锁的线程ID）。
 * 当后续线程尝试获取锁时，发现被占用的锁是重量级锁，则直接将自己挂起（而不是忙等），等待将来被唤醒。在JDK1.6之前，synchronized直接加重量级锁，很明显现在得到了很好的优化。
 * <p>
 * 一个锁只能按照 偏向锁、轻量级锁、重量级锁的顺序逐渐升级（也有叫锁膨胀的），不允许降级。
 * <p>
 * <p>
 * 自旋锁
 * 自旋锁原理非常简单，如果持有锁的线程能在很短时间内释放锁资源，那么那些等待竞争锁的线程就不需要做内核态和用户态之间的切换进入阻塞挂起状态，
 * 它们只需要等一等（自旋），等持有锁的线程释放锁后即可立即获取锁，这样就避免用户线程和内核的切换的消耗。
 * 但是线程自旋是需要消耗cup的，说白了就是让cup在做无用功，如果一直获取不到锁，那线程也不能一直占用cup自旋做无用功，
 * 所以需要设定一个自旋等待的最大时间。如果持有锁的线程执行的时间超过自旋等待的最大时间扔没有释放锁，就会导致其它争用锁的线程在最大等待时间内还是获取不到锁，
 * 这时争用线程会停止自旋进入阻塞状态。
 * <p>
 * <p>
 * 2. 同步控制器：利用锁编写的一些类，通过使用它们，可以达到控制线程运行的效果
 * <p>
 * 读写锁：能同时读读 不能读写 写写
 * ReentrantReadWriteLock
 * <p>
 * 信号量：
 * Semaphore
 * <p>
 * 倒计数器：
 * CountDownLatch
 * <p>
 * 循环栅栏：
 * CyclicBarrier
 * <p>
 * 线程阻塞工具类：
 * LockSupport
 * <p>
 * 阶段控制器：
 * Phaser
 * <p>
 * <p>
 * 3.并发容器：
 * ConcurrentHashMap ConcurrentLinkedQueue CopyOnWriteArraySet....
 * <p>
 * 需要注意的是线程安全的容器，保证多个线程同时操作容器的正确性，但是并不保证逻辑的正确性
 * private static final Map<Integer, Integer> map = new ConcurrentHashMap<>();
 * <p>
 * public void unsafeUpdate(Integer key, Integer value) {
 * Integer oldValue = map.get(key);
 * if (oldValue == null) {
 * (STOP)
 * map.put(key, value);
 * }
 * }
 * <p>
 * 多个线程可同时到达STOP处操作容器，虽然都能插入，但顺序无法保证
 */
public class MultiThreadAndLockProblem {

}