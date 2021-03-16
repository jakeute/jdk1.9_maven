package mjava.problem;

/**
 * 解决内部类的问题
 * 成员内部类、局部内部类、匿名内部类和静态内部类
 */
public class InnerClassProblem {
    public static void main(String[] args) {
        //对于成员内部类先创建外部类才能创建内部类
        OutClass_1.InnerClass_1 innerClass_1 = new OutClass_1().new InnerClass_1();
        //直接创建外部类的静态内部类
        new OutClass_2.InnerClass_2();
        int i = 1;

        // 匿名内部类使用的最多 可以视作一种继承了一个类或者实现了一个接口的局部内部类
        // 匿名内部类被视为一种对现有的类的加强（子类），可以在当前类的基础上增加属性、方法，改写现有方法
        // 使用匿名内部类大大减少了创建类的数量
        // 匿名内部类不能定义任何静态成员和静态方法。当所在的方法的 变量 需要被匿名内部类使用时，必须声明为 final。
        //：对于普通局部变量他的作用域就是该方法内，当方法结束该局部变量就随之消失；但局部内部类可能产生隐式的“闭包”，
        // 闭包将使得局部变量脱离他所在的方法继续存在。由于内部类可能扩大局部变量的作用域，如果再加上这个被内部类访问的局部变量没有使用final修饰，
        // 也就是说这个变量的值可以随时改变，但此变量可能已经弹栈不能修改了，因此java编译器要求所有被内部类访问的局部变量必须使用final修饰符修饰。

        OutClass_1 outClass_1 = new OutClass_1() {
            @Override
            public void see() {
                super.see();
                System.out.println(i);
            }
        };
    }
}

/**
 * 成员内部类
 * 定义在类内部，成员位置上的非静态类，就是成员内部类。
 * 成员内部类可以访问外部类所有的变量和方法，包括静态和非静态，私有和公有。
 * 成员内部类依赖于外部类的实例(内部类的属性有一项是外部类对象的引用)，
 * 可以用OutClass.this 来获取外部类的引用
 * <p>
 * 但是内部类不能有静态声明
 * 外部类创建了内部类的实例，则可以访问内部类类所有公有私有的变量和方法。
 * <p>
 * 所以成员内部类可以看成外部类的一种扩展（成员方法），将一部分的变量与方法单独迁到一个类中
 * 两者可以无障碍的任意访问，通常成员内部类被当作外部类实现接口的一种实现类
 * <p>
 * 成员内部类可以添加访问权限控制，代表能否被其他类创建识别
 */
class OutClass_1 {
    private static int i = 1;
    private String name;
    private InnerClass_1 class_1;

    class InnerClass_1 {
        //static int u;
        private void shwo() {
            i = 1;
            name = "14";
            class_1 = null;
        }
    }

    private class InnerClass_2 {
        private void shwo() {
            i = 1;
            name = "14";
            class_1 = null;
        }
    }

    public void see() {
        class_1.shwo();
    }
}

/**
 * 静态内部类
 * 静态内部类没有外部类对象的引用，所以并不依赖于外部对象而创建
 * 因此静态内部类无法操作外部类的非静态属性方法。
 * 静态内部类可以有自己的静态方法和变量
 * <p>
 * 外部类、内部类可以相互访问公有私有的静态属性、方法
 * 在创建对象后可以相互访问公有私有的成员属性、方法
 * <p>
 * 可以将静态内部类看作是一个在外部类内部的无关类（static方法）,在创建对象后就可以相互访问
 * 通常将一些与外部类相关的静态方法、变量放入到静态内部类中
 * <p>
 * 可以添加权限控制
 */
class OutClass_2 {
    private static int i = 1;
    private String name;

    private void see() {
        new InnerClass_2().k();
    }

    static class InnerClass_2 {
        static int u;

        private void shwo() {
            i = 1;
            //name = "14";
            new OutClass_2().see();
        }

        private void k() {

        }

    }

    static void shwo() {
        i = 45;
    }

    public static void main(String[] args) {

        //因为 psvm 是一个类的 static 方法，对于 static 方法，能直接访问类的static变量 而且不用加类名限定
        System.out.println(i);
        //同理 静态内部类也不需要添加类名限定 但是在其他类的使用上需要添加
    }
}

/**
 *局部内部类就是创建在方法内部的类，相比成员内部类能访问当前方法内的final变量
 * 能访问的本质是在创建内部类的时候会创建相同的访问类型的成员类型并且将访问的变量传入
 * 为了数据一致，访问的变量不能改变。
 * 如果方法是是static 则不能访问外部类的成员属性和方法
 */
class OutClass_3 {
    private static int i = 1;
    private String name;

    private void z() {

    }

    void see() {
        int a = 3;
        class p {
            void show() {
                System.out.println(a);
                //a=5;
                System.out.println(name);
                z();
            }
        }
    }

    static void k() {
        class u {
            void show() {
                //System.out.println(name);
            }
        }
    }

    public static void main(String[] args) {
        OutClass_2.InnerClass_2 innerClass_2 = new OutClass_2.InnerClass_2();
    }
}

