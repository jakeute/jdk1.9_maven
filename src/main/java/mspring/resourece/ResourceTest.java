package mspring.resourece;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Spring 定义了一个 org.springframework.core.io.Resource 接口，
 * Resource 接口是为了统一各种类型不同的资源而定义的，Spring 提供了若干 Resource 接口的实现类
 * 这些实现类可以轻松地加载不同类型的底层资源，并提供了获取文件名、URL 地址以及资源内容的操作方法
 * getFileName() 获取文件名，
 * getFile() 获取资源对应的 File 对象，
 * getInputStream() 直接获取文件的输入流。
 * createRelative(String relativePath) 在资源相对地址上创建新的资源。
 * getPath()  相对路径
 *
 *
 * 众所周知，spring所依赖的就是配置文件，而配置文件需要我们在resources目录下手动编写
 * 然后在编译后会被复制到类目录下，spring的classpath就会定位到配置文件，所以classpath也就间接的指向resources。
 * 我们更改resources在编译后就会同步到类目录，但是在运行时两者就不会存在依赖关系，更改resources并不会同步到类目录，
 * 所以resources下的文件都应该是只读资源文件，只能手动更改，而不能被程序更改，只有这样才能保证classpath与resources的一一对应关系
 * 对于需要程序更改的文件可以放在项目路径下，此时并不会复制到类路径下，文件仅存在一份，具有唯一性
 * 当然也可以在类路径下创建文件进行操作，但非必要要不推荐
 */
public class ResourceTest {
    /**
     *  通过 FileSystemResource 以文件系统绝对路径的方式进行访问；
     *  通过 ClassPathResource 以类路径的方式进行访问；
     *  通过 ServletContextResource 以相对于 Web 应用根目录的方式进行访问
     * @param args
     */
    public static void main(String[] args) throws IOException {
        Resource resource =
                new FileSystemResource("C:\\Users\\90343\\IdeaProjects\\jdk1.9_maven\\src\\main\\resources\\Gtest.txt");

        System.out.println(resource.getFilename());

        ClassPathResource classPathResource = new ClassPathResource("Gtest.txt");

        InputStream inputStream = classPathResource.getInputStream();

        System.out.println(classPathResource.getFile().getAbsolutePath());

    }

    @Test
    public void test1() throws IOException {
        File file = new ClassPathResource("Route.txt").getFile();
        FileOutputStream out = new FileOutputStream(file);
        out.write("lllllllllwwww".getBytes());
        out.close();
    }

    @Test
    public void test2() throws IOException {
        File file = new File("C:\\Users\\90343\\IdeaProjects\\jdk1.9_maven\\src\\main\\resources\\Route.txt");
        FileOutputStream out = new FileOutputStream(file);
        out.write("lllllllllwwww".getBytes());
        out.close();
    }
}
