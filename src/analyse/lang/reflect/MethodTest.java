package analyse.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Method类：代表类的方法。
 * 在Java的调用方法时含有隐含参数为该对象
 * public Object invoke(Object obj,Object... args)
 * 在具有指定参数的方法对象上调用此方法对象表示的基础方法。 个别参数自动解包以匹配原始形式参数，原始参考参数和参考参数都需要进行方法调用转换。
 * 如果底层方法是静态的，则指定的obj参数将被忽略。 它可能为null。
 * 返回值是方法返回值
 * <p>
 * 如果底层方法所需的形式参数的数量为0，则提供的args数组的长度为0或为空
 * <p>
 * 如果为私有构造 则调用前需要setAccessible(true);
 * <p>
 * <p>
 * <p>
 * getReturnType()  返回值类型
 * getParameterTypes()  参数类型
 */

public class MethodTest {
    public void show(String a) {
        System.out.println(a);
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        MethodTest methodTest = new MethodTest();
        Method show = methodTest.getClass().getDeclaredMethod("show", String.class);
        show.invoke(methodTest, "156");
        //getParameterTypes()  形式参数
        // getReturnType()  返回

        System.out.println(show.getReturnType());
        System.out.println(Arrays.toString(show.getParameterTypes()));
    }

    public void show() {
        System.out.println("Method1");
    }
}

class Method2 {
    public void show() {
        System.out.println("Method2");
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method show = MethodTest.class.getMethod("show");
        MethodTest methodTest = new MethodTest();

        show.invoke(methodTest);
    }
}