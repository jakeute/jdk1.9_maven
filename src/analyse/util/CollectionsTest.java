package analyse.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * List：arraylist linkedlist
 * list可以看作是可变长度的数组，可以当作
 * 栈或者队列使用
 * <p>
 * Set：hashset linkedhashset treeset（comparable）
 * set利用map的key不可重复性进行存储
 * <p>
 * map: hashmap linkedhashmap treemap（comparable）
 * 存储键值对 key是不可以重复的
 * <p>
 * <p>
 * 为集合list常用操作使用
 * list集合代表的是 按照添加顺序排序的可重复扩展集合
 * get set add remove
 * 迭代器也是按照顺序读取的
 */
public class CollectionsTest {
    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("pxj", "Luoli", "heisi", "baisi"));
        System.out.println(arrayList);
        //sort  实现Comparable 或者提供 Comparator
        Collections.sort(arrayList);
        System.out.println(arrayList);
        //shuffle 随机排序
        Collections.shuffle(arrayList);
        System.out.println(arrayList);
        //binarySearch 二分查找 实现Comparable
        Collections.binarySearch(arrayList, "123");

        //replaceAll
        //reverse 颠倒顺序
        //frequency() 出现次数
        // rotate 循环右移
        //copy 将另一个list的数据添加到目标的数据之后
        //swap 交换数据


        //将容器转化为线程安全的容器
        //内部使用了委托，将具体的功能交给传入的容器，而它只负责加锁
        //同一时间只能有一个线程进行操作
        //        Collections.synchronizedList()
        //        Collections.synchronizedMap()
    }
}
