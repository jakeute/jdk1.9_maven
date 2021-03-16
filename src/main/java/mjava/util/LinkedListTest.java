package mjava.util;

import java.util.LinkedList;

/**
 * linkedlist 适用于频繁的插入，删除。对于查找能力弱
 * 其内部为双向链表，
 * LinkedList 实现了 Queue 接口，可作为队列使用。
 * LinkedList 实现了 Deque 接口，可作为双端队列使用。
 * toString 被设置成输出其内部数据
 */
public class LinkedListTest {
    /**
     * getFirst getLast removeFirst removeLast addFirst addLast
     * 通过上面的方法 linkedlist可以还被包装成为 队列 栈
     * 队列： peek(返回第一个) offer(插入最后一个) poll(弹出第一个)   ---队头是双向链表的头
     * 栈： pop（弹出第一个） push（插入第一个） peek(查看顶)  ---栈顶是双向链表的头
     * remove addAll  get set indexof lastindexof
     * clone toArray
     *
     * @param args
     */
    public static void main(String[] args) {
        LinkedList<String> queue = new LinkedList<>();
        queue.add("1");
        queue.add("2");
        queue.add("3");
        queue.add("4");

        queue.push("0");

        System.out.println(queue.poll());
        System.out.println(queue.pop());
    }
}
