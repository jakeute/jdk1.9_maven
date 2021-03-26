package mjava.lang.reflect;

/**
 * Constructor类：代表类的构造方法。
 * public T newInstance(Object... initargs)
 * 使用由此Constructor对象表示的构造函数，使用指定的初始化参数创建和初始化构造函数的声明类的新实例。 个别参数自动解包以匹配原始形式参数，原始参考参数和参考参数都需要进行方法调用转换。
 * 如果底层构造函数所需的形式参数的数量为0，则提供的initargs数组的长度为0或为空。
 * 需要可访问 如果为私有构造 则调用前需要setAccessible(true);
 */
public class ConstructTest {
}
