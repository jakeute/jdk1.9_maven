package mjava.lang;

/**
<<<<<<< HEAD
 *
 *
 * Throwable
 * 有两个重要的子类：Exception（异常）和 Error（错误），
 * 二者都是 Java 异常处理的重要子类，各自都包含大量子类。
 *
 * Error（错误）
 * 是程序无法处理的错误，表示运行应用程序中较严重问题。大多数错误与代码编写者执行的操作无关，
 * 而表示代码运行时 JVM（Java 虚拟机）出现的问题。例如，Java虚拟机运行错误（Virtual MachineError），
 * 当 JVM 不再有继续执行操作所需的内存资源时，将出现 OutOfMemoryError。
 * 这些异常发生时，Java虚拟机（JVM）一般会选择线程终止。这些错误表示故障发生于虚拟机自身、或者发生在虚拟机试图执行应用时，
 * 如Java虚拟机运行错误（Virtual MachineError）、类定义错误（NoClassDefFoundError）等。这些错误是不可查的，
 * 因为它们在应用程序的控制和处理能力之 外，而且绝大多数是程序运行时不允许出现的状况。对于设计合理的应用程序来说，
 * 即使确实发生了错误，本质上也不应该试图去处理它所引起的异常状况。在 Java中，错误通过Error的子类描述。
 *
 * Exception（异常）
 * 是程序本身可以处理的异常。Exception 类有一个重要的子类 RuntimeException。RuntimeException 类及其子类表示“JVM 常用操作”引发的错误。例如，若试图使用空值对象引用、除数为零或数组越界，则分别引发运行时异常（NullPointerException、ArithmeticException）和 ArrayIndexOutOfBoundException。
 *
 * unchecked exception（非检查异常，运行时异常）
 * 也称运行时异常（RuntimeException），比如常见的NullPointerException、IndexOutOfBoundsException。对于运行时异常，java编译器不要求必须进行异常捕获处理或者抛出声明，由程序员自行决定。
 *
 * checked exception（检查异常，编译异常）
 * 也称非运行时异常（运行时异常以外的异常就是非运行时异常），java编译器强制程序员必须进行捕获处理，比如常见的IOExeption和SQLException。对于非运行时异常如果不进行捕获或者抛出声明处理，编译都不会通过。
 * Java中的 Exception分为运行时、编译时异常
 * <p>
 * 对于运行时异常不做处理，默认是抛出
 * <p>
 * 对于编译时异常可以抛出也可以捕获:
 * 捕获：try catch
 * 抛出：只有方法声明为抛出该类型异常，方法内部才能够抛出，但声明后方法内部不一定会抛出异常
 * 一旦抛出异常，则方法的返回值可以省略
 * <p>
 * 其实无论如何处理结果都差不多，对于运行时异常都是一层一层的抛出
 * 对于编译时异常要么也是一层层抛出，要么在某一层进行捕获 而捕获后是抛出还是继续运行看自己
 */
public class ExceptionTest {
    int throwException() {
        try {
            throw new IllegalAccessException();
        } catch (IllegalAccessException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //常用方法 printStackTrace
        ok1();
    }

    static void ok1() {
        ok2(0);
    }

    static void ok2(int i) {
        System.out.println(1 / i);
    }
}
