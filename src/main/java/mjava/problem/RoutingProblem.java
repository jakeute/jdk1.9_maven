package mjava.problem;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 此项目在maven环境下构建 所以资源文件必须在resources目录下
 * 而resources目录下的资源问件编译后在类目录下
 *
 * 对于资源只需要谨记：file指向谁就操作谁
 * idea在编译后会将resources的文件复制放在类目录下，如果代码中的file/resource选择的是类路径
 * 此时操作的就是编译后的类目录的文件，如果操作的是resources目录下的文件，则不会对编译后类目录的文件
 * 产生影响
 */
public class RoutingProblem {
    @Test
    public void relative_path() throws FileNotFoundException {

        // File、I/O流的默认路径是当前项目（相对路径）
        // 因为文件的位置是随意的，不受约束
        File file = new File("Route.txt");
        //获取绝对路径
        System.out.println(file.getAbsolutePath());
        //C:\Users\90343\IdeaProjects\jdk1.9_maven\Route.txt

        //Class.getResource(String path)
        //path  不以’/'开头时，默认是从此类所在的包下取资源；path(相对路径)  以’/'开头时，则是从ClassPath根下获取（绝对路径）；
        System.out.println(RoutingProblem.class.getResource(""));
        //file:/C:/Users/90343/IdeaProjects/jdk1.9_maven/target/classes/mjava/problem/
        System.out.println(RoutingProblem.class.getResource("/"));
        //file:/C:/Users/90343/IdeaProjects/jdk1.9_maven/target/classes/

        //Class.getClassLoader().getResource(String path)
        //path不能以’/'开头时；
        //path是从ClassPath根下获取；
        System.out.println(RoutingProblem.class.getClassLoader().getResource("Route.txt"));
        // file:/C:/Users/90343/IdeaProjects/jdk1.9_maven/target/classes/Route.txt
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

    @Test
    public void test3() throws IOException {
        System.out.println(RoutingProblem.class.getClassLoader());
    }
}
