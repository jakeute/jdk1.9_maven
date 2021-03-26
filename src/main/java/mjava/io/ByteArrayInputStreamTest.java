package mjava.io;

import java.io.ByteArrayInputStream;

/**
 * 此类可以将byte[] 封装成 输入流
 */
public class ByteArrayInputStreamTest {
    public static void main(String[] args) {
        byte[] bytes = "qwertyuioppfdsasdfmnbvcxcfyukmnbvcddd".getBytes();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);

    }
}
