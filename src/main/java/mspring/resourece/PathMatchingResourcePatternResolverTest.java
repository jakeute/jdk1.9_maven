package mspring.resourece;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * Resource 接口下有众多的实现类 而我们只需要Resource接口并不感兴趣实现类
 *
 * 所以引入了资源地址表达式：classpath: file: http://
 * 其中
 * classpath:指的是类路径下的资源文件 也是resources下的资源
 * classpath*:指的是当前项目与导入的jar包的资源文件  经常用在多模块中，一个模块可以导入另外一个模块的classpath下资源文件
 * 例如: smart项目分成三个模块 module1 module2 module3 三个项目都在classpath下的com/smart/下创建了资源文件
 * 在module3中使用classpath*:com/smart/spring*.xml 可以将module1，2中的资源进行加载
 *
 *
 * 而且引入了ResourceLocal接口 我们只需要提供带资源表达式的地址，它可以帮我们自动的判别生成对应的Resource实现类
 *
 * PathMatchingResourcePatternResolver 不仅支持资源地址表达式 还支持Ant风格
 *    ？ 匹配某一字符
 *    * 匹配任意字符
 *    ** 匹配多层路径
 */
public class PathMatchingResourcePatternResolverTest {
    public static void main(String[] args) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:*");
        for (Resource resource : resources) {
            System.out.println(resource.getURI());
        }
    }
}
