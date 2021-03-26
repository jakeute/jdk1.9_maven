package mjava.util;

import java.util.EnumSet;

/**
 * EnumSet的集合元素也是有序的，EnumSet以枚举值在Enum类内的定义顺序来决定集合元素的顺序。
 * EnumSet在内部以位向量的形式存储，这种存储形式非常紧凑、高效,因此EnumSet对象占用内存很小，而且运行效率很好。
 * 尤其是进行批量操作（如调用containsAll()和retainAll()方法）时，如果其参数也是EnumSet集合，则该批量操作的执行速度也非常快。
 * EnumSet集合不允许加入null元素，如果试图插入null元素，EnumSet将抛出NullPointerException异常。
 * EnumSet类没有暴露任何构造器来创建该类的实例，程序应该通过它提供的类方法来创建EnumSet对象。
 * 如果只是想判断EnumSet是否包含null元素或试图删除null元素都不会抛出异常，只是删除操作将返回false，因为没有任何null元素被删除。
 *
 *
 * EnumSet allOf(Class elementType): 创建一个包含指定枚举类里所有枚举值的EnumSet集合。
 * EnumSet complementOf(EnumSet e): 创建一个其元素类型与指定EnumSet里元素类型相同的EnumSet集合，新EnumSet集合包含原EnumSet集合所不包含的、
 * 此类枚举类剩下的枚举值（即新EnumSet集合和原EnumSet集合的集合元素加起来是该枚举类的所有枚举值）。
 * EnumSet copyOf(Collection c): 使用一个普通集合来创建EnumSet集合。
 * EnumSet copyOf(EnumSet e): 创建一个指定EnumSet具有相同元素类型、相同集合元素的EnumSet集合。
 * EnumSet noneOf(Class elementType): 创建一个元素类型为指定枚举类型的空EnumSet。
 * EnumSet of(E first,E…rest): 创建一个包含一个或多个枚举值的EnumSet集合，传入的多个枚举值必须属于同一个枚举类。
 * EnumSet range(E from,E to): 创建一个包含从from枚举值到to枚举值范围内所有枚举值的EnumSet集合。
 *
 */
public class EnumSetTest {
    public static void main(String[] args) {
        EnumSet<Dog> es = EnumSet.allOf(Dog.class);
        System.out.println(es);
    }

    enum Dog {
        pxj,ww,pas,we
    }
}
