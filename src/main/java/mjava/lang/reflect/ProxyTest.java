package mjava.lang.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类在程序运行时创建的代理方式被成为动态代理。 静态代理的例子中，代理类是自己定义好的，
 * 在程序运行之前就已经编译完成。然而动态代理，代理类并不是在Java代码中定义的，而是在运行时根据我们在Java代码中的“指示”动态生成的。
 * 相比于静态代理， 动态代理的优势在于可以很方便的对代理类的函数进行统一的处理，而不用修改每个代理类中的方法。
 *
 *   JDK的动态代理机制只能代理实现了接口的类，而不实现接口的类就不能实现JDK的动态代理
 *   代理对象具有传入接口的所有方法
 *
 */
public class ProxyTest {
    public static void main(String[] args) {
        //newProxyInstance(1,2,3)
        //1 类加载器  = 对象.getClass.getClassLoader
        //2 接口数组  = 对象.getClass.getInterfaces
        //3 处理器   InvocationHandler

        // 通过接口数组，jvm能够创造出一个实现接口数组的类 并用类加载器加载
        // 类中的方法内部调用的都是 InvocationHandler.invoke 方法
        student student = new student();

        people proxy = (people) Proxy.newProxyInstance(student.getClass().getClassLoader(), student.getClass().getInterfaces(),
                new InvocationHandler() {

                    /**
                     * @param proxy 是jvm生成的类
                     * @param method 传递的接口方法
                     * @param args 方法参数
                     * @return
                     * @throws Throwable
                     *
                     * proxy 内部：
                     *
                     * String hello(...){
                     *     return InvocationHandler.invoke(....)
                     * }
                     *
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println(student.hello());
                        //student 为 final
                        Object invoke = method.invoke(student, args);
                        return invoke;
                    }
                });
        System.out.println(proxy.hello());
        System.out.println(proxy.equals(student));

    }
}

interface people {
    String hello();
}

class student implements people {

    @Override
    public String hello() {
        return "I am a student";
    }
}
