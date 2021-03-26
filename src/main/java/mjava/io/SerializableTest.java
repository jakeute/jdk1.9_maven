package mjava.io;

import java.io.*;
import java.util.Objects;

/**
 * 序列化：指堆内存中的java对象数据，通过某种方式把对存储到磁盘文件中，或者传递给其他网络节点（网络传输）。
 * 这个过程称为序列化，通常是指将数据结构或对象转化成二进制的过程。
 * <p>
 * 反序列化：把磁盘文件中的对象数据或者把网络节点上的对象数据，恢复成Java对象模型的过程。
 * 也就是将在序列化过程中所生成的二进制串转换成数据结构或者对象的过程
 */
public class SerializableTest {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setId(6);
        user.setName("lqx");
        user.setPwd("hha");


        saveObject(user);

        Object o = readObject();
        System.out.println(o);
        System.out.println(user);
    }

    // 保存对象，序列化
    public static void saveObject(Object object) throws Exception {
        ObjectOutputStream out = null;
        FileOutputStream fout = null;
        try {
            File file = new File("se.txt");
            fout = new FileOutputStream(file);
            out = new ObjectOutputStream(fout);
            out.writeObject(object);
        } finally {
            out.close();
            fout.close();
        }
    }

    // 读取对象，反序列化
    public static Object readObject() throws Exception {
        ObjectInputStream in = null;
        FileInputStream fin = null;
        try {
            File file = new File("se.txt");
            fin = new FileInputStream(file);
            in = new ObjectInputStream(fin);
            return in.readObject();
        } finally {
            in.close();
            fin.close();
        }
    }
}

/**
 * 这个序列化ID起着关键的作用，它决定着是否能够成功反序列化！java的序列化机制是通过判断运行时类的serialVersionUID来验证版本一致性的，
 * 在进行反序列化时，JVM会把传进来的字节流中的serialVersionUID与本地实体类中的serialVersionUID进行比较，如果相同则认为是一致的，
 * 便可以进行反序列化，否则就会报序列化版本不一致的异常。
 * 当我们一个实体类中没有显式的定义一个名为“serialVersionUID”、类型为long的变量时，Java序列化机制会根据编译时的class自动生成
 * 一个serialVersionUID作为序列化版本比较，这种情况下，只有同一次编译生成的class才会生成相同的serialVersionUID。
 * 譬如，当我们编写一个类时，随着时间的推移，我们因为需求改动，需要在本地类中添加其他的字段，这个时候再反序列化时便会出现
 * serialVersionUID不一致，导致反序列化失败。
 * <p>
 * <p>
 * 注意：一个对象在进行序列化后并不会被销毁，依然存在
 * <p>
 * 该类必须实现 java.io.Serializable 接口。
 * 该类的所有属性必须是可序列化的。如果有一个属性不是可序列化的，则该属性必须注明是短暂的（transient）。
 * 静态变量不会被序列化（static）
 * 当一个父类实现序列化，子类自动实现序列化，不需要显式实现Serializable接口。
 * 当一个对象的实例变量引用其他对象，序列化该对象时也把引用对象进行序列化。
 */
class User implements Serializable {
    static int p = 5;

    private static final long serialVersionUID = -9059724963506809277L;

    private int id;
    private String name;
    private String password;
    private transient String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(password, user.password) && Objects.equals(pwd, user.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, pwd);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", pwd='" + pwd + '\'' +
                '}' + p;
    }
}


class Student extends User {
    String offer;
    double price;

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Double.compare(student.price, price) == 0 && Objects.equals(offer, student.offer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), offer, price);
    }
}