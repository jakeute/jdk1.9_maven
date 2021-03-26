package mjava.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

/**
 * 该类主要用于读取Java的配置文件，不同的编程语言有自己所支持的配置文件，配置文件中很多变量是经常改变的，为了方便用户的配置，
 * 能让用户够脱离程序本身去修改相关的变量设置。就像在Java中，其配置文件常为.properties文件，是以键值对的形式进行参数配置的。
 * <p>
 * 此类是一个map  一些map的方法都可以使用
 * }
 */
public class PropertiesTest {
    public static void main(String[] args) throws IOException {
        InputStream resourceAsStream = PropertiesTest.class.getResourceAsStream("/key.properties");
        Properties properties = new Properties();

        //加载文件流
        properties.load(resourceAsStream);

        //展示文件
        // properties.list(System.out);

        //得到value
        System.out.println(properties.getProperty("lqx"));

        Set<Object> objects = properties.keySet();
        for (Object object : objects) {
            System.out.println(properties.getProperty((String) object));
        }

    }
}
