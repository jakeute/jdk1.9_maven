package problem;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * out文件夹的项目是src下的文件（层次完全一样）
 * 在项目的src下创建的文件会在out下
 * 而在项目目录下的其他文件则不会出现在out中
 */
public class RoutingProblem {
    @Test
    public void relative_path() throws FileNotFoundException {

        // File、I/O流的默认路径是当前项目
        // 因为文件的位置是随意的，不受约束
        File file = new File("Route.txt");
        //获取绝对路径
        System.out.println(file.getAbsolutePath());
        //C:\Users\90343\IdeaProjects\jdk1.9-source-analyze\Route.txt

        //Class.getResource(String path)
        //path  不以’/'开头时，默认是从此类所在的包下取资源；path(相对路径)  以’/'开头时，则是从ClassPath(即src目录)根下获取（绝对路径）；
        System.out.println(RoutingProblem.class.getResource(""));
        //file:/C:/Users/90343/IdeaProjects/jdk1.9-source-analyze/out/production/jdk1.9-source-analyze/problem/
        System.out.println(RoutingProblem.class.getResource("/"));
        // file:/C:/Users/90343/IdeaProjects/jdk1.9-source-analyze/out/production/jdk1.9-source-analyze/

        //Class.getClassLoader().getResource(String path)
        //path不能以’/'开头时；
        //path是从ClassPath根下获取；
        System.out.println(RoutingProblem.class.getClassLoader().getResourceAsStream("Route.txt"));
        // file:/C:/Users/90343/IdeaProjects/jdk1.9-source-analyze/out/production/jdk1.9-source-analyze/

    }
}
