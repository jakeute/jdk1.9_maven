package analyse.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer是Java中的定时器 它会开启另外一个非守护线程去执行任务
 * 只有调用cancel 才能停止定时器
 * <p>
 * 内部维护着按照nextExecutionTime排序的小头堆 ---任务队列
 * 其内部也维护着一个线程 此线程（while）一直执行任务队列中的任务，而当队列中没有任务的时候会等待
 */
public class TimerTest {
    static volatile boolean is = false;

    public static void main(String[] args) {
        //timer2();
        //timer2();
        //timer3();
        //timer4();
        //System.out.println(Thread.currentThread().getName()+"-end");

        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                is = true;
            }
        }.start();

        //专门开启一个线程监测
        new Thread() {
            @Override
            public void run() {
                long s = System.currentTimeMillis() + 3000;
                while (System.currentTimeMillis() < s && !is) ;
                if (is) {
                    System.out.println("ok");
                } else {
                    System.out.println("cao");
                }
            }
        }.start();

        //定时器检测
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (is) {
                    System.out.println("timer ok");
                    timer.cancel();
                }
            }
        }, 0, 300);

    }

    // 第一种方法：设定指定任务task在指定时间time后执行 schedule(TimerTask task, Date time)
    public static void timer1() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("-------任务1--------");

                System.out.println(Thread.currentThread().getName());

                System.out.println("-------任务1 end--------");
                //结束
                timer.cancel();
            }
        }, 2000);// 设定指定的时间time,此处为2000毫秒
    }

    // 第二种方法：设定指定任务task在指定延迟delay后进行 固定延迟peroid的执行
    // schedule(TimerTask task, long delay, long period)
    public static void timer2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 0;

            public void run() {
                i++;
                if (i == 10)
                    timer.cancel();
                System.out.println("-------任务2--------");

                System.out.println(Thread.currentThread().getName());

                System.out.println("-------任务2 end--------");
            }
        }, 1000, 500);//在1000毫秒后每500毫秒执行任务
    }

    // 第三种方法：设定指定任务task在指定延迟delay后进行固定频率peroid的执行。
    // scheduleAtFixedRate(TimerTask task, long delay, long period)
    public static void timer3() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, 1000, 2000);
    }

    // 第四种方法：安排指定的任务task在指定的时间firstTime开始进行重复的固定速率period执行．
    // Timer.scheduleAtFixedRate(TimerTask task,Date firstTime,long period)
    public static void timer4() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 12); // 控制时
        calendar.set(Calendar.MINUTE, 0);    // 控制分
        calendar.set(Calendar.SECOND, 0);    // 控制秒

        Date time = calendar.getTime();     // 得出执行任务的时间,此处为今天的12：00：00

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("-------设定要指定任务--------");
            }
        }, time, 1000 * 60 * 60 * 24);// 这里设定将延时每天固定执行
    }
}

