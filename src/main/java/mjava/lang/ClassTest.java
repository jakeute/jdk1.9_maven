package mjava.lang;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

/**
 *
 * Java程序在运行时，Java运行时系统一直对所有的对象进行所谓的运行时类型标识，即所谓的RTTI。
 * 这项信息纪录了每个对象所属的类。虚拟机通常使用运行时类型信息选准正确方法去执行，用来保存这些类型信息的类是Class类。
 * Class类封装一个对象和接口运行时的状态，当装载类时，Class类型的对象自动创建。
 *
 * 说白了就是：
 * Class类也是类的一种，只是名字和class关键字高度相似，毕竟Java是大小写敏感的语言,Class可以被命名成其他的类名。
 * Class类的对象内容是你创建的类的类型信息，比如你创建一个shapes类，那么，Java会生成一个内容是shapes的Class类的对象
 * Class类的对象不能像普通类一样，以 new shapes() 的方式创建，它的对象只能由JVM创建，因为这个类没有public构造函数
 * 当，
 * 可以把Class换成People方便理解，当我们引入List类 jvm创建一个People对象i 记录List类信息，
 * 因此也需要引入People类，我们也要创建一个People对象j 记录People类信息，
 * 我们使用getClass()时 获取的就是jvm自动创建的记录类信息的 People对象
 * 如果我们对i.getClass（）获取的就是jvm中记录People类信息的People对象，也就是j
 * 对j.getCLass 还是 j，因为j记录了People类的信息
 *
 * Class类的作用是运行时提供或获得某个对象的类型信息，和C++中的typeid()函数类似。这些信息也可用于反射。
 *
 * class 是唯一的（同一个类加载器）
 * T - 由此类对象建模的类的类型。 例如， String.class的类型是Class<String> 。 如果正在建模的类是未知的，请使用Class<?> 。
 * 编译后只有Class
 * Class类的类表示正在运行的Java应用程序中的类和接口。 枚举是一种类。 每个数组也属于一个反映为类对象的类，
 * 该对象由具有相同元素类型和维数的所有数组共享。 原始Java类型（ boolean ， byte ， char ， short ， int ， long ， float和double ），
 * 和关键字void也表示为类对象。类没有公共构造函数。 相反， 类对象由Java虚拟机自动构建，因为加载了类，并且通过调用类加载器中的defineClass方法。
 * <p>
 * 跟class 相关的类：Field(属性) Method(方法) Construct(构造器) ParameterizedType、Array(泛型)
 * <p>
 * Class类：代表一个类，位于java.lang包下。
 */
public class ClassTest {
    public static void main(String[] args) throws ClassNotFoundException {
        //1.       调用对象的getClass()方法获取该对象的Class实例；
        One one = new One();
        Class<? extends One> aClass = one.getClass();
        System.out.println(aClass);
        //2.       使用Class类的静态方法forName("类名")获取该类的Class实例；需要类的全限定名
        Class<?> aClass1 = Class.forName("mjava.lang.ClassTest");
        System.out.println(aClass1);
        //3.       使用类的属性.class来获取Class实例，
        System.out.println(ClassTest.class);

    }

