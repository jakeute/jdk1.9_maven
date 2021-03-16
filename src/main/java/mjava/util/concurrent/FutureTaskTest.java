package mjava.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 *
 * <p>
 * <p>
 * 众所周知，Thread为创建线程的主体，但是Thread的run没有返回值，我们无法通过Thread.run去获取线程执行后的结果
 * Thread.run默认是调用target.run，我们可以从target这个Runnable的实现类入手，如果target在执行玩run后能将结果
 * 保存在类中，我们只需要在线程结束后去target中寻找即可
 * 所以我们现在的目标是寻找一个Runnable的实现类target，它能在run()结束后保存结果。而且我们还需要创造一个接口 此接口能够返回result
 * 并且此接口可以让用户自定义内容
 * <p>
 * 所以我们创造了 Callable 此接口的call()可以返回result 而我们可以编写实现类放入逻辑代码
 * FutureTask 为Runnable的实现类，此类需要一个Callable实现类获取result，run()方法会调用Callable.call()并且保存结果
 * <p>
 * 流程：
 * Thread.run --> FutureTask.run --> Callable.call --> 保存result在FutureTask中 --> FutureTask.get
 * <p>
 * 而FutureTask可以提炼出另一个接口 Future 去获取操作结果
 * public boolean cancel（boolean mayInterrupt）：用于停止任务。如果尚未启动，它将停止任务。如果已启动，则仅在mayInterrupt为true时才会中断任务。
 * public Object get（）抛出InterruptedException，ExecutionException：用于获取任务的结果。如果任务完成，它将立即返回结果，否则将阻塞等待任务完成，然后返回结果。
 * public boolean isDone（）：如果任务完成，则返回true，否则返回false
 * public void run() 执行Callable 但是执行任务的只能有一个线程，其他的线程会直接返回.当执行完成后会唤醒因调用get而阻塞的线程
 * 总体来说在内部 Callable代替 Runnable 实现逻辑代码，而FutureTask包装Runnable 适应Thread，Future接口去获取结果
 * 而且Callable能抛出异常，而Runnable只能将异常转化为运行时异常抛出，虽说结果都是一样的
 * 但是runnable能被多个线程执行，callable只能被一个线程去执行，其他的线程直接去获取结果就可
 */
public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        // 新建一个固定5个线程的线程池
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);
//
//        List<Future<Integer>> futureList = new ArrayList<>();
//        // 提交5个任务，分别返回0、1、2、3、4
//        for (int i = 0; i < 5; i++) {
//            int num = i;
//
//            // 任务执行的结果用Future包装
//            Future<Integer> future = threadPool.submit(() -> {
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("return: " + num);
//                // 返回值
//                return num;
//            });
//
//            // 把future添加到list中
//            futureList.add(future);
//        }
//
//        // 任务全部提交完再从future中get返回值，并做累加
//        int sum = 0;
//        for (Future<Integer> future : futureList) {
//            sum += future.get();
//        }
//
//        System.out.println("sum=" + sum);
        FutureTask<String> stringFutureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {

                System.out.println(Thread.currentThread());

                return "ok";
            }
        });

        new Thread(stringFutureTask).start();
        new Thread(stringFutureTask).start();

        System.out.println(stringFutureTask.get());

    }

}


