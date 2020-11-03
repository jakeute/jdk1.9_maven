package analyse.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 此类是带戳的
 * 首先，使用版本号控制；
 * <p>
 * 其次，不重复使用节点（Pair）的引用，每次都新建一个新的Pair来作为CAS比较的对象，而不是复用旧的（Pair就只有一个引用和int）；
 * <p>
 * 最后，外部传入元素值及版本号，而不是节点（Pair）的引用。
 * 从此避免ABA问题
 * <p>
 * compareAndSet：比较两个期望值是否相等，如果不相等返回false
 * 再次比较修改值是否和原先的值相等 相等返回true
 * 最后尝试 比较修改 可能返回false
 */
public class AtomicStampedReferenceTest {
    public static void main(String[] args) {
        AtomicStampedReference<String> p = new AtomicStampedReference<String>("p", 0);

        String reference = p.getReference();
        int stamp = p.getStamp();

        p.compareAndSet(reference, "ww", stamp, stamp + 1);


    }
}

/**
 * ABA问题
 */
class ABATest {

    static class Stack {
        // 将top放在原子类中
        private AtomicReference<Node> top = new AtomicReference<>();

        // 栈中节点信息
        static class Node {
            int value;
            Node next;

            public Node(int value) {
                this.value = value;
            }
        }

        // 出栈操作
        public Node pop() {
            for (; ; ) {
                // 获取栈顶节点
                Node t = top.get();
                if (t == null) {
                    return null;
                }
                // 栈顶下一个节点
                Node next = t.next;
                // CAS更新top指向其next节点
                if (top.compareAndSet(t, next)) {
                    // 把栈顶元素弹出，应该把next清空防止外面直接操作栈
                    t.next = null;
                    return t;
                }
            }
        }

        // 入栈操作
        public void push(Node node) {
            for (; ; ) {
                // 获取栈顶节点
                Node next = top.get();
                // 设置栈顶节点为新节点的next节点
                node.next = next;
                // CAS更新top指向新节点
                if (top.compareAndSet(next, node)) {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
// 假如，我们初始化栈结构为 top->1->2->3，然后有两个线程分别做如下操作：
//（1）线程1执行pop()出栈操作，但是执行到 if(top.compareAndSet(t,next)){这行之前暂停了，所以此时节点1并未出栈；
//（2）线程2执行pop()出栈操作弹出节点1，此时栈变为 top->2->3；
//（3）线程2执行pop()出栈操作弹出节点2，此时栈变为 top->3；
//（4）线程2执行push()入栈操作添加节点1，此时栈变为 top->1->3；
//（5）线程1恢复执行，比较节点1的引用并没有改变，执行CAS成功，此时栈变为 top->2；
//  What？点解变成 top->2 了？不是应该变成 top->3 吗？
//  那是因为线程1在第一步保存的next是节点2，所以它执行CAS成功后top节点就指向了节点2了。

        Stack stack = new Stack();
        stack.push(new Stack.Node(3));
        stack.push(new Stack.Node(2));
        stack.push(new Stack.Node(1));

        new Thread(() -> {
            // 线程1出栈一个元素
            stack.pop();
        }).start();

        new Thread(() -> {
            // 线程2出栈两个元素
            Stack.Node A = stack.pop();
            Stack.Node B = stack.pop();
            // 线程2又把A入栈了
            stack.push(A);
        }).start();


    }
}

