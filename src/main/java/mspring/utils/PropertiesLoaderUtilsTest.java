package mspring.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Spring 提供的 PropertiesLoaderUtils 允许您直接通过基于类路径的文件 地址加载属性资源
 */
public class PropertiesLoaderUtilsTest {
    public static void main(String[] args) throws IOException {
        Properties properties = PropertiesLoaderUtils.loadProperties(new ClassPathResource("key.properties"));
        String xiaoming = properties.getProperty("xiaoming");
        System.out.println(xiaoming);
    }
}
