package mjava.problem;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 字节输入流：
 *  抽象基类：InputStream
 *  访问文件：FileInputStream
 *  访问数组：ByteArrayInputStream
 *  缓冲流：BufferInputStream
 *  对象流：ObjectInputStream
 *
 * 字节输出流：
 *  抽象基类：OutputStream
 *  访问文件：FileOutputStream
 *  访问数组：ByteArrayOutputStream
 *  缓冲流：BufferOutputStream
 *  对象流：ObjectIOutputStream
 *
 * 字符输入流：
 *  抽象基类：Reader
 *  访问文件：FileReader
 *  访问数组：CharArrayReader
 *  缓冲流：BufferReader
 *  转换流：InputStreamReader
 *
 *  字符输入流：
 *   抽象基类：Writer
 *   访问文件：FileWriter
 *   访问数组：CharArrayWriter
 *   缓冲流：BufferWriter
 *   转换流：OutputStreamWriter
 */
public class IOProblem {
    /**
     * FileInputStream 从文件系统中的某个文件中获得输入字节。FileInputStream
     * 用于读取非文本数据之类的原始字节流。要读取字符流，需要使用 FileReader
     *
     * 程序中打开的文件 IO 资源不属于内存里的资源，垃圾回收机制无法回收该资
     * 源，所以应该显式关闭文件 IO 资源。
     *
     * int read(byte[] b)
     * 从此输入流中将最多 b.length 个字节的数据读入一个 byte 数组中。如果因为已
     * 经到达流末尾而没有可用的字节，则返回值 -1。否则以整数形式返回实际读取
     * 的字节数。
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {
        FileInputStream inputStream = new FileInputStream("src\\main\\resources\\Utest.txt");
        byte[] bytes = new byte[1024];
        int num = inputStream.read(bytes);
        System.out.println(num);
        inputStream.close();

        FileReader read =new FileReader("src\\main\\resources\\Utest.txt");
        char[] chars = new char[1024];
        num = read.read(chars);
        System.out.println(chars);
        read.close();
    }

    /**
     *  OutputStream 和 Writer 也非常相似：
     *  void write(int b/int c);
     *  void write(byte[] b/char[] cbuf);
     *  void write(byte[] b/char[] buff, int off, int len);
     *  void flush();
     *  void close(); 需要先刷新，再关闭此流
     *  因为字符流直接以字符作为操作单位，所以 Writer 可以用字符串来替换字符数组，
     * 即以 String 对象作为参数
     *  void write(String str);
     *  void write(String str, int off, int len);
     *
     * 当第二个参数为真时，是追加，默认是false即覆盖
     * @throws IOException
     */
    @Test
    public void test2() throws IOException {
        FileWriter writer = new FileWriter("src\\main\\resources\\Utest.txt", true);
        writer.write("\nllll");
        writer.close();
    }

    /**
     * 当读取数据时，数据按块读入缓冲区，其后的读操作则直接访问缓冲区
     *  当使用BufferedInputStream读取字节文件时，BufferedInputStream会一次性从
     * 文件中读取8192个(8Kb)，存在缓冲区中，直到缓冲区装满了，才重新从文件中
     * 读取下一个8192个字节数组。
     *  向流中写入字节时，不会直接写到文件，先写到缓冲区中直到缓冲区写满，
     * BufferedOutputStream才会把缓冲区中的数据一次性写到文件里。使用方法
     * flush()可以强制将缓冲区的内容全部写入输出流
     *  关闭流的顺序和打开流的顺序相反。只要关闭最外层流即可，关闭最外层流也
     * 会相应关闭内层节点流
     *  flush()方法的使用：手动将buffer中内容写入文件
     *  如果是带缓冲区的流对象的close()方法，不但会关闭流，还会在关闭流之前刷
     * 新缓冲区，关闭后不能再写出
     *
     *
     *
     * 转换流提供了在字节流和字符流之间的转换
     * Java API提供了两个转换流：
     *  InputStreamReader：将InputStream转换为Reader
     *  OutputStreamWriter：将OutputStream转换为Writer
     *  字节流中的数据都是字符时，转成字符流操作更高效。
     *  很多时候我们使用转换流来处理文件乱码问题。实现编码和解码的功能。
     *
     * InputStreamReader
     *  实现将字节的输入流按指定字符集转换为字符的输入流。
     *  需要和InputStream“套接”。
     *  构造器
     *  public InputStreamReader(InputStream in)
     *  public InputStreamReader(InputStream in,String charsetName)
     *
     *
     * OutputStreamWriter
     *  实现将字符的输出流按指定字符集转换为字节的输出流。
     *  需要和OutputStream“套接”。
     *  构造器
     *  public OutputStreamWriter(OutputStream out)
     *  public OutputStreamWriter(OutputStream out,String charsetName)
     */
    @Test
    public void test3(){

    }
}
