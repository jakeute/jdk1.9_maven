package mjava.problem;

import mjava.lang.ObjectTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 总结：泛型就是将运行时的转换异常提前到了编译时检测异常
 * 在泛型类、泛型接口、泛型方法中，对于声明的泛型 E，我们可以把它当作一个真实存在的类，可以在类内自由的使用E，甚至可以当作泛型传入其他泛型类中，
 * 只是不能创建E的对象，因为E是假想的类。在源文件编译后E会被替换成其父类，擦除泛型信息，泛型类、泛型接口、泛型方法成为原生态类型（不存在泛型）。
 * 如果我们不对泛型在编译期进行检测的话，在运行时，我们就可以传入E的父类的任意子类。所以我们需要保存一个泛型记录，用于编译期进行泛型检测
 * 泛型记录只作用于 方法凭证，只需要保证方法的参数与返回值即可
 *
 * 在编译期中，对于E 所对应的泛型记录 需要在编译时确定，它可以是具体的类，也可以是其他的泛型T，我们只需要在方法上进行静态参数匹配检测与返回强转
 * 至于方法内部，递归检测即可
 *
 * 例如:
 * class peo<E>{
 *     E get(E e){
 *         System.out.println(e);
 *         return e;
 *     }
 * }
 *
 * class sup<T>{
 *
 *     T show(T t){
 *         peo<T> tpeo = new peo<>();
 *
 *         T t1 = tpeo.get(t);
 *
 *         System.out.println(t1);
 *
 *         return t1;
 *     }
 *
 *
 *      public static void main(String[]args){
 *
 *          sup<String> sup=new sup<>();
 *
 *          String aaaa=sup.show("aaaa");
 *
 *          System.out.println(aaaa);
 *      }
 * }
 *
 *
 * 在编译期，我们创建了 sup对象，T的泛型记录是String
 * 调用show方法，检查传入参数是否是String,并且将返回值强转为String
 * 然后进入show 内部，我们创建了peo 对象，E的泛型记录是 T
 * 调用get方法，检查传入参数是否是T,并且将返回值强转为 T
 * 如此递归下去，检测所有的方法，如果没有错误，就开始
 * 擦除泛型信息，泛型类、泛型接口、泛型方法成为原生态类型（不存在泛型）
 * 此时E T 都会转化为 Object。但是我们已成功确保不会发生运行时转换异常
 *
 * class peo{
 *     Object get(Object e){
 *         System.out.println(e);
 *         return e;
 *     }
 * }
 * class sup{
 *     Object show(Object t){
 *         peo tpeo = new peo();
 *         Object t1 = (Object)tpeo.get(t);
 *         System.out.println(t1);
 *         return t1;
 *     }
 *
 *      public static void main(String[]args){
 *          sup sup=new sup();
 *          String aaaa= (String)sup.show("aaaa");
 *          System.out.println(aaaa);
 *      }
 * }
 *
 *
 * 注意点：不能显式声明 E   List<E>  List<String> 的泛型数组
 * (但可以使用 类型变量E[],List<E>[]，还可以在可变参数中隐式创建数组 T... a,其真实类型是编译时根据传入参数确定的，并非Array.newInstance运行时确定)，
 * 可以使用List代替数组。至于为什么，
 * 因为Java中数组是协变可以具体化的，而泛型是可变可擦除的。两者混用会出现差错，从而违背泛型的设计理念：只要在程序中正确使用泛型，在编译时没有异常，就不会在运行时出现类型转化异常。
 *
 * 泛型保留：对于声明了具体的泛型的属性而言（private List<String> lis），因为其他类需要使用该属性所以编译后依旧保存泛型信息用于在其他类中检查与强转
 *
 * 操作的数据类型被指定为一个参数，这种参数类型可以用在类、接口和方法中，
 * 分别被称为泛型类、泛型接口、泛型方法。
 *
 * 通配符 :只能作用于引用类型
 * 1.<?> 被称作无限定的通配符。
 * ? 并不是声明的泛型而是具体的泛型信息，只是不知道是什么泛型信息而已，他可以匹配任意的类型
 * <?> 提供了只读（往外读出）的功能，也就是它删减了增加具体类型元素的能力，只保留与具体类型无关的功能。
 * 它不管装载在这个容器内的元素是什么类型，它只关心元素的数量、容器是否为空？
 *  <?>=<? extends Object>
 * <p>
 * 2.<? extends T> 被称作有上限的通配符。
 * <?> 代表着类型未知，但是我们的确需要对于类型的描述再精确一点，
 * 我们希望在一个范围内确定类别，比如类型 T 及 类型 T 的子类都可以
 * 对于使用了 <? extends T> 的类，我们只能使用具有 泛型返回 的泛型方法
 * <p>
 * 3.<? super T> 被称作有下限的通配符。
 * <? super T> 只能使用 具有 泛型入参 的泛型方法，或者 get Object
 * <p>
 * 一般而言，通配符能干的事情都可以用类型参数替换。只不过通配符限制了方法内的使用
 * public void testWildCards(Collection<?> collection) == public <T> void test(Collection<T> collection)
 * public void testWildCards(Collection<? extends ObjectTest> collection) == public <T extends ObjectTest> void test(Collection<T> collection)
 * <p>
 * 对于 class a<E extends a<E>> 而言 实际上是代表的是 父类的泛型是子类类型 例如 class b extends a<b>
 */
public class GenericityProblem<E extends ObjectTest> {
    E object;

    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }

    public static void main(String[] args) {
        ArrayList<String>[] strings = (ArrayList<String>[]) new ArrayList[5];
    }
}

/**
 * 泛型类 在泛型类的内部不允许创建泛型对象，但是泛型数组可以用泛型的父类数组代替
 * 因为在编译后会进行替换
 * E[] es =(E[])Object[5] -> Object[] es=(Object[])Object[5]
 * ArrayList<String>[] strings = (ArrayList<String>[]) new ArrayList[5] ->ArrayList[] strings = (ArrayList[]) new ArrayList[5]
 * Array.newInstance(class,int) 运行时从class中获取数组元素的具体class 并不会报错
 * <p>
 * 泛型接口与泛型类类似，但是泛型接口的方法由子类提供，而在子类中我们明确了泛型，可以显示的创造泛型对象
 *
 * @param <E>
 */
class Ping<E> {

    List<E> list =new ArrayList<>();
    /**
     * 泛型方法 在使用泛型方法时会自动的进行匹配
     *
     * @param t
     * @param <T>
     */
    public <T> void showT(T t) {
        System.out.println(t);
    }
}

/**
 * 子类可以指定父类的泛型
 */
class Ping2 extends Ping<String> {

}

/**
 * 子类也可以指定父类使用和自己一样的泛型
 *
 * @param <T>
 */
class Ping3<T> extends Ping<T> {
    public static void main(String[] args) {
        String p = show();
        System.out.println(Ping3.array(
                new ArrayList<String>(),new ArrayList<String>()
        ).getClass());
    }

    /**
     * 静态参数检测正确，不会关心内部情况
     * 只在返回做强转
     * @param <T>
     * @return
     */
    static <T> T show() {

        T i = (T) new Date();
        return (T) "123";
    }

    /**
     * 隐式数组创建
     * @param ts
     * @param <T>
     * @return
     */
    static <T> T[] array(T... ts){
        System.out.println(ts.length);
        return ts;
    }

}