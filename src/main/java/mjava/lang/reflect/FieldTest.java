package mjava.lang.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Field类：代表类的成员变量（成员变量也称为类的属性） 可以获得变量的类型 泛型 变量名
 * <p>
 * public void set(Object obj,Object value)
 * 将指定对象参数上的此Field对象表示的字段设置为指定的新值。 如果基础字段具有原始类型，则新值将自动展开。
 * public void get(Object obj) 获取obj上的字段的值
 * <p>
 * 如果基础字段是静态的，则忽略obj参数; 为null。
 * 如果为私有字段 则调用前需要 setAccessible(true);
 * <p>
 * 如果为基本类型可以 setInt setDouble ..设置
 */
public class FieldTest {
    int a;
    double[] p;
    Set<String> strings;

    public static void main(String[] args) throws IllegalAccessException {
        Field[] declaredFields = FieldTest.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {

            //getGenericType 得到这个属性的具体类型Type(可以携带泛型信息)
            //ParameterizedType 这个type是参数化 能获取泛型信息

            Type genericType = declaredField.getGenericType();
            if (genericType instanceof Class) {
                System.out.println("class " + declaredField + " : " + genericType);
            }
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = ((ParameterizedType) genericType);
                System.out.println("------------------");

                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                Type rawType = parameterizedType.getRawType();

                System.out.println(rawType.getTypeName() + " : " + (rawType instanceof Class) + " : " + rawType.getClass());

            }
        }

//        declaredFields[0].setInt(fieldTest,5);
    }
}
