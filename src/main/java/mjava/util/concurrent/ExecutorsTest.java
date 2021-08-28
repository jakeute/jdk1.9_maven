package mjava.util.concurrent;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors都是对threadpool进行参数组合
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
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            System.out.println("hello");
        });
//       new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//                60L, TimeUnit.SECONDS,
//                new SynchronousQueue<Runnable>());
        //因为核心为0，所以一来任务就入队
        //而使用SynchronousQueue的目的就是保证“对于提交的任务，如果有空闲线程正在take，则使用空闲线程获取任务；否则返回null
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
        int[] array = new int[]{5,4,3,2,4,5,2,3,1,10};
        sort(array,0, array.length-1);
        System.out.println(Arrays.toString(array));
    }

    void sort(int[] arrrays,int left,int right){
        if(left>=right)
            return;
        int i= left,j=right;
        int num=arrrays[left];

        while (i!=j){
            while (i<j-1 && arrrays[i]<=num){
                i++;
            }
            while (j>i +1 && arrrays[j]>=num){
                j--;
            }
            if(i<j){
                swap(arrrays,i,j);
            }
        }


        sort(arrrays,left,i-1);
        sort(arrrays,i+1,right);
    }

    void swap(int[]arrays,int i,int j){
        int u= arrays[i];
        arrays[i]=arrays[j];
        arrays[j]=u;
    }
}
