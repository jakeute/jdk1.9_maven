package analyse.util.concurrent;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * （1）Executor，线程池顶级接口；
 * （2）ExecutorService，线程池次级接口，对Executor做了一些扩展，增加一些功能；
 * （3）ScheduledExecutorService，对ExecutorService做了一些扩展，增加一些定时任务相关的功能；
 * （4）AbstractExecutorService，抽象类，运用模板方法设计模式实现了一部分方法；
 * （5）ThreadPoolExecutor，普通线程池类，这也是我们通常所说的线程池，包含最基本的一些线程池操作相关的方法实现；
 * （6）ScheduledThreadPoolExecutor，ThreadPoolExecutor子类，定时任务线程池类，用于实现定时任务相关功能；
 * （7）ForkJoinPool，新型线程池类，java7中新增的线程池类，基于工作窃取理论实现，运用于大任务拆小任务、任务无限多的场景；
 * （8）Executors，线程池工具类，定义了一些快速实现线程池的方法（谨慎使用）；
 * Executors.newCachedThreadPool();
 * Executors.newFixedThreadPool();
 * Executors.newSingleThreadExecutor();
 * Executors.newSingleThreadScheduledExecutor();
 * Executors.newScheduledThreadPool()
 */
public class ExecutorTest {

}

/**
 * 简单实现一个线程
 * 流程：
 * 如果活动线程数目小于核心线程数目 则增加一个核心线程去执行任务
 * 如果核心线程数目已满，增加到任务队列，活动线程会从中取得任务执行
 * 如果任务队列满了后，则增加活动线程数目，去执行任务
 * 如果线程数目达到最大线程数，则执行应对策略
 */
class MyThreadPoolExecutor implements Executor {

    //核心线程数目
    int coreSize;
    //最大线程数目
    int maxSize;
    //线程池名称
    String poolName;
    //当前线程数目
    AtomicInteger runningCount = new AtomicInteger();
    //拒绝策略
    RejectPolicy rejectPolicy = new RejectPolicy();
    //任务队列
    BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(15);
    //线程名次
    AtomicInteger sequence = new AtomicInteger();

    @Override
    public void execute(Runnable task) {
        int count = runningCount.get();
        //当前活动线程数目小于核心线程数目 则增加一个线程去执行任务
        if (count < coreSize) {
            //如果增加核心线程成功则退出 否则继续向下运行
            if (addWorker(task, true)) {
                return;
            }
        }
        //如果上面失败则入队
        //当核心线程数目满了后，增加到任务队列，活动线程会从中取得任务执行
        if (!taskQueue.offer(task)) {
            //如果入队失败
            //当任务队列满了后，则增加线程数目，去执行任务
            if (!addWorker(task, false)) {
                //则执行应对策略
                rejectPolicy.reject(task, this);
            }
        }
    }

    private boolean addWorker(Runnable newTask, boolean core) {
        // 自旋判断是不是真的可以创建一个线程
        for (; ; ) {
            // 正在运行的线程数
            int count = runningCount.get();
            // 核心线程还是非核心线程
            int max = core ? coreSize : maxSize;
            // 不满足创建线程的条件，直接返回false
            if (count >= max) {
                return false;
            }
            // 修改runningCount成功，可以创建线程
            //否则则说明已有其他线程去增加了活动线程的数目，需要重新判断
            if (runningCount.compareAndSet(count, count + 1)) {
                // 线程的名字 每一个都是独一无二的sequence
                String threadName = (core ? "core_" : "nocore_") + poolName + sequence.incrementAndGet();
                // 创建线程并启动
                new Thread(() -> {
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    // 运行的任务
                    Runnable task = newTask;
                    // 不断从任务队列中取任务执行，如果取出来的任务为null，则跳出循环，线程也就结束了
                    while (task != null || (task = getTask()) != null) {
                        try {
                            // 执行任务
                            task.run();
                        } finally {
                            // 任务执行完成，置为空
                            task = null;
                        }
                    }
                }, threadName).start();

                break;
            }
        }

        return true;
    }

    private Runnable getTask() {
        try {
            // take()方法会一直阻塞直到取到任务为止
            return taskQueue.take();
        } catch (InterruptedException e) {
            // 线程中断了，返回null可以结束当前线程
            // 当前线程都要结束了，理应要把runningCount的数量减一
            runningCount.decrementAndGet();
            return null;
        }
    }

    public MyThreadPoolExecutor(int coreSize, int maxSize, String poolName, RejectPolicy rejectPolicy) {
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.poolName = poolName;
        this.rejectPolicy = rejectPolicy;
    }
}

class RejectPolicy {
    public void reject(Runnable task, MyThreadPoolExecutor myThreadPoolExecutor) {
        // do nothing
        System.out.println("discard one task");
    }

    <T> Future<T> get(Callable<T> task) {
        return new FutureTask<>(task);
    }

}
