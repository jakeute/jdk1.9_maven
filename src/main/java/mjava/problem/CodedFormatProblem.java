package mjava.problem;

import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * 编码问题的实质是  编码与解码
 *
 * 编码：是将 Unicode 转化为 编码结果
 * 解码：是将 编码结果  转化为 Unicode
 *
 * 至于格式有很多 utf-8 gbk
 *
 * Java String 默认编码解码格式 utf-8
 * Java io 默认编码解码 utf-8
 */
public class CodedFormatProblem {
    /**
     * String 默认转化成byte[]的编码格式是UTF-8
     * 而byte[]转化成 String 默认的也是 UTF-8
     *
     * @throws UnsupportedEncodingException
     */
    @Test
    public void StringTest() throws UnsupportedEncodingException {
        String uf = "123ABC王德峰";
        System.out.println(Arrays.toString(uf.getBytes()));
        System.out.println(Arrays.toString(uf.getBytes("UTF-8")));
        System.out.println(Arrays.toString(uf.getBytes("GBK")));
        System.out.println(Charset.defaultCharset().name());

        System.out.println(new String(uf.getBytes("utf-8")));
        System.out.println(new String(uf.getBytes("GBK")));
    }

    public static void main(String[] args) throws IOException {


        //对于字节流而言 字符串的编码格式并不是很重要 因为它主要处理字节传输 不需要关心
        InputStream ustr = CodedFormatProblem.class.getResourceAsStream("Utest.txt");
        InputStream gstr = CodedFormatProblem.class.getResourceAsStream("Gtest.txt");

//        byte[] bytes = new byte[20];
//        int loc;
//        loc=ustr.read(bytes);
//        System.out.println(Arrays.toString(bytes));
//        System.out.println(new String(bytes,0,loc));
//        loc=gstr.read(bytes);
//        System.out.println(Arrays.toString(bytes));
//        System.out.println(new String(bytes,0,loc));

        //对于字符流 默认编码格式是UTF-8 对于其他的编码格式需要手动输入编码格式
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(gstr, "GBK"));
        System.out.println(bufferedReader.readLine());

    }
}
