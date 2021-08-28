package mjava.lang;

/**
 * 每个Thread对象内部都维护了一个ThreadLocalMap这样一个ThreadLocal的Map -- threadLocals，key是ThreadLocal value是一个Object。
 * <p>
 * 当调用ThreadLocal.set(obj) 方法时，会把当前的ThreadLocal对象当作key，obj当作value 存入当前线程的threadLocals中
 * 当调用ThreadLocal.get 方法时，会从当前线程的threadLocals中取出当前的ThreadLocal对象对应的value   如果没有存储 则会value = null存储到threadlocals中
 * <p>
 * 事实上，从本质来讲，就是每个线程都维护了一个map，而这个map的key就是threadLocal，而值就是我们set的那个值，每次线程在get的时候，都从自己的变量中取值，既然从自己的变量中取值，
 * 那肯定就不存在线程安全问题，总体来讲，ThreadLocal这个变量的状态根本没有发生变化，他仅仅是充当一个key的角色，另外提供给每一个线程一个初始值。如果允许的话，我们自己就能实现一个这样的功能，
 * 只不过恰好JDK就已经帮我们做了这个事情。当然每个线程也可以有多个 key
 *
 * 如果threadLocal外部强引用被置为null(threadLocalInstance=null)的话，threadLocal实例就没有一条强引用链路可达，
 * 很显然在gc(垃圾回收)的时候势必会被回收，因此entry就存在key为null的情况，无法通过一个Key为null去访问到该entry的value。
 * 同时，就存在了这样一条引用链：threadRef->currentThread->threadLocalMap->entry->valueRef->valueMemory，
 * 导致在垃圾回收的时候进行可达性分析的时候,value可达从而不会被回收掉，但是该value永远不能被访问到，这样就存在了内存泄漏。
 *

 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<String> one = new ThreadLocal<>();
        ThreadLocal<Integer> two = new ThreadLocal<>();


        new Thread(new Runnable() {
            @Override
            public void run() {
                one.set("jib");
                two.set(65);


                System.out.println(".." + one.get());
                System.out.println(".." + two.get());
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                one.set("axb");
                two.set(666);


                System.out.println("**" + one.get());
                System.out.println("**" + two.get());
            }
        }).start();

    }
}
