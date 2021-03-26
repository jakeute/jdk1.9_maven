package mjava.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 此类的意义是 存储byte流
 * 可以将零散的byte[] 写入流中
 * byte[] toByteArray() 可以将其内的所有byte[] 输出
 */
public class ByteArrayOutputStreamTest {
    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write("abced".getBytes());

        outputStream.write("abced".getBytes());
        byte[] bytes = outputStream.toByteArray();

        String p= new String(bytes);

        System.out.println(p);
    }
}
