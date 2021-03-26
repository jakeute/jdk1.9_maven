package mjava.problem;

import java.util.Arrays;

/**
 * clone意思是克隆、复制。在Java语言中，当对象调用clone()方法时，就会复制已有的对象。clone()方法在根类Object中定义如下。
 * protected native Object clone() throws CloneNotSupportedException;
 * 从上面对clone方法的注解可知clone方法的通用约定：对于任意一个对象x，
 * 表达式①x.clone != x将会是true；
 * 表达式②x.clone().getClass()==x.getClass()将会是true，但不是绝对的。通常情况下，
 * 表达式③x.clone().equals(x)将会是true，但是这也不是绝对的。
 * 从源代码可知，根类Object的clone方法是用protected关键字修饰，这样做是为避免我们创建每一个类都默认具有克隆能力。这样做造成的后果就是：
 * 对于那些简单使用一下这个类的客户程序员来说，他们不会默认使用这个方法；其次，我们不能利用指向基础类的一个句柄来调用clone方法。
 * 在编译期间，实际上是通知我们对象不可克隆的一种方式
 *
 *
 * 根类Object中的clone()方法负责建立正确的存储容量，并通过“按位复制”将所有二进制从原始对象中复制到新对象的存储空间。
 * 也就是说，它并不只是预留存储空间以及复制一个对象--实际需要调查出欲复制的新对象准确大小，然后再复制那个对象。
 * 由于这些工作都是由根类定义的clone()方法内部代码进行的，这个过程需要用RTTI（运行时类型鉴定）来判断欲克隆对象的实际大小。
 * 采用这种方式，clone()方法便可建立起正确数量的存储空间，并对那个类型进行正确的按位复制。
 *
 * 克隆过程的第一个部分通常都应该是调用super.clone()。通过进行一次准确的复制，这样做可为后续的克隆进程建立起一个良好的基础。
 * 随后，可采取另一些必要的操作，以完成最终的克隆。
 *
 * 需要注意的是 由于clone是将二进制数据复制到一个新的存储空间，新的对象内的引用与原对象内的引用一样，称为浅复制。
 * 另外 java内的数组都实现了 clone 方法
 *
 * 总之，所有实现了Cloneable接口的类都应该用一个公有的方法覆盖clone方法。此方法首先调用super.clone，然后修正任何需要修正的域。
 * 一般情况下，这意味着要拷贝任何包含内部“深层结构”的可变对象，并用指向新对象的引用代替原来指向这些对象的引用。
 * 如果该类只包含基本类型的域，或者指向不可变对象的引用，那多半的情况下是没有域需要修正的。
 *
 * 其实实现Cloneable接口具有很多问题，很多接口都不应该扩展这个接口，也不应该实现这个接口。
 * 因此很多程序员从来不去覆盖clone方法，也从来不去调用它，除非拷贝数组。所以，慎重选择覆盖clone()方法。
 */
public class CloneProblem {

    public static void main(String[] args) throws CloneNotSupportedException {
        User user1 = new User();
        user1.setAge(5);
        user1.setName("lqx");
        System.out.println("user1:"+user1);

        System.out.println("user2"+user1.clone());

        Object[] objects1 = new Object[5];
        objects1[0]="lqx";objects1[1]=5;

        Object[] objects2 = objects1.clone();

        System.out.println(Arrays.toString(objects2));
    }
}

/**
 * 要使类具有克隆能力能力时，需要实现Cloneable接口，实现它的目的是作为一个对象的一个mixin(混入)接口，表明这个对象是允许克隆的
 * Cloneable是一个空接口(标记接口)，它决定了Object中受保护的clone方法的实现行为：
 * 如果一个类实现了Cloneable接口，Object的clone方法就返回这个对象的逐域拷贝，否则就抛出CloneNotSupportedException异常。
 * 如果实现了这个接口，类和它所有的超类都无需调用 构造器 就可以创建对象。
 *
 * 其次由于Object.clone()是protected，无法被外部调用，需要重写方法
 */
class User implements Cloneable {
    public User() {
        System.out.println("...");
    }

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public User clone() throws CloneNotSupportedException {
        return (User) super.clone();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}'+super.toString();
    }
}