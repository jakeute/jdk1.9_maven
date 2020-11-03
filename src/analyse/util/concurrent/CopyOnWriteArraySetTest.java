package analyse.util.concurrent;

/**
 * （1）CopyOnWriteArraySet是用CopyOnWriteArray实现的；
 * <p>
 * （2）CopyOnWriteArraySet是有序的，因为底层其实是数组，数组是不是有序的？！
 * <p>
 * （3）CopyOnWriteArraySet是并发安全的，而且实现了读写分离；
 * <p>
 * （4）CopyOnWriteArraySet通过调用CopyOnWriteArrayList的addIfAbsent()方法来保证元素不重复；
 */
public class CopyOnWriteArraySetTest {
}
