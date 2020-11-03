package analyse.math;

import java.math.BigInteger;

/**
 * 继承自Number类，可表示任意长度的整数。它的初始化方式却没有String那么方便可以直接赋值，而是跟其他自定义的类一样，
 * 要调用它的构造器进行初始化。这个类的取值范围原则上是没有上限的
 * <p>
 * public BigInteger(int numBits, Random rnd)
 * <p>
 * 构造一个随机生成的numBits位的BigInteger。
 * <p>
 * public BigInteger(String val)
 * <p>
 * 将十进制字符串表示的val转为BigInteger。
 * <p>
 * public BigInteger(String val, int radix)
 * <p>
 * 将radix进制字符串表示的val转为BigInteger。
 * <p>
 * public BigInteger add(BigInteger val)
 * <p>
 * 加法。
 * <p>
 * public BigInteger subtract(BigInteger val)
 * <p>
 * 减法。
 * <p>
 * public BigInteger multiply(BigInteger val)
 * <p>
 * 乘法。
 * <p>
 * public BigInteger divide(BigInteger val)
 * <p>
 * 除法。
 * <p>
 * public BigInteger[] divideAndRemainder(BigInteger val)
 * <p>
 * 带余除法，返回商和余数。
 * <p>
 * public BigInteger remainder(BigInteger val)
 * <p>
 * 求余数。
 * <p>
 * public BigInteger mod(BigInteger m)
 * <p>
 * 取模，注意和取余的区别。
 * <p>
 * public BigInteger pow(int exponent)
 * <p>
 * 幂运算。
 * <p>
 * public BigInteger gcd(BigInteger val)
 * <p>
 * 求最大公因子。
 * <p>
 * public BigInteger abs()
 * <p>
 * 取绝对值。
 * <p>
 * public BigInteger negate()
 * <p>
 * 取相反数。
 * <p>
 * <p>
 * intValue() longValue() 返回int long表示的数据
 * <p>
 * int a = bigdemical.compareTo(bigdemical2)
 * * 返回结果分析：
 * *
 * * a = -1,表示bigdemical小于bigdemical2；
 * * a = 0,表示bigdemical等于bigdemical2；
 * * a = 1,表示bigdemical大于bigdemical2；
 */
public class BigIntegerTest {
    public static void main(String[] args) {
        BigInteger bigInteger = new BigInteger("22222222222222222222");
        System.out.println(bigInteger.subtract(new BigInteger("5")));

    }
}
