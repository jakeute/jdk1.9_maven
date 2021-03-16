import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class RsTest {
    @Test
    public void test() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:Route.txt");
        System.out.println(file);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource resource = resolver.getResource("classpath:Route.txt");
        System.out.println(resource.getFilename());
    }
}
