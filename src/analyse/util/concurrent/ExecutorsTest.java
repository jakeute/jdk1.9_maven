package analyse.util.concurrent;

import org.junit.Test;

/**
 * Exectors都是对threadpool进行参数组合
 */
public class ExecutorsTest {
    public static void main(String[] args) {

    }

    @Test
    public void test1() {
//        Executors.newFixedThreadPool();

//        new ThreadPoolExecutor(nThreads, nThreads,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>());
        //创建一个核心线程数与最大线程数相等的线程池，也就是说每来一个任务都会创建一个核心线程
        //当线程数量达到核心线程数目时，直接入队，且队列长度无限

    }

    @Test
    public void test2() {
//        Executors.newCachedThreadPool();
//       new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>());
        //因为核心为0，所以一来任务就入队
        //而使用SynchronousQueue的目的就是保证“对于提交的任务，如果有空闲线程正在 take，则使用空闲线程获取任务；否则返回null
        //从而新建一个非核心线程线程来处理任务，非核心线程在空闲60s后消亡
    }

    @Test
    public void test3() {
//        Executors.newSingleThreadExecutor();
//        new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>())
        //该线程池只有一个线程，当有任务时创造线程或者入队，没有任务时线程消亡
    }

    @Test
    public void test4() {
//        Executors.newScheduledThreadPool()
        //就是创造一个ScheduledThreadPool
    }

    @Test
    public void test5() {
//        Executors.newSingleThreadScheduledExecutor();
        //单个线程的ScheduledThreadPool
    }


}
