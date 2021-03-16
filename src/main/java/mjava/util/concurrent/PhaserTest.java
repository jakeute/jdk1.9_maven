package mjava.util.concurrent;

import org.junit.Test;

import java.util.concurrent.Phaser;

/**
 * Phaser，翻译为阶段，它适用于这样一种场景，一个大任务可以分为多个阶段完成，且每个阶段的任务可以多个线程并发执行，但是必须上一个阶段的任务都完成了才可以执行下一个阶段的任务。
 * <p>
 * 这种场景虽然使用CyclicBarrier或者CountryDownLatch也可以实现，但是要复杂的多。首先，具体需要多少个阶段是可能会变的，其次，每个阶段的任务数也可能会变的。
 * 相比于CyclicBarrier和CountDownLatch，Phaser更加灵活更加方便。一个可重用的同步工具，可以实现提CyclicBarrier和CountDownLatch的功能，但是还有其他高级的功能。
 * <p>
 * <p>
 * 对于CountDownLatch而言，有2个重要的方法，一个是await()方法，可以使线程进入等待状态，在Phaser中，与之对应的方法是awaitAdvance(int n)。CountDownLatch中另一个重要的方法是countDown()，
 * 使计数器减一，当计数器为0时所有等待的线程开始执行，在Phaser中，与之对应的方法是arrive()。
 * <p>
 * 用Phaser替代CyclicBarrier更简单，CyclicBarrier的await()方法可以直接用Phaser的arriveAndAwaitAdvance()方法替代。
 * <p>
 * 内部状态：
 * state的高32位存储当前阶段phase，中16位存储当前阶段参与者（任务）的数量parties，低16位存储未完成参与者的数量unarrived；
 * <p>
 * arriveAndAwaitAdvance
 * （1）修改state中unarrived部分的值减1；
 * <p>
 * （2）如果不是最后一个到达的，则调用internalAwaitAdvance()方法自旋或排队等待；
 * <p>
 * （3）如果是最后一个到达的，则调用onAdvance()方法，然后修改state的值为下一阶段对应的值，并唤醒其它等待的线程；
 * <p>
 * （4）返回下一阶段的值；
 */
public class PhaserTest {
    public static final int PARTIES = 3;
    public static final int PHASES = 4;

    public static void main(String[] args) {

        Phaser phaser = new Phaser(PARTIES) {
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {

                System.out.println("=======phase: " + phase + " finished=============");
                return super.onAdvance(phase, registeredParties);
            }
        };

        for (int i = 0; i < PARTIES; i++) {
            new Thread(() -> {
                for (int j = 0; j < PHASES; j++) {
                    System.out.println(String.format("%s: phase: %d", Thread.currentThread().getName(), j));
                    phaser.arriveAndAwaitAdvance();
                }
            }, "Thread " + i).start();
        }

    }


    @Test
    public void showCountDownLatch() {
        final Phaser phaser = new Phaser(5);

        for (int i = 0; i < 5; i++) {
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(finalI);
                    phaser.arrive();   //arrive的次数与parties相同
                }
            }).start();
        }
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println("结束了");
    }
}
