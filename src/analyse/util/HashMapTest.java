package analyse.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * HashMap基于Map接口实现，元素以键值对的方式存储，并且允许使用null 建和null　值，
 * 因为key不允许重复，因此只能有一个键为null,另外HashMap不能保证放入元素的顺序，它是无序的，
 * 和放入的顺序并不能相同。HashMap是线程不安全的。
 * <p>
 * 首先有一个每个元素都是链表（可能表述不准确）的数组，当添加一个元素（key-value）时，就首先计算元素key的hash值，
 * 以此确定插入数组中的位置，在数组位置上获取链表，从头开始比较，当hash值相同并且equals 时更换value，否则在最后添加节点
 * <p>
 * 当桶的数量大于64且单个桶中元素的数量大于8时，链表就转换为红黑树，这样大大提高了查找的效率。使用key哈希值作为树的分支变量，如果两个哈希值不等，但指向同一个桶的话，
 * 较大的那个会插入到右子树里，较小的会插入到左子树。如果哈希值相等，且equals不相等，HashMap希望put的 key 最好是实现了Comparable接口的，而且
 * 与比较节点是同一个类，这样它可以按照比较来进行插入。否则的话按照System.identityHashCode判断插入位置（无法保证是插入右子树还是左子树）
 * 应为TreeNode是Node的子类所以依旧能放在原先的数组中，在一些方法中需要判断是否instanceof TreeNode 来调用不同的方法
 * <p>
 * 当size超过初始容量的0.75时，再散列将链表数组扩大2倍，把原链表数组的搬移到新的数组中。
 * <p>
 * <p>
 * put()
 * （1）计算key的hash值；
 * <p>
 * （2）如果桶（数组）数量为0，则初始化桶；
 * <p>
 * （3）如果key所在的桶没有元素，则直接插入；
 * <p>
 * （4）如果key所在的桶中的第一个元素的key与待插入的key相同，说明找到了元素，转后续流程（9）处理；
 * <p>
 * （5）如果第一个元素是树节点，则调用树节点的putTreeVal()寻找元素或插入树节点；
 * <p>
 * （6）如果不是以上三种情况，则遍历桶对应的链表查找key是否存在于链表中；
 * <p>
 * （7）如果找到了对应key的元素，则转后续流程（9）处理；
 * <p>
 * （8）如果没找到对应key的元素，则在链表最后插入一个新节点并判断是否需要树化；
 * <p>
 * （9）如果找到了对应key的元素，则判断是否需要替换旧值，并直接返回旧值；
 * <p>
 * （10）如果插入了元素，则数量加1并判断是否需要扩容；
 * <p>
 * resize()
 * （1）如果使用是默认构造方法，则第一次插入元素时初始化为默认值，容量为16，扩容门槛为12；
 * <p>
 * （2）如果使用的是非默认构造方法，则第一次插入元素时初始化容量等于扩容门槛，扩容门槛在构造方法里等于传入容量向上最近的2的n次方；
 * <p>
 * （3）如果旧容量大于0，则新容量等于旧容量的2倍，但不超过最大容量2的30次方，新扩容门槛为旧扩容门槛的2倍；
 * <p>
 * （4）创建一个新容量的桶；
 * <p>
 * （5）搬移元素，原链表分化成两个链表，低位链表存储在原来桶的位置，高位链表搬移到原来桶的位置加旧容量的位置；
 */
public class HashMapTest {
    public static void main(String[] args) {
        //put remove  get  putAll size
        HashMap<String, String> map = new HashMap<>();
        map.put("s", "56");
        map.put("e", "00");
        map.put("p", "5");

        //clear containsKey containsValue

        System.out.println(map.containsKey("s"));
        System.out.println(map.containsValue("00"));
        // entrySet() keySet() values()

        Set<Map.Entry<String, String>> entries = map.entrySet();
        for (Map.Entry<String, String> next : entries) {
            System.out.println(next.getKey() + " " + next.getValue());
        }

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println(key + "  " + map.get(key));
        }


        Collection<String> values = map.values();
        System.out.println(values);
    }
}

