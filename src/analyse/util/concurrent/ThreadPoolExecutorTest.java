package analyse.util.concurrent;

import java.util.concurrent.*;

/**
 * ThreadPoolExecutor有四个构造方法，其中前三个最终都是调用最后一个，它有7个参数，分别为corePoolSize、maximumPoolSize、keepAliveTime、
 * unit、workQueue、threadFactory、handler。
 * <p>
 * corePoolSize 核心线程数。
 * 当正在运行的线程数小于核心线程数时，即使有其他工作线程处于空闲状态，来一个任务依旧创建一个核心线程；
 * 当正在运行的线程数大于或等于核心线程数时，任务来了先不创建线程而是丢到任务队列中。
 * <p>
 * maximumPoolSize 最大线程数。
 * 当任务队列满了时，来一个任务才创建一个非核心线程，但不能超过最大线程数。
 * <p>
 * keepAliveTime + unit 线程保持空闲时间及单位。
 * 默认情况下，此两参数仅当正在运行的线程数大于核心线程数时才有效，即只针对非核心线程。
 * 但是，如果allowCoreThreadTimeOut被设置成了true，针对核心线程也有效。
 * 即当任务队列为空时，线程保持多久才会销毁，内部主要是通过阻塞队列带超时的poll(timeout, unit)方法实现的。
 * <p>
 * workQueue 任务队列。
 * 当正在运行的线程数大于或等于核心线程数时，任务来了是先进入任务队列中的。
 * 这个队列必须是阻塞队列，所以像ConcurrentLinkedQueue就不能作为参数，因为它虽然是并发安全的队列，但是它不是阻塞队列。
 * <p>
 * threadFactory  线程工厂
 * 默认使用的是Executors工具类中的DefaultThreadFactory类，这个类有个缺点，创建的线程的名称是自动生成的，无法自定义以区分不同的线程池，且它们都是非守护线程。
 * <p>
 * handler 拒绝策略。
 * 拒绝策略表示当任务队列满了且线程数也达到最大了，这时候再新加任务，线程池已经无法承受了，这些新来的任务应该按什么逻辑来处理。
 * 常用的拒绝策略有丢弃当前任务、丢弃最老的任务、抛出异常、调用者自己处理等待。
 * 默认的拒绝策略是抛出异常，即线程池无法承载了，调用者再往里面添加任务会抛出异常。
 * 默认的拒绝策略虽然比较简单粗暴，但是相对于丢弃任务策略明显要好很多，最起码调用者自己可以捕获这个异常再进行二次处理。
 * <p>
 * 钩子方法
 * 该类提供了在每个任务执行之前和之后调用的protected覆盖的beforeExecute(Thread, Runnable)和afterExecute(Runnable, Throwable)方法。
 * 这些可以用来操纵执行环境; 例如，重新初始化ThreadLocals，收集统计信息或添加日志条目。 另外，方法terminated()可以被覆盖，以执行执行程序完全终止后需要执行的任何特殊处理。
 * <p>
 * 线程池内部：
 * （1）线程池的状态和工作线程的数量共同保存在控制变量ctl中，类似于AQS中的state变量，不过这里是直接使用的AtomicInteger，这里换成unsafe+volatile也是可以的；
 * （2）ctl的高三位保存运行状态，低29位保存工作线程的数量，也就是说线程的数量最多只能有(2^29-1)个，也就是上面的CAPACITY；
 * （3）线程池的状态一共有五种，分别是RUNNING、SHUTDOWN、STOP、TIDYING、TERMINATED；
 * （4）RUNNING，表示可接受新任务，且可执行队列中的任务；
 * （5）SHUTDOWN，表示不接受新任务，但可执行队列中的任务；
 * （6）STOP，表示不接受新任务，且不再执行队列中的任务，且中断正在执行的任务；
 * （7）TIDYING，所有任务已经中止，且工作线程数量为0，最后变迁到这个状态的线程将要执行terminated()钩子方法，只会有一个线程执行这个方法；
 * （8）TERMINATED，中止状态，已经执行完terminated()钩子方法
 * <p>
 * （1）新建线程池时，它的初始状态为RUNNING，这个在上面定义ctl的时候可以看到；
 * （2）RUNNING->SHUTDOWN，执行shutdown()方法时；
 * （3）RUNNING->STOP，执行shutdownNow()方法时；
 * （4）SHUTDOWN->STOP，执行shutdownNow()方法时
 * （5）STOP->TIDYING，执行了shutdown()或者shutdownNow()后，所有任务已中止，且工作线程数量为0时，此时会执行terminated()方法；
 * （6）TIDYING->TERMINATED，执行完terminated()方法后；
 * <p>
 * 常用的方法：
 * 关闭线程池，不再接受新任务，但已经提交的任务会执行完成
 * void shutdown();
 * <p>
 * 立即关闭线程池，尝试停止正在运行的任务，未执行的任务将不再执行
 * 被迫停止及未执行的任务将以列表的形式返回
 * List<Runnable> shutdownNow();
 * <p>
 * 执行有返回值的任务，任务的返回值为task.call()的结果
 * <T> Future<T> submit(Callable<T> task);
 * <p>
 * 执行无返回值的任务
 * void execute(Runnable task)
 * <p>
 * 执行有返回值的任务，任务的返回值为null
 * 当然只有当任务执行完成了调用get()时才会返回
 * Future<?> submit(Runnable task);
 * <p>
 * 批量执行任务，只有当这些任务都完成了这个方法才会返回
 * <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
 * throws InterruptedException;
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        // 新建一个线程池
        // 核心数量为5，最大数量为10，空闲时间为1秒，队列长度为5，拒绝策略打印一句话
        ExecutorService threadPool = new ThreadPoolExecutor(5, 10,
                1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5),
                Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(currentThreadName() + ", discard task");
            }
        });

        // 提交20个任务，注意观察num
        for (int i = 0; i < 20; i++) {
            int num = i;
            threadPool.execute(() -> {
                try {
                    System.out.println(currentThreadName() + ", " + num + " running, " + System.currentTimeMillis());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private static String currentThreadName() {
        return Thread.currentThread().getName();
    }

}
