package mjava.util.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList是ArrayList的线程安全版本，内部也是通过数组实现，每次对数组的修改都完全拷贝一份新的数组来修改，
 * 修改完了再替换掉老数组，这样保证了只阻塞写操作，不阻塞读操作，实现读写分离。
 * <p>
 * （1）CopyOnWriteArrayList使用ReentrantLock重入锁加锁，保证线程安全；
 * <p>
 * （2）CopyOnWriteArrayList的写操作都要先拷贝一份新数组，在新数组中做修改，修改完了再用新数组替换老数组，所以空间复杂度是O(n)，性能比较低下；
 * <p>
 * （3）CopyOnWriteArrayList的读操作支持随机访问，时间复杂度为O(1)；
 * <p>
 * （4）CopyOnWriteArrayList采用读写分离的思想，读操作不加锁，写操作加锁，且写操作占用较大内存空间，所以适用于读多写少的场合；
 * 但是在写的时候，可进行读操作，不如读写锁安全
 * <p>
 * （5）CopyOnWriteArrayList只保证最终一致性，不保证实时一致性
 */
public class CopyOnWriteArrayListTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        list.add("4555");
        //（1）加锁；
        //
        //（2）获取元素数组；
        //
        //（3）新建一个数组，大小为原数组长度加1，并把原数组元素拷贝到新数组；
        //
        //（4）把新添加的元素放到新数组的末尾；
        //
        //（5）把新数组赋值给当前对象的array属性，覆盖原数组；
        //
        //（6）解锁；
        System.out.println(list.get(0));
    }
}
