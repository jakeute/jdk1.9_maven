package mjava.util.concurrent;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue 也是一个队列来的，但它的特别之处在于它内部没有容器，一个生产线程，当它生产产品（即put的时候），
 * 如果当前没有人想要消费产品(即当前没有线程执行take)，此生产线程必须阻塞，等待一个消费线程调用take操作，
 * take操作将会唤醒该生产线程，同时消费线程会获取生产线程的产品（即数据传递），同样当take没有数据是也会阻塞
 * put线程与put线程必需一一配对，可以认为这是一种线程与线程间一对一传递消息的模型。
 * <p>
 * 1.	SynchronousQueue没有容量。与其他BlockingQueue不同，SynchronousQueue是一个不存储元素的BlockingQueue。
 * 每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。
 * 2.	因为没有容量，所以对应 peek, contains, clear, isEmpty … 等方法其实是无效的。例如clear是不执行任何操作的
 * ，contains始终返回false,peek始终返回null。
 * 3.	SynchronousQueue分为公平和非公平，默认情况下采用非公平性访问策略，当然也可以通过构造函数来设置为公平性访问策略（为true即可）。
 *
 *      *iterator() 永远返回空，因为里面没东西。
 *     * peek() 永远返回null。
 *     * put() 往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走。
 *     * offer() 往queue里放一个element后立即返回，如果碰巧这个element被另一个thread取走了，offer方法返回true，认为offer成功；否则清空queue返回false。
 *     * offer(2000, TimeUnit.SECONDS) 往queue里放一个element但是等待指定的时间后才返回，返回的逻辑和offer()方法一样。
 *     * take() 取出并且remove掉queue里的element（认为是在queue里的。。。），取不到东西他会一直等。
 *     * poll() 取出并且remove掉queue里的element（认为是在queue里的。。。），只有到碰巧另外一个线程正在往queue里offer数据或者put数据的时候，该方法才会取到东西。否则立即返回null。
 *     * poll(2000, TimeUnit.SECONDS) 等待指定的时间然后取出并且remove掉queue里的element,其实就是再等其他的thread来往里塞。
 *     * isEmpty()永远是true。
 *     * remainingCapacity() 永远是0。
 *     * remove()和removeAll() 永远是false。
 *
 */
public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

        Thread putThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("put thread start");
                try {
                    queue.put(1);
                } catch (InterruptedException e) {
                }
                System.out.println("put thread end");
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("take thread start");
                try {
                    System.out.println("take from putThread: " + queue.take());
                } catch (InterruptedException e) {
                }
                System.out.println("take thread end");
            }
        });

        takeThread.start();

        Thread.sleep(5000);
        putThread.start();
    }

    @Test
    public void test(){
        SynchronousQueue<String> query = new SynchronousQueue<>();
        System.out.println(query.offer("xxxx"));
        System.out.println(query.poll());
    }
}
