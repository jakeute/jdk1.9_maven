package mjava.lang;

/**
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
