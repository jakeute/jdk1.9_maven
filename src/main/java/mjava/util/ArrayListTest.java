package mjava.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 对于arraylist不适用于频繁的插入删除  适用于添加查找
 * toString 被设置成输出其内部数据
 */
public class ArrayListTest {
    public static void main(String[] args) {
        //arraylist 内部是用Object[] 存储对象
        //构造函数可以用 Collection 来继承其他容器的数据

        //利用arrays.aslist 可以快速添加对象
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("12", "1234", "avd"));

        //arraylist 默认扩增数组的容量为原容量的一半 无需手动更改
        //trimToSize 可以将数组大小调整为存储数据的大小

        //contains 是否包含
        // indexOf lastIndexOf

        //clone 克隆容器
        @SuppressWarnings("unchecked")
        ArrayList<String> arrayList2 = (ArrayList<String>) arrayList.clone();

        System.out.println(arrayList2.get(1));

        //toArray 无参时，会返回Object[] 数组
        //因为泛型擦除，容器无法获得泛型信息
        // 可以提供一个数组类型 获得数组的class 返回T[] 数组
        String[] strings = arrayList.toArray(new String[0]);
        System.out.println(strings[2]);

        //get set add remove clear 增删改查
        //addAll
        // removeAll  A-B
        // retainAll  A∩B
        System.out.println();


        //迭代器 hasNext->当前位置下是否有数据
        // next->输出当前的位置下的数据 并且向后位移
        // remove -> 删除next上次的位置
        Iterator<String> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }
        System.out.println(arrayList.size());


    }
}
