package mjava.util;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * arrays主要处理与数组相关的数据
 */
public class ArraysTest {
    @Test
    public void test1() {
        //aslist
        //根据输入的对象数组生成Arrays中内部类Arraylist（无法添加）
        //而且生成的list内部使用的是提供的对象数组
        String[] strings = {"1", "2", "3"};
        List<String> strings1 = Arrays.asList(strings);
        strings1.set(0, "5");
        System.out.println(strings[0]);

        //binarySearch 二分查找
        //使用二进制搜索算法搜索指定值的指定字节数组。 在进行此调用之前，数组必须按照sort()方法进行排序。
        // 如果没有排序，结果是未定义的。 如果数组包含具有指定值的多个元素，则不能保证将找到哪个元素。
        // 数组对象需要实现Comparable接口 <T extends Comparable<T>> int search(T[] t,T s)
        Arrays.binarySearch(strings, "6");
    }


    @Test
    public void test2() {
        //copyof
        //复制数组
        String[] strings = {"1", "2", "3"};
        String[] scp = Arrays.copyOf(strings, strings.length);
        int[] i = {1, 2, 3, 4, 5};
        int[] icp = Arrays.copyOf(i, i.length);


        //equal
        //比较两个数组的内容
        //如果要比较二维数组则需要 deepEquals
        System.out.println(Arrays.equals(i, icp));

        //toString  deepToString
        //输出数组的信息


        //stream  返回流
        Stream<String> stream = Arrays.stream(strings);

        //sort 排序（由小到大）
        //数组对象需要实现Comparable接口 或者提供 Comparator

        //fill 数组填充
        Arrays.fill(scp, "asdas");

    }

}
