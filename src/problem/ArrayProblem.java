package problem;

import java.util.Arrays;

/**
 * java 中数组为协变类型 Object <- Object[] <- T[]
 * 数组是一个自动生成的类  其内部组成为声明的类
 * 不管如何协变，或者因为泛型丢失信息 但内部都为原始的类
 */
public class ArrayProblem {
    public static void main(String[] args) {
        String[] i = new String[10];
        Object[] p = i;
        Object[] objects = Arrays.copyOf(p, p.length);
        System.out.println(objects.getClass());
    }

    static <T> void show(T[] t) {
        //System.out.println(t.getClass().getComponentType());
        t = (T[]) new Object[5];
    }
}
