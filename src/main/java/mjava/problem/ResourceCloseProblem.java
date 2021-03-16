package mjava.problem;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;


public class ResourceCloseProblem {
    /**
     * 为了确保外部资源一定要被关闭，通常关闭代码被写入finally代码块中，
     * 当然我们还必须注意到关闭资源时可能抛出的异常，于是有了下面的经典代码：
     *
     * 在这个例子中，如果read和close方法都抛出异常，那么最终会在finally代码块抛出异常，
     * 从catch代码块里面抛出的那个异常被抑制住了。这会让我们无法找到正确的异常点
     */
    @Test
    public void test1() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("se.txt");
            System.out.println(inputStream.read());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 确实，在JDK7以前，Java没有自动关闭外部资源的语法特性，直到JDK7中新增了try-with-resource语法，才实现了这一功能。
     * <p>
     * 那什么是try-with-resource呢？简而言之，当一个外部资源的句柄对象（比如FileInputStream对象）
     * 实现了AutoCloseable/Closeable接口，那么就可以将上面的板式代码简化为如下形式：
     *
     * try-with-resource并不是JVM虚拟机的新增功能，只是JDK实现了一个语法糖，
     * 当你将上面代码反编译后会发现，其实对JVM虚拟机而言，它看到的依然是之前的写法：
     * try {
     *         FileInputStream inputStream = new FileInputStream(new File("test"));
     *         Throwable var2 = null;
     *
     *         try {
     *             System.out.println(inputStream.read());
     *         } catch (Throwable var12) {
     *             var2 = var12;
     *             throw var12;
     *         } finally {
     *             if (inputStream != null) {
     *                 if (var2 != null) {
     *                     try {
     *                         inputStream.close();
     *                     } catch (Throwable var11) {
     *                         var2.addSuppressed(var11);
     *                     }
     *                 } else {
     *                     inputStream.close();
     *                 }
     *             }
     *
     *         }
     *
     *     } catch (IOException var14) {
     *         throw new RuntimeException(var14.getMessage(), var14);
     *     }
     * 通过反编译的代码，大家可能注意到代码中有一处对异常的特殊处理：
     * var2.addSuppressed(var11);
     * 这是try-with-resource语法涉及的另外一个知识点，叫做异常抑制。当对外部资源进行处理（例如读或写）时，如果遭遇了异常，
     * 并且在随后的关闭外部资源过程中，又遭遇了异常，那么你catch到的将会是对外部资源进行处理时遭遇的异常，
     * 关闭资源时遭遇的异常将被“抑制”但不是丢弃，通过异常的getSuppressed方法，可以提取出被抑制的异常。
     * 这能让我们找的正确的异常点
     *
     * 当try（）中创建多个资源时用 ; 分隔，资源的 close 方法调用顺序与它们的创建顺序相反。
     * 注意：try-with-resources语句也可以像普通的try语句一样，有catch和finally代码块。
     * 在try-with-resources语句中，任何的catch和finally代码块都在所有被声明的资源被关闭后执行。
     */
    @Test
    public void test2() {
        try (FileInputStream inputStream = new FileInputStream("se.txt")) {
            System.out.println(inputStream.read());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