    /**
     *    1 getName()：返回String形式的该类的名称。
     * 　　2 newInstance()：根据某个Class对象产生其对应类的实例，它调用的是此类的默认构造方法(没有默认无参构造器会报错)
     * 　　3 getClassLoader()：返回该Class对象对应的类的类加载器。
     * 　　4 getSuperClass()：返回某子类所对应的直接父类所对应的Class对象
     * 　　5 isArray()：判定此Class对象所对应的是否是一个数组对象
     * 　　6 getComponentType() ：如果当前类表示一个数组，则返回表示该数组组件的 Class 对象，否则返回 null。
     * <p>
     * 　　7 getConstructor(Class[]) :返回当前 Class 对象表示的类的指定的公有构造对象。(构造函数无参数的话空参即可)
     * 　　8 getConstructors() :返回当前 Class 对象表示的类的所有公有构造的对象数组。
     * 　　9 getDeclaredConstructor(Class[]) :返回当前 Class 对象表示的类的指定已说明的一个构造对象。
     * 　　10 getDeclaredConstructors() :返回当前 Class 对象表示的类的所有已说明的构造对象数组。
     * <p>
     * 　　11 getDeclaredField(String) :返回当前 Class 对象表示的类或接口(不包含父类)的指定已说明的一个域对象(包含static)。
     * 　　12 getDeclaredFields() :返回当前 Class 对象表示的类或接口(不包含父类)的所有已说明的域对象数组(包含static)。
     * 　　13 getDeclaredMethod(String, Class[]) :返回当前 Class 对象表示的类或接口(不包含父类)的指定已说明的一个方法对象(包含static)。
     * 　　14 getDeclaredMethods() :返回 Class 对象表示的类或接口(不包含父类)的所有已说明的方法数组(包含static)。
     *    17 getInterfaces() :返回当前对象表示的类或接口(不包含父类)实现的接口。
     * <p>
     * 　　15 getField(String) :返回当前 Class 对象表示的类或接口(及其父类)的指定的公有成员域对象(包含static)。
     * 　　16 getFields() :返回当前 Class 对象表示的类或接口(及其父类)的所有可访问的公有域对象数组(包含static)。
     * 　　18 getMethod(String, Class[]) :返回当前 Class 对象表示的类或接口(及其父类)的指定的公有成员方法对象。
     * 　　19 getMethods() :返回当前 Class 对象表示的类或接口(及其父类)的所有公有成员方法对象数组
     * <p>
     * 　　20 isInstance(Object) :此方法是 Java 语言 instanceof 操作的动态等价方法。
     * 　　21 isInterface() :判定指定的 Class 对象是否表示一个接口类型
     * 　　22 isPrimitive() :判定指定的 Class 对象是否表示一个 Java 的基类型。
     */
    @Test
    public void try1() throws NoSuchMethodException, IllegalAccessException, InstantiationException, NoSuchFieldException, InvocationTargetException {
        Class<One> oneClass = One.class;
        //  System.out.println(Arrays.toString(oneClass.getClasses()));

//        One one = oneClass.newInstance();
//        System.out.println(one);
//        System.out.println(oneClass.getClassLoader());
//
//        System.out.println(Two.class.getSuperclass());
//
//        Constructor<?>[] constructors = oneClass.getConstructors();
//        System.out.println(Arrays.toString(constructors));
//
//        Constructor<One> declaredConstructor = oneClass.getConstructor();
//        System.out.println(declaredConstructor);
        Class<Two> twoClass = Two.class;
        Two two = new Two();
//        Field p = twoClass.getDeclaredField("p");
//        p.set(two,163.6);
//        System.out.println(two.p);
//        Field w = twoClass.getDeclaredField("w");
//        w.set(null,10);
//        System.out.println(Two.w);

//        Class<Three> threeClass = Three.class;
//        Method show = threeClass.getMethod("show");
//        System.out.println(show);
//        Method tey = twoClass.getDeclaredMethod("tey",int[].class);
//        Method puw = twoClass.getDeclaredMethod("puw");
//
//        puw.invoke(null);
//        tey.invoke(two, (Object) new int[5]);

        Field p = twoClass.getDeclaredField("p");

        p.setAccessible(true);
        p.set(two, 12.3);
        System.out.println(two);
    }
}


class One<T> {

    public Set<T> a;
    private int b;

    public static int c;
    private static int d;


    One() {
    }

    @Override
    public String toString() {
        return "One{" +
                "a=" + a +
                ", b=" + b +
                '}';
    }

    public static void main(String[] args) {
        Field[] declaredFields = One.class.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            System.out.println(declaredFields[i] + " : " + declaredFields[i].getGenericType());
        }

    }
}

class Two implements Comparable<Two> {
    private double p;


    /**
     * java 的可变参数实际上是数组的封装
     * 根据传入的参数自动生成数组
     *
     * @param a
     */
    void tey(int... a) {
        System.out.println(a.length);
    }

    @Override
    public String toString() {
        return "Two{" +
                "p=" + p +
                '}';
    }

    @Override
    public int compareTo(Two o) {
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(Two.class.getDeclaredMethods()));

    }
}
