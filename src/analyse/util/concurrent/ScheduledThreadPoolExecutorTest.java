package analyse.util.concurrent;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在ThreadPoolExecutor的基础上添加了定时任务
 * <p>
 * 定时任务总体分为四种：
 * <p>
 * （1）未来执行一次的任务，无返回值；
 * schedule
 * （2）未来执行一次的任务，有返回值；
 * schedule
 * （3）未来按固定频率重复执行的任务； 是以上一次任务的开始时间为间隔的，并且当任务执行时间大于设置的间隔时间时，真正间隔的时间由任务执行时间为准
 * scheduleAtFixedRate
 * （4）未来按固定延时重复执行的任务；  是以上一次任务的结束时间为间隔的
 * scheduleWithFixedDelay
 * 原理：
 * （1）指定某个时刻执行任务，是通过延时队列的特性来解决的，
 * 该队列是个堆，时间最小的排在前面（普通任务等待时间是0），当take取出的任务还没到时间就会阻塞
 * （2）重复执行，是通过在任务执行后再次把任务加入到队列中来解决的。
 */
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(5);
        long start = System.currentTimeMillis();
        Runnable runnable = new Runnable() {
            AtomicInteger i = new AtomicInteger(0);

            @Override
            public void run() {
                long stop = System.currentTimeMillis();
                System.out.println(i.get());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i.addAndGet(1) + "  " + (stop - start));
            }
        };
        threadPoolExecutor.scheduleAtFixedRate(runnable, 0, 10, TimeUnit.SECONDS);

    }

    @Test
    public void test() {


    }
}
