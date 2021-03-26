package mjava.util.concurrent;

/**
 * 内部使用currentskipmap实现
 * <p>
 * 其中需要注意的是add 中调用的是putIfAbsent
 * 如果是currentskipmap.put()的话，当cmp的结果为0时还会更新相同的value
 * 而currentskipmap.putIfAbsent则发现key相同时，不进行更新，直接返回
 */
public class ConcurrentSkipListSetTest {
}
