package mjava.math;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。双精度浮点型变量double可以处理16位有效数，
 * 但在实际应用中，可能需要对更大或者更小的数进行运算和处理。一般情况下，对于那些不需要准确计算精度的数字，我们可以直接使用Float和Double处理，
 * 但是Double.valueOf(String) 和Float.valueOf(String)（例如将‘0.3’转化为double）甚至进行数据运算都会丢失精度。所以开发中，如果我们需要精确计算的结果，
 * 则必须使用BigDecimal类来操作。BigDecimal所创建的是对象，故我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，而必须调用其相对应的方法。
 * 方法中的参数也必须是BigDecimal的对象。构造器是类的特殊方法，专门用来创建对象，特别是带有参数的对象。
 *
 * BigDecimal内部使用字符串存储数值，并不受长度的限制,因此将数据输出时也应该将String输出，不应该再转化为double造成精度损失
 *
 * <p>
 * 构造函数
 * BigDecimal(double)  BigDecimal(String)
 * 1）参数类型为double的构造方法的结果有一定的不可预知性。有人可能认为在Java中写入newBigDecimal(0.1)所创建的BigDecimal正好等于 0.1（非标度值 1，其标度为 1），
 * 但是它实际上等于0.1000000000000000055511151231257827021181583404541015625。这是因为0.1无法准确地表示为 double（或者说对于该情况，
 * 不能表示为任何有限长度的二进制小数）。这样，传入到构造方法的值不会正好等于 0.1（虽然表面上等于该值）。
 * 2）String 构造方法是完全可预知的：写入 newBigDecimal(“0.1”) 将创建一个 BigDecimal，它正好等于预期的 0.1。因此，比较而言，
 * 通常建议优先使用String构造方法。
 * 3）当double必须用作BigDecimal的源时，请注意，先使用Double.toString(double)方法,然后使用BigDecimal(String)构造方法，将double转换为String。
 *  这样就可以掩盖double的精度损失，例如 double a=0.1  虽然内部不是真正的0.1但是toString后为0.1（计算机内部进行了化简）
 *
 * <p>
 * 常用方法
 * add(BigDecimal)
 * <p>
 * BigDecimal对象中的值相加，返回BigDecimal对象
 * <p>
 * subtract(BigDecimal)
 * <p>
 * BigDecimal对象中的值相减，返回BigDecimal对象
 * <p>
 * multiply(BigDecimal)
 * <p>
 * BigDecimal对象中的值相乘，返回BigDecimal对象
 * <p>
 * divide(BigDecimal)
 * <p>
 * BigDecimal对象中的值相除，返回BigDecimal对象
 * <p>
 * toString()
 * <p>
 * doubleValue()
 * <p>
 * 将BigDecimal对象中的值转换成双精度数
 * <p>
 * floatValue()
 * <p>
 * 将BigDecimal对象中的值转换成单精度数
 * <p>
 * int a = bigdemical.compareTo(bigdemical2)
 * 返回结果分析：
 * <p>
 * a = -1,表示bigdemical小于bigdemical2；
 * a = 0,表示bigdemical等于bigdemical2；
 * a = 1,表示bigdemical大于bigdemical2；
 */
public class BigDecimalTest {
    public static void main(String[] args) {
        System.out.println(new BigDecimal(0.1));
        System.out.println(new BigDecimal("0.1"));
        System.out.println(new BigDecimal(Double.toString(0.1)));
    }

    @Test
    public void test1(){
        BigDecimal bigDecimal = new BigDecimal("0.3");
        System.out.println(bigDecimal.doubleValue());
        BigDecimal bigDecimal1 = new BigDecimal(0.3);
        System.out.println(bigDecimal1);
    }

    @Test
    public void test2() {
        BigDecimal bigDecimal = new BigDecimal("1");
        BigDecimal divide = bigDecimal.divide(new BigDecimal("3"),new MathContext(20));
        System.out.println(divide);
        System.out.println(divide.multiply(new BigDecimal("3")));
    }
}
