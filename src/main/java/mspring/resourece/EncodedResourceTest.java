package mspring.resourece;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.FileCopyUtils;

import java.io.FileReader;
import java.io.IOException;

/**
 * spring 提供了对于 Resource编码问题的解决方式
 */
public class EncodedResourceTest {

    /**
     * 当您使用 Resource 实现类加载文件资源时，它默认采用操作系统的编码格式。如果文件资源采用了特殊的编码格式（如 GDK），
     * 则在读取资源内容时必须事先通过 EncodedResource 指定编码格式，否则将会产生中文乱码的问题。
     * @throws IOException
     */
    @Test
    public void test1() throws IOException {

        Resource res1 = new ClassPathResource("Utest.txt");
        String content1 = FileCopyUtils.copyToString(new FileReader(res1.getFile()));
        System.out.println(content1);


        Resource res2 = new ClassPathResource("Gtest.txt");
        //指定文件资源对应的编码格式（UTF-8）
        EncodedResource encRes = new EncodedResource(res2,"gbk");
        //这样才能正确解码从而读取文件的内容，而不会出现乱码
        String content2 = FileCopyUtils.copyToString(encRes.getReader());

        System.out.println(content2);
    }
}
