package mjava.util.concurrent.atomic;

/**
 * AtomicInteger、AtomicLong使用非阻塞的CAS算法原子性地更新某一个变量，
 * 比synchronized这些阻塞算法拥有更好的性能，但是在高并发情况下，大量线程同时去更新一个变量，
 * 由于同一时间只有一个线程能够成功，绝大部分的线程在尝试更新失败后，会通过自旋的方式再次进行尝试，严重占用了CPU的时间片。
 *
 * LongAdder是JDK8新增的原子操作类，它提供了一种新的思路，既然AtomicLong的性能瓶颈是由于大量线程同时更新一个变量造成的，
 * 那么能不能把这个变量拆分出来，变成多个变量，然后让线程去竞争这些变量，最后合并即可?LongAdder的设计精髓就在这里，
 * 通过将变量拆分成多个元素，降低该变量的并发度，最后进行合并元素，变相的减少了CAS的失败次数。
 *
 * LongAdder内部维护了一个Cell类型的数组，其中Cell是Striped64中的一个静态内部类。
 * Cell用来封装被拆分出来的元素，内部用一个value字段保存当前元素的值，等到需要合并时，则累加所有Cell数组中的value。
 * Cell内部使用CAS操作来更新value值，
 *
 * LongAdder在没有线程竞争的时候，只使用base值，此时的情况就类似与AtomicLong。但LongAdder的高明之处在于，
 * 发生线程竞争时，便会使用到Cell数组，所以该数组是惰性加载的。
 *
 * LongAdder的总大小是：base+Cell
 */
public class LongAdderTest {
}
