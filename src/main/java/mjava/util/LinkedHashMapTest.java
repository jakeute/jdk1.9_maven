package mjava.util;

import java.awt.*;

/**
 * LinkedHashMap 继承了 HashMap
 * <p>
 * HashMap的内部数据结构是 Node                          implement Map.Entry
 * LinkedHashMap的内部数据结构是 LinkedHashMap.Entry     extends Map.Node
 * HashMap的 TreeNode                            extends LinkedHashMap.Entry
 * <p>
 * LinkedMap 内部主要方法继承于 hashMap 但是它需要维护一个双向链表
 * 因此需要将节点 Node 替换为 LinkedHashMap.Entry 。
 * 但是因为 节点数据结构 的继承关系 LinkedHashMap.Entry也能在HashMap中正常运行
 * 所以LinkedMap 重写了HashMap中生成 Node的方法（在生成LinkedHashMap.Entry的同时添加到双向链表结尾）
 * 对于TreeNode也是重写了生成方法
 */
public class LinkedHashMapTest {
    public static void main(String[] args) {
        System.out.println(Color.blue);
    }
}
