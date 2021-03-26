package mjava.util.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap是HashMap的线程安全版本，内部也是使用（数组 + 链表 + 红黑树）的结构来存储元素。
 * 相比于同样线程安全的HashTable来说，效率等各方面都有极大地提高。
 * <p>
 * put()
 * 整体流程跟HashMap比较类似，大致是以下几步：
 * <p>
 * （1）如果桶数组未初始化，则初始化；
 * <p>
 * （2）如果待插入的元素所在的桶为空，则尝试把此元素直接插入到桶的第一个位置（CAS）；
 * <p>
 * （3）如果正在扩容，则当前线程一起加入到扩容的过程中；
 * <p>
 * （4）如果待插入的元素所在的桶不为空且不在迁移元素，则锁住这个桶（分段锁 synchronized(桶头)），不允许其他线程更改桶，但是可以访问；
 * <p>
 * （5）如果当前桶中元素以链表方式存储，则在链表中寻找该元素或者插入元素；
 * <p>
 * （6）如果当前桶中元素以红黑树方式存储，则在红黑树中寻找该元素或者插入元素；
 * <p>
 * （7）如果元素存在，则返回旧值；
 * <p>
 * （8）如果元素不存在，整个Map的元素个数加1，并检查是否需要扩容；
 * <p>
 * 添加元素操作中使用的锁主要有（ CAS + synchronized ）。
 * <p>
 * <p>
 * initTable()
 * （1）使用CAS锁控制只有一个线程初始化桶数组；
 * <p>
 * （2）sizeCtl在初始化后存储的是扩容门槛；
 * <p>
 * （3）扩容门槛写死的是桶数组大小的0.75倍，桶数组大小即map的容量，也就是最多存储多少个元素。
 * <p>
 * <p>
 * addCount(long x, int check)
 * （1）元素个数的存储方式类似于LongAdder类，存储在不同的段上，减少不同线程同时更新size时的冲突；
 * <p>
 * （2）计算元素个数时把这些段的值及baseCount相加算出总的元素个数；
 * <p>
 * （3）正常情况下sizeCtl存储着扩容门槛，扩容门槛为容量的0.75倍；
 * <p>
 * （4）扩容时sizeCtl高位存储扩容邮戳(resizeStamp)，低位存储扩容线程数加1（1+nThreads）；
 * <p>
 * （5）此时只能有一个线程能CAS更改sizeCtl状态，进行数组扩容。其它线程添加元素后如果发现存在扩容，则会加入的协助扩容行列中来；
 * 扩容状态下其他线程对集合进行插入、修改、删除、合并、compute 等操作时遇到 ForwardingNode（其hash为-1，
 * 正常的节点 hash为 key的 hash不可能为负数）节点会触发协助扩容 。
 * <p>
 * <p>
 * transfer(Node<K,V>[] tab, Node<K,V>[] nextTab)
 * （1）新桶数组大小是旧桶数组的两倍；
 * <p>
 * （2）根据CPU的数目将数组长度划分出多个区域，供多线程进行协同操作。迁移元素先从靠后的桶开始；
 * <p>
 * （3）迁移完成的桶在里面放置一ForwardingNode类型的元素，标记该桶迁移完成；
 * <p>
 * （4）迁移时根据hash&n是否等于0把桶中元素分化成两个链表或树；
 * <p>
 * （5）低位链表（树）存储在原来的位置；
 * <p>
 * （6）高们链表（树）存储在原来的位置加n的位置；
 * <p>
 * （7）迁移元素时会锁住当前桶，也是分段锁的思想；
 * <p>
 * <p>
 * remove()
 * （1）计算hash；
 * <p>
 * （2）如果所在的桶不存在，表示没有找到目标元素，返回；
 * <p>
 * （3）如果正在扩容，则协助扩容完成后再进行删除操作；
 * <p>
 * （4）如果待删除的元素所在的桶不为空且不在迁移元素，则锁住这个桶（分段锁）；
 * <p>
 * （5）如果是以链表形式存储的，则遍历整个链表查找元素，找到之后再删除；
 * <p>
 * （6）如果是以树形式存储的，则遍历树查找元素，找到之后再删除；
 * <p>
 * （7）如果是以树形式存储的，删除元素之后树较小，则退化成链表；
 * <p>
 * （8）如果确实删除了元素，则整个map元素个数减1，并返回旧值；
 * <p>
 * （9）如果没有删除元素，则返回null；
 * <p>
 * <p>
 * get()
 * （1）hash到元素所在的桶；
 * <p>
 * （2）如果桶中第一个元素就是该找的元素，直接返回；
 * <p>
 * （3）如果是树或者正在迁移元素，则调用各自Node子类的find()方法寻找元素；
 * <p>
 * （4）如果是链表，遍历整个链表寻找元素；
 * <p>
 * （5）获取元素没有加锁
 * <p>
 * size()
 * （1）元素的个数依据不同的线程存在在不同的段里；（见addCounter()分析）
 * <p>
 * （2）计算CounterCell所有段及baseCount的数量之和；
 * <p>
 * （3）获取元素个数没有加锁；
 * <p>
 * <p>
 * 总结：
 * （1）ConcurrentHashMap是HashMap的线程安全版本；
 * （2）ConcurrentHashMap采用（数组 + 链表 + 红黑树）的结构存储元素；
 * （3）ConcurrentHashMap相比于同样线程安全的HashTable，效率要高很多；
 * （4）ConcurrentHashMap采用的锁有 synchronized，CAS，自旋锁，分段锁，volatile等；
 * （5）ConcurrentHashMap中没有threshold和loadFactor这两个字段，而是采用sizeCtl来控制；
 * （6）sizeCtl = -1，表示正在进行初始化；
 * （7）sizeCtl = 0，默认值，表示后续在真正初始化的时候使用默认容量；
 * （8）sizeCtl > 0，在初始化之前存储的是传入的容量，在初始化或扩容后存储的是下一次的扩容门槛；
 * （9）在进行扩容时，sizeCtl = (resizeStamp << 16) + (1 + nThreads)，高位存储扩容邮戳，低位存储扩容线程数加1；
 * （10）更新操作时如果正在进行扩容，当前线程协助扩容；
 * （11）更新操作会采用synchronized锁住当前桶的第一个元素，这是分段锁的思想；
 * （12）整个扩容过程都是通过CAS控制sizeCtl这个字段来进行的，这很关键；
 * （13）迁移完元素的桶会放置一个ForwardingNode节点，以标识该桶迁移完毕；
 * （14）元素个数的存储也是采用的分段思想，类似于LongAdder的实现；
 * （15）元素个数的更新会把不同的线程hash到不同的段上，减少资源争用；
 * （16）元素个数的更新如果还是出现多个线程同时更新一个段，则会扩容段（CounterCell）；
 * （17）获取元素个数是把所有的段（包括baseCount和CounterCell）相加起来得到的；
 * （18）查询操作是不会加锁的，所以ConcurrentHashMap不是强一致性的；
 * （19）ConcurrentHashMap中不能存储key或value为null的元素；
 */
public class ConcurrentHashMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    }
}
