package mjava.util.concurrent;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue是java并发包下的延时阻塞队列，常用于实现定时任务，
 * 阻塞队列只要不为空就不会阻塞，延时队列还需要看时间是否到期
 * 阻塞队列只需要在添加元素的时候唤醒即可，而延时队列还需要在任务时间到了的时候自动唤醒
 *
 * 从继承体系可以看到，DelayQueue实现了BlockingQueue，所以它是一个阻塞队列。
 * 另外，DelayQueue还组合了一个叫做Delayed的接口，DelayQueue中存储的所有元素必须实现Delayed接口。
 * 从属性我们可以知道，延时队列主要使用优先级队列来实现，并辅以重入锁和条件来控制并发安全。
 * <p>
 * 入队方法比较简单：
 * <p>
 * （1）加锁；
 * <p>
 * （2）添加元素到优先级队列中；
 * <p>
 * （3）如果添加的元素是堆顶元素，就把leader置为空，并唤醒等待在条件available上的线程；
 * <p>
 * （4）解锁；
 * <p>
 * <p>
 * poll()方法比较简单：
 * <p>
 * （1）加锁；
 * <p>
 * （2）检查第一个元素，如果为空或者还没到期，就返回null；
 * <p>
 * （3）如果第一个元素到期了就调用优先级队列的poll()弹出第一个元素；
 * <p>
 * （4）解锁。
 * <p>
 * take()方法稍微要复杂一些：
 * <p>
 * （1）加锁；
 * <p>
 * （2）判断堆顶元素是否为空，为空的话直接阻塞等待 入队唤醒；
 * <p>
 * （3）判断堆顶元素是否到期，到期了直接调用优先级队列的poll()弹出元素；
 * <p>
 * （4）没到期，再判断前面是否有其它线程在等待，有则直接等待；
 * <p>
 * （5）前面没有其它线程在等待，则把自己当作第一个线程等待delay时间后唤醒，再尝试获取元素；
 * <p>
 * （6）获取到元素之后如果堆顶不为空再唤醒下一个等待的线程；
 * <p>
 * （7）解锁；
 */
public class DelayQueueTest {
    public static void main(String[] args) {
        DelayQueue<Message> queue = new DelayQueue<>();

        long now = System.currentTimeMillis();

        // 启动一个线程从队列中取元素
        new Thread(() -> {
            while (true) {
                try {
                    // 将依次打印1000，2000，5000，7000，8000
                    System.out.println(queue.take().deadline - now);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // 添加5个元素到队列中
        queue.add(new Message(now + 5000));
        queue.add(new Message(now + 8000));
        queue.add(new Message(now + 2000));
        queue.add(new Message(now + 1000));
        queue.add(new Message(now + 7000));
    }

}


class Message implements Delayed {
    long deadline;

    public Message(long deadline) {
        this.deadline = deadline;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return deadline - System.currentTimeMillis();
    }

    @Override
    public int compareTo(Delayed o) {
        return (int) (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return String.valueOf(deadline);
    }
}
