package analyse.util;

import java.util.PriorityQueue;

/**
 * 优先队列：数据按关键词有序排列，插入新数据的时候，会自动插入到合适的位置保证队列有序（降序或者升序）
 * 简单版本的是从头开始往后比较寻找插入位置
 * PriorityQueue是基于优先堆的一个无界队列，这个优先队列中的元素可以默认自然排序或者通过提供的Comparator（比较器）在队列实例化的时排序
 * 堆可以视为一个完全二叉树，这样插入速度更快  它是小堆 即最小的在头顶
 * 插入元素时放到数组末端，进行由下到上的排序
 * 弹出元素时，将末尾元素放到头的位置，由上到下排序
 * <p>
 * 比较： comparable：a.compareTo(b)  -> a-b
 * comparator compare(a,b)  -> a-b
 */
public class PriorityQueueTest {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        //队列由小到大排序
        //poll() 检索并删除此队列的头 如果此队列为空，则返回 null 。
        //peek() 检索但不删除此队列的头，如果此队列为空，则返回 null 。
        //add() 添加到队列末尾
        //indexOf()


        queue.add(6);
        queue.add(9);
        queue.add(10);
        queue.add(7);
        queue.add(11);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.peek());


        //iterator 是按照数组从头开始输出的（层次输出）
        for (Integer s : queue) {
            System.out.println(s);
        }

    }
}
