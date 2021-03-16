package mspring.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * 文件操作
 * 在使用各种 Resource 接口的实现类加载文件资源后，经常需要对文件资源进行读取、拷贝、转存等不同类型的操作。
 * FileCopyUtils
 * 它提供了许多一步式的静态操作方法，能够将文件内容拷贝到一个目标 byte[]、String 甚至一个输出流或输出文件中。
 */
public class FileCopyUtilsTest {

    /**
     * static void copy(byte[] in, File out) 将 byte[] 拷贝到一个文件中
     * static void copy(byte[] in, OutputStream out) 将 byte[] 拷贝到一个输出流中
     * static int copy(File in, File out) 将文件拷贝到另一个文件中
     * static int copy(InputStream in, OutputStream out) 将输入流拷贝到输出流中
     * static int copy(Reader in, Writer out) 将 Reader 读取的内容拷贝到 Writer 指向目标输出中
     * static void copy(String in, Writer out) 将字符串拷贝到一个 Writer 指向的目标中
     */
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int copy = FileCopyUtils.copy(new ClassPathResource("Route.txt").getInputStream(), outputStream);
        System.out.println(Arrays.toString(outputStream.toByteArray()));

    }

}
