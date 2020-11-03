package analyse.lang;

import org.junit.Test;

/**
 * 父类：抽象类Number是表示数字值可转换为基本数据类型平台类的超类byte ， double ， float ， int ， long和short 。
 * 从一个特定的数字值转换的特定语义Number实现给定的原语类型由定义Number所讨论的实现。转换可能会丢失有关数值的总体大小的信息，可能会失去精度，
 * 甚至可能会返回与输入不同的符号结果。
 * Integer类包装一个对象中的原始类型int的值。 类型为Integer的对象包含一个单一字段，其类型为int 。
 * 此外，该类还提供了一些将int转换为String和String转换为int ，以及在处理int时v 有用的其他常量和方法。
 */
public class IntegerTest {
    @Test
    public void start() {
        //Integer 跟String一样都是不变类型的 更改属性后会生成新的对象，不会更改原先的对象
        Integer a = 5;
        Integer b = a;
        a = 4;
        System.out.println(b);

        // 获取 int 类型可取的最大值
        System.out.println(Integer.MAX_VALUE);
        // 获取 int 类型可取的最小值
        System.out.println(Integer.MIN_VALUE);
        // 获取 int 类型的二进制位
        System.out.println(Integer.SIZE);
        // 获取基本类型 int 的 Class 实例
        System.out.println(Integer.TYPE + ":" + int.class);

        //Integer.parseInt()  将String转化为int
        int i = Integer.parseInt("123");
        System.out.println(i);
        System.out.println(Integer.parseInt("256", 16));

        //进制转换  当num为正数时会省略前面的0
        int num = 226;
        System.out.println(Integer.toString(num));
        System.out.println(Integer.toBinaryString(num));
        System.out.println(Integer.toHexString(num));
        System.out.println(Integer.toOctalString(num));


    }

    /**
     * 分析Integer内部构造
     */
    @Test
    public void creat() {
        //运用构造函数产生的是两个不同的对象 跟String类似
        Integer a = new Integer(5);
        Integer b = new Integer(5);
        System.out.println(a == b);
        //在Integer内部已经将-128————127的Integer对象并且存储
        Integer c = 4;
        Integer d = 4;
        System.out.println(c == d);

        c = 128;
        d = 128;
        System.out.println(c == d);
    }
}
