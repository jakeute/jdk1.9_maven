package mspring.utils;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * Spring 提供了一个 ResourceUtils 工具类，它支持“classpath:”和“file:”的地址前缀 ，它能够从指定的地址加载文件资源。
 * 只能用于已经存在的文件，否则返回 null
 *
 */
public class ResourceUtilsTest {
    public static void main(String[] args) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:Route.txt");
        System.out.println(file);
    }
}
