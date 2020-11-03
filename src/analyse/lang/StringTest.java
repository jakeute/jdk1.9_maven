package analyse.lang;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;

/**
 * <p>
 * 字符串不变; 它们的值在创建后不能被更改
 * Java语言为字符串连接运算符（+）提供特殊支持，并为其他对象转换为字符串。 字符串连接是通过StringBuilder
 * （或StringBuffer ）类及其append方法实现的。
 * <p>
 */
public class StringTest {
    @Test
    public void StringTest_init() {

        //在Java中字符串实际上是对char[] 的包装 (老版本)，存储了unicode字符集中对应字符的编号
        // 新版本是byte[]  char是两个字节，如果用来存一个字节的字符编号有点浪费，为了节约空间，Java 公司就改成了一个字节的byte来存储字符串。
        //这样在存储一个字节的字符是就避免了浪费 String中维护了一个新的属性 coder，它是编码格式的标识，在计算字符串长度或者调用 indexOf () 函数时，
        //需要根据这个字段，判断如何计算字符串长度。coder 属性默认有 0 和 1 两个值，0 代表Latin - 1（单字节编码），1 代表 UTF -16 编码。
        //如果 String判断字符串只包含了 Latin - 1，则 coder 属性值为 0 ，反之则为 1。

        //常量字符串的字符串内容保存在class文件之中，是在类加载完成，经过验证，准备阶段之后在堆中生成字符串对象实例，
        // 然后将该字符串对象实例的引用值存到string pool中。在遇到String类型常量时，resolve的过程如果发现StringTable(string pool实现)已经有了内容匹配的
        // String的引用，则直接返回这个引用，反之，如果StringTable里尚未有内容匹配的String实例的引用，
        // 则会在Java堆里创建一个对应内容的String对象，然后在StringTable记录下这个引用，并返回这个引用出去

        String coder_0 = "123"; //在堆中创建“123”的对象将引用保存在字符串常量池并且返回引用
        String w = "123"; //返回字符串常量池的引用
        String coder_1 = "刘全鑫";

        //对于new的String对象则每一次都会在对堆内生成。
        //对于new String(String obj)构造方法 内部的byte[]指向obj的数组，两者使用同一个byte[]对象
        //对于new String(char[])、 String(byte[])、String(int[]) 都是复制原数组 使用新的数组对象

        String p = new String("123");  //参数是字符串常量池的引用

        //intern()方法将字符串添加到常量池中，如果已经存在，则返回引用，否则将调用对象的引用添加到字符串常量池
    }

    @Test
    public void StringTest_usual() {
        String w = "123456";

        //length() 字符串的字符的个数 从一开始计数 使用时方便 < w.length()
        System.out.println(w.length());

        //charAt(int) 返回指定位置的char 从0开始 不能大于length()
        System.out.println(w.charAt(0));

        //compareTo() 按照字典顺序比较两个字符串 调用对象大时返回>0 相等时返回0 小于时返回<0
        System.out.println(w.compareTo("0123456"));

        //compareToIgnoreCase() 忽视大小写
        System.out.println("abc".compareToIgnoreCase("ABC"));

        //String.CASE_INSENSITIVE_ORDER 是Comparator<String>一个忽视大小写的实现
        Comparator<String> stringComparator = String.CASE_INSENSITIVE_ORDER;

        //contains() 当且仅当此字符串包含指定的char值序列时才返回true。
        System.out.println(w.contains("23"));

        //String.valueof() 以系列方法能将其他类型转化成String类型
        String s = String.valueOf(123);

        //String.format() 格式化输出字符串(和C语言一样)
        s = String.format("%d = %d", 1, 2);

        //getBytes() 使用平台的默认字符集将此 String编码为字节序列，将结果存储到新的字节数组中
        //也可以指定参数
        byte[] bytes = s.getBytes();
        try {
            bytes = s.getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //toCharArray()  将此字符串转换为新的字符数组
        char[] chars = s.toCharArray();

        //trim()  返回一个字符串，其值为此字符串，并删除任何前导和尾随空格。
        String trim = "  123   ".trim();
        System.out.println(trim);

        //toUpperCase() toLowerCase() 大小写转换
        System.out.println("acd".toUpperCase());
        System.out.println("CDE".toLowerCase());

        //substring(int beginIndex,int endIndex)返回一个字符串，该字符串是此字符串的子字符串。
        // 子串开始于指定beginIndex并延伸到字符索引endIndex - 1
        //substring(int beginIndex)返回一个字符串，该字符串是此字符串的子字符串。 子字符串以指定索引处的字符开头，并扩展到该字符串的末尾。
        System.out.println("abcderfgh".substring(1, 3));
        System.out.println("abcderfgh".substring(1));

        //indexof(int/String) 从开始位置处查找字符或则字符数组 返回第一次查找到的位置 未查找到则返回-1
        //也可以指定查找的位置indexof(int/String,int)
        //lastIndexof 查找最后出现的位置
        System.out.println(w.indexOf('2'));

        //split() 根据正则表达式分割字符串 在字符串中寻找匹配字段进行前后分割(相邻的匹配会产生"")
        // 要是无法分割则数组中只含有自己  ""分割每一个字符
        String[] split = w.split("\\.");

        //startwith() endwith() 进行开头结尾匹配
        System.out.println(w.startsWith("2"));
        System.out.println(w.endsWith("4"));

        //replace(char/String v1,char/String v2) 将字符串中的v1替换成v2
        //replaceFirst() 替换掉第一个
        //replaceAll() 根据正则表达式替换
        String p = "I like java but more like 123";
        System.out.println(p.replace(" ", ","));
        System.out.println(p.replaceFirst(" ", ","));
        System.out.println(p.replace("\\d+", ""));
        System.out.println(p.replaceAll("\\d+", ""));
    }

}
