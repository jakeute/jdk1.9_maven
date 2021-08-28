package mjava.lang;

import org.junit.Test;

/**
 * 线程安全的可变字符序列。
 * 在多次String相加时可以使用来减少中间变量的产生，
 * String在创建后内部的数组就不能变化 但是StringBuffer内部维护着可变长的数组
 * 于是StringBuffer适用于在字符串上进行插入删除的等更改字符串的操作
 * String适用于对字符串进行分析处理的操作
 */
public class  StringBufferTest {
    @Test
    public void analyse() {
        //public StringBuffer()
        //构造一个其中不带字符的字符串缓冲区，其初始容量为 16 个字符。
        //public StringBuffer( int capacity)
        //指定容量的字符串缓冲区对象
        //public StringBuffer(String str)
        //指定字符串内容的字符串缓冲区对象
        StringBuffer stringBuffer = new StringBuffer();

        //append() 添加数据到字符串缓冲区
        stringBuffer.append("123");

        //public int length()
        System.out.println(stringBuffer.length());

        //insert() 插入 第一个参数是插入的位置
        stringBuffer.insert(1, 5);
        System.out.println(stringBuffer);

        //delete() 删除  前闭后开
        stringBuffer.delete(0, 2);

        //reverse() 反转字符串
        stringBuffer.reverse();

        //replace() 替换 前两个参数前闭后开
        stringBuffer.replace(0, 2, "123445");

        //subString() 子字符串
        stringBuffer.substring(2);
    }
}
