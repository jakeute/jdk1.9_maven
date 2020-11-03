package analyse.util;

import java.util.LinkedList;

/**
 * linkedlist 适用于频繁的插入，删除。对于查找能力弱
 * 其内部为双向链表，其不仅可以作为容器，也可以作为栈或者队列
 * toString 被设置成输出其内部数据
 */
public class LinkedListTest {
    /**
     * getFirst getLast removeFirst removeLast addFirst addLast
     * 通过上面的方法 linkedlist可以还被包装成为 队列 栈
     * 队列： peek(查看头) add(入队) poll(出队)   ---队头是双向链表的头
     * 栈： pop（出顶） push（入栈） peek(查看顶)  ---栈顶是双向链表的头
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
