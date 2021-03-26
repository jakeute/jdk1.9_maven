package mjava.util;

import java.util.Comparator;

/**
 * 在jdk8后接口引入了默认方法 comparator提供了一些十分方便的方法
 * 可以快速生成Comparator接口实现
 */
public class ComparatorTest {
    public static void main(String[] args) {
        Comparator<Student> studentComparator = Comparator.comparingInt(Student::getAge).
                thenComparing((o1, o2) -> o1.password.compareTo(o2.password));
    }
}

class Student{
    int age;
    String password;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}