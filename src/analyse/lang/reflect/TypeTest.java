package analyse.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Type代表的是 Java 编程语言中的 类型信息（也就是变量名前的类型），也是Java中所有 类型 的“爹”；
 * 其中，“所有类型”的描述尤为值得关注。它并不是我们平常工作中经常使用的 int、String、List、Map等数据类型，
 * 而是从Java语言角度来说，对基本类型、引用类型、泛型（list<T>）、泛型数组 泛型变量（T t）向上的抽象（只要是能想到的类型）；
 * 但是能从class文件中获取到泛型信息的都是类属性 方法内声明的变量编译后的都丢失具体的泛型信息
 * <p>
 * Type体系中类型的包括：原始类型(Class)、参数化类型(ParameterizedType)、数组类型(GenericArrayType)、类型变量(TypeVariable)、基本类型(Class);
 * <p>
 * 原始类型，不仅仅包含我们平常所指的类，还包括枚举、数组、注解等；Filed Method 都是为Class服务的
 * <p>
 * 参数化类型，就是我们平常所用到的泛型 List、Map；
 * <p>
 * 数组类型，并不是我们工作中所使用的数组String[] 、byte[]，而是带有泛型的数组，即T[] ；
 * <p>
 * 基本类型，也就是我们所说的java的基本类型，即int,float,double等
 */
public class TypeTest<T> {
    ArrayList<T> list;

    public static void main(String[] args) throws NoSuchFieldException, ClassNotFoundException {
        show4();

    }

    static void show1() throws NoSuchFieldException {
        //1.ParameterizedType
        //ParameterizedType表示参数化类型，也就是泛型，例如List<T>、Set<T>等；
        Field list = TypeTest.class.getDeclaredField("list");

        //获取变量 list 的类型信息
        Type genericType = list.getGenericType();
        //查看类型信息的 Class --ParameterizedType
        System.out.println(genericType.getClass());

        if (genericType instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) genericType;

            //获取泛型中的实际类型，可能会存在多个泛型，例如Map<K,V>,所以会返回Type[]数组；
            //值得注意的是，无论<>中有几层嵌套(List<Map<String,Integer>)，getActualTypeArguments()方法永远都是脱去最外层的<>(也就是List<>)
            //将口号内的内容（Map<String,Integer>）返回；
            //我们经常遇到的List<T>，通过getActualTypeArguments()方法，得到的返回值是TypeVariableImpl对象，也就是TypeVariable类型(后面介绍);
            //要是具体的类的话则是CLass对象
            Type[] actualTypeArguments = type.getActualTypeArguments();
            for (Type t : actualTypeArguments) {
                System.out.println(t.getTypeName() + " - " + (t instanceof Class));
            }

            //获取声明泛型的类或者接口，也就是泛型中<>前面的那个值
            //也是Class的对象
            Type rawType = type.getRawType();
            System.out.println(rawType.getTypeName() + " + " + (rawType instanceof Class));

            //getOwnerType
            //通过方法的名称，我们大概了解到，此方法是获取泛型的拥有者，
            //通过注解，我们得知，“拥有者”表示的含义--内部类的“父类”，通过getOwnerType()方法可以获取到内部类的“拥有者”；例如：
            // Map  就是 Map.Entry<String,String>的拥有者
            Type ownerType = type.getOwnerType();
        }
    }

    static void show2() {
        //GenericArrayType
        //泛型数组类型，例如List<String>[] 、T[]等；

        //getGenericComponentType
        //返回泛型数组中元素的Type类型，即List<String>[] 中的 List<String>（ParameterizedTypeImpl）、T[] 中的T（TypeVariableImpl）；
    }

    static void show3() {
        //3.TypeVariable
        //泛型的类型变量，指的是List<T>、Map<K,V>中的T，K，V等值，实际的Java类型是TypeVariableImpl（TypeVariable的子类）；
        //此外，还可以对类型变量加上extend限定，这样会有类型变量对应的上限

        //getBounds
        //获得该类型变量的上限，也就是泛型中extend右边的值；例如 List<T extends Number> ，
        // Number就是类型变量T的上限；如果我们只是简单的声明了List<T>（无显式定义extends），那么默认为Object；
        //值得注意的是，类型变量的上限可以为多个，必须使用&符号相连接，例如 List<T extends Number & Serializable>；其中，& 后必须为接口；


        //getName
        //获取类型变量在源码中定义的名称


        // getGenericDeclaration
        //获取声明该类型变量实体，也就是T 得到 TypeTest<T>；
        //通过查看源码发现，GenericDeclaration下有三个子类，分别为Class、Method、Constructor；
        // 也就是说，我们定义泛型只能在一个类中这3个地方自定义泛型；我们在Field中并没有声明泛型，而是在使用泛型而已
    }


    static void show4() {
        //Class
        //Type接口的实现类，是我们工作中常用到的一个对象；在Java中，每个.class文件在程序运行期间，都对应着一个Class对象，
        //Class.class也是Class的一个实例对象
        // 这个对象保存有这个类的全部信息；因此，Class对象也称之为Java反射的基础


        //int.class 被加载到jvm 自动生成 Class类的一个对象
        //对其对象.getClass 会寻找 这个对象（Class实例）对应类（Class）的 .class文件 生成的 Class对象
        Class<?> integerClass = int.class;
        Class<? extends Class> aClass = integerClass.getClass();
        System.out.println(aClass.getClass());
    }

    static void show5() {
        //WildcardType
        //？---通配符表达式，表示通配符泛型，但是WildcardType并不属于Java-Type中的一钟；例如：List<? extends Number> 和 List<? super Integer>；
        //在WildcardType接口中，有2个方法，分别为getUpperBounds()、getLowerBounds();
    }
}
