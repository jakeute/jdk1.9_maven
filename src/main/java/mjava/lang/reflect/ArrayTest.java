package mjava.lang.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class ArrayTest {
    public static void main(String[] args) {
        String[] array = getArray(String[].class, 15);
        Method[] declaredMethods = ArrayTest.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }
    }

    /**
     * 生成泛型数组
     * 因为泛型在编译后就会擦除，但是传入泛型数组的class（运行时获取）
     * 可以得到具体的泛型
     *
     * @param tClass
     * @param length
     * @param <T>
     * @return
     */
    static <T> T[] getArray(Class<?> tClass, int length) {
        @SuppressWarnings("unchecked")
        T[] t = (T[]) Array.newInstance(tClass.getComponentType(), length);
        return t;
    }

    static <E> E[] getArray(E[] t, int length) {
        E[] array = getArray(t.getClass(), length);
        return array;
    }

    static Object[] getArray(String array, int length) throws ClassNotFoundException {
        Class<?> aClass = Class.forName(array);
        Object o = Array.newInstance(aClass, length);
        return (Object[]) o;
    }
}
