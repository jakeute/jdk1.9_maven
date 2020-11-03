package problem;

import analyse.lang.ObjectTest;

import java.util.ArrayList;
import java.util.Date;

/**
 * 泛型，就是将类型由原来的具体的类型参数化，类似于方法中的变量参数，此时类型也定义成参数形式（可以称之为类型形参），
 * 然后在使用/调用时传入具体的类型（类型实参）。
 * <p>
 * 泛型的本质仅仅是 泛型匹配检查，与返回类型强转，对于方法内部并不做任何处理
 * <p>
 * 操作的数据类型被指定为一个参数，这种参数类型可以用在类、接口和方法中，
 * 分别被称为泛型类、泛型接口、泛型方法。
 * <p>
 * 在泛型类型编译之后程序会采取去泛型化的措施,
 * 1.泛型保留：对于声明了具体的泛型的属性而言（private List<String> lis），因为其他类需要使用该属性所以编译后依旧保存泛型信息用于在其他类中检查与强转
 * 2.泛型擦除：首先，对于在泛型类或者泛型方法中的声明的泛型(T) 在编译后会被替换成其父类（如果没有extends,默认父类是Object）。这样泛型类和泛型方法就
 * 不再带有泛型（但是保留 泛型记录，用于匹配与强转）。
 * 其次，对于在方法内的声明的泛型对象（List<String>）来说，传入泛型并不需要具体的类型，T也可以，因为在编译的时候就可以知道具体的类型。
 * 在编译过程中，泛型记录匹配<>内具体的类型（泛型方法是自动判断匹配） 然后在进入和离开方法的边界处根据泛型记录添加类型匹配检查和类型强转之后，
 * 方法内部并不会根据泛型记录进行类型匹配检查和类型强转
 * 会将<>的相关信息擦除(List)，编译完成后 泛型对象其实是没有泛型的对象
 * 从此 泛型类、泛型方法、泛型对象都进行了泛型擦除，不再保留泛型信息，泛型信息只在编译阶段有效
 * <p>
 * 因为泛型擦除 无法在运行时获取具体的泛型类型信息(T匹配的是什么)，但是可以获取泛型对象的信息（class）
 * <p>
 * 例如：在方法中创造了ArrayList<String> lis，
 * lis.add(..)
 * lis.get()
 * 在编译中会进行add(String) 入口检查 与 (String)get强转后
 * 会将<String>擦除 创造的lis其实就是 一个没有泛型的ArrayList
 * （其实ArrayList的类编译后泛型会被替换成Object，从而不带有泛型信息）,这样就形成了一统，成功的
 * 将泛型擦除
 * <p>
 * 对此总结成一句话：泛型类型在逻辑上看以看成是多个不同的类型，实际上都是相同的基本类型。
 * <p>
 * 通配符
 * 1.<?> 被称作无限定的通配符。
 * ? 并不是声明的泛型而是具体的泛型信息，只是不知道是什么泛型信息而已，他可以匹配任意的类型
 * <?> 提供了只读（往外读出）的功能，也就是它删减了增加具体类型元素的能力，只保留与具体类型无关的功能。
 * 它不管装载在这个容器内的元素是什么类型，它只关心元素的数量、容器是否为空？
 * <p>
 * 2.<? extends T> 被称作有上限的通配符。
 * <?> 代表着类型未知，但是我们的确需要对于类型的描述再精确一点，
 * 我们希望在一个范围内确定类别，比如类型 T 及 类型 T 的子类都可以
 * <p>
 * 3.<? super T> 被称作有下限的通配符。
 * <? super T> 神奇的地方在于，它拥有一定程度的写操作（往里写入）的能力
 * 但是没有读的能力
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
    }

    static <T> T show() {
        T i = (T) new Date();
        return (T) "123";
    }
}