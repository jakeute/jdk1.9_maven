package analyse.lang;

/**
 * 所有的枚举类都继承Enum<E extends Enum<E>> 这种泛型代表的是 E必须是Enum的子类
 * 枚举类中的枚举都是public static final 的类对象  枚举类的构造函数是私有的
 * 枚举类可以有方法，并且枚举对象可以重写方法
 */
public class EnumTest {

}

enum Color {
    RED, GREEN, BLANK, YELLOW() {
        @Override
        void show() {
            super.show();
            Runnable runnable = () -> System.out.println("hhaha");
        }
    };

    void show() {
        System.out.println("h");
    }
}
