package analyse.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * stream的基础是所有的Collection中的无参stream方法与Arrays的静态方法
 * stream操作数据更加简洁，并且不会改变原集合或者数组
 * stream依靠Lambda（接口实现）
 */
public class StreamTest {

    /**
     * 创造流
     */
    @Test
    public void creat() {
        //方法一：Collection实现stream() --容器
        ArrayList<String> strings = new ArrayList<>();
        Stream<String> stream = strings.stream();

        //方法二：Arrays静态方法stream()--数组
        //能生成IntStream。。。
        IntStream stream1 = Arrays.stream(new int[]{1, 2, 3, 4});

        //方法三：Stream.of() --离散数据
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4);

        //无限流
        Stream<Integer> limit = Stream.iterate(0, t -> t + 5).limit(5);
        Stream<Double> limit1 = Stream.generate(Math::random).limit(5); //随机数
    }

    /**
     * 中间操作符
     * map(mapToInt,mapToLong,mapToDouble) 转换操作符，把比如A->B，这里默认提供了转int，long，double的操作符。
     * <p>
     * flatmap(flatmapToInt,flatmapToLong,flatmapToDouble) 拍平操作比如把 int[]{2,3,4}
     * 拍平 变成 2，3，4 也就是从原来的一个数据变成了3个数据，这里默认提供了拍平成int,long,double的操作符。
     * <p>
     * limit 限流操作，比如数据流中有10个 我只要出前3个就可以使用。
     * <p>
     * distinct 去重操作，对重复元素去重，底层使用了equals方法。
     * <p>
     * filter 过滤操作，把不想要的数据过滤。
     * <p>
     * peek 挑出操作，如果想对数据进行某些操作，如：读取、编辑修改等。
     * <p>
     * skip 跳过操作，跳过某些元素。
     * <p>
     * sorted(unordered) 排序操作，对元素排序，前提是实现Comparable接口，当然也可以自定义比较器。
     * <p>
     * parallel 将顺序流转换为并行流
     * sequential 将并行流转换为顺序流
     */
    @Test
    public void middle() {

        Stream<Integer> stream = Stream.iterate(0, t -> t + 5).limit(5);

        //过滤
        //filter()需要一个返回boolean，参数是流泛型的接口实现
        //boolean test(T t);
        Stream<Integer> stream1 = stream.filter(i -> i > 8);

        //去重
        Stream<Integer> stream2 = stream1.distinct();

        //转换
        Stream<Double> doubleStream = stream2.map(t -> t + 0.5);
        IntStream intStream = doubleStream.mapToInt(Double::intValue);

        //将流的元素拆分重新组合成新的流
        Stream.of("a-b-c-d", "e-f-i-g-h")
                .flatMap(e -> Stream.of(e.split("-")))
                .forEach(System.out::println);

        //对流内的元素进行操作
        Stream<Double> peek = doubleStream.peek(t -> {
            t += 1;
            System.out.println(t);
        });

        //对流内的元素排序
        Stream<Double> sorted = peek.sorted();
    }

    /**
     * 终结操作
     * collect 收集操作，将所有数据收集起来，这个操作非常重要，官方的提供的Collectors 提供了非常多收集器，可以说Stream 的核心在于Collectors。
     * <p>
     * count 统计操作，统计最终的数据个数。
     * <p>
     * findFirst、findAny 查找操作，查找第一个、查找任何一个 返回的类型为Optional。
     * <p>
     * noneMatch、allMatch、anyMatch 匹配操作，数据流中是否存在符合条件的元素 返回值为bool 值。
     * <p>
     * min、max 最值操作，需要自定义比较器，返回数据流中最大最小的值。
     * <p>
     * reduce 规约操作，将整个数据流的值规约为一个值，count、min、max底层就是使用reduce。
     * <p>
     * forEach、forEachOrdered 遍历操作，这里就是对最终的数据进行消费了。
     * <p>
     * toArray 数组操作，将数据流的元素转换成数组。
     * <p>
     * toList 集合操作
     */
    @Test
    public void finalization() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        //语法糖
        strings.forEach(s -> {
            s += "lqx";
            System.out.println(s);
        });
    }
}
