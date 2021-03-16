package mjava.lang;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Java 语言中的类、方法、变量、参数和包等都可以被标注。和 Javadoc 不同，Java 标注可以通过反射获取标注内容。在编译器生成类文件时，
 * 标注可以被嵌入到字节码中。Java 虚拟机可以保留标注内容，在运行时可以获取到标注内容 。 当然它也支持自定义 Java 标注。
 * <p>
 * 内置的注解
 * Java 定义了一套注解，共有 7 个，3 个在 java.lang 中，剩下 4 个在 java.lang.annotation 中。
 * <p>
 * 作用在代码的注解是
 *
 * @Override - 检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。
 * @Deprecated - 标记过时方法。如果使用该方法，会报编译警告。
 * @SuppressWarnings - 指示编译器去忽略注解中声明的警告。
 *
 * @SuppressWarnings("unchecked") 忽略警告
 *
 * <p>
 * 作用在其他注解的注解(或者说 元注解)是:
 * @Retention - 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问。--RetentionPolicy
 * @Documented - 标记这些注解是否包含在用户文档中。
 * @Target - 标记这个注解可以标注在哪里。--ElementType
 * @Inherited - 标记这个注解是否具有继承性
 * <p>
 * "每 1 个 Annotation" 都与 "1 个 RetentionPolicy" 关联，并且与 "1～n 个 ElementType" 关联。可以通俗的理解为：每 1 个 Annotation 对象，
 * 都会有唯一的 RetentionPolicy 属性；至于 ElementType 属性，则有 1~n 个。
 */

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationTest {
    String[] value();

    int ch();

    int ah() default 0;
}

@AnnotationTest(value = {"1", "2"}, ch = 14)
class show {
    public static void main(String[] args) {
        //能用反射获取注解的元素
        Class<show> showClass = show.class;
        if (showClass.isAnnotationPresent(AnnotationTest.class)) {

            AnnotationTest annotation = showClass.getAnnotation(AnnotationTest.class);

            System.out.println(annotation.ah());
            String[] value = annotation.value();
            System.out.println(value.length);
        }
    }
}
