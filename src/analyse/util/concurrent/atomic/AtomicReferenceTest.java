package analyse.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子更新引用类型
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {


    }
}

class State {
    private final Thread thread;
    private final boolean accessedByMultipleThreads;

    public State(Thread thread, boolean accessedByMultipleThreads) {
        super();
        this.thread = thread;
        this.accessedByMultipleThreads = accessedByMultipleThreads;
    }

    public State() {
        super();
        this.thread = null;
        this.accessedByMultipleThreads = false;
    }

    public State update() {
        if (accessedByMultipleThreads) {
            return this;
        }
        if (thread == null) {
            return new State(Thread.currentThread()
                    , accessedByMultipleThreads);
        }
        if (thread != Thread.currentThread()) {
            return new State(null, true);
        }
        return this;
    }

    public boolean isAccessedByMultipleThreads() {
        return accessedByMultipleThreads;
    }
}

class UpdateStateNotThreadSafe {
    private volatile State state = new State();

    public void update() {
        state = state.update();
    }

    public State getState() {
        return state;
    }


    /**
     * 会出现 两个线程同时进入update方法之中，然后返回同一个对象
     *
     * @throws InterruptedException
     */

    public static void main(String[] args) throws InterruptedException {
        final UpdateStateNotThreadSafe object = new UpdateStateNotThreadSafe();
        Thread first = new Thread(object::update);
        Thread second = new Thread(object::update);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(object.getState().isAccessedByMultipleThreads());
    }
}

class UpdateStateThreadSafe {
    private volatile AtomicReference<State> state = new AtomicReference<>();

    public void update() {
        State current = state.get();
        State newValue = current.update();
        while (!state.compareAndSet(current, newValue)) {
            current = state.get();
            newValue = current.update();
        }
    }

    public State getState() {
        return state.get();
    }


    /**
     * state 变量使用了AtomicReference。要更新 state 首先需要获取当前值。然后，计算新值，并尝试使用
     * compareAndSet 更新 AtomicReference。如果更新成功，则处理完毕。如果没有成功，需要在再次获取当前值，并行重新计算新值。
     * 然后，再次尝试使用 compareAndSet 更新 AtomicReference。使用 while 循环是因为 compareAndSet 可能会多次失败。
     *
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        final UpdateStateThreadSafe object = new UpdateStateThreadSafe();
        Thread first = new Thread(object::update);
        Thread second = new Thread(object::update);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(object.getState().isAccessedByMultipleThreads());
    }

}