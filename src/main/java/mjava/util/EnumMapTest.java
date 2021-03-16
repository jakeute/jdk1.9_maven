package mjava.util;

import java.util.EnumMap;

/**
 * 需要指定 enum类
 */
public class EnumMapTest {
    public static void main(String[] args) {
        EnumMap<EnumSetTest.Dog,String> enumMap = new EnumMap<>(EnumSetTest.Dog.class);
        enumMap.put(EnumSetTest.Dog.pas,"xxxx");
    }
    enum Dog{
        pxj,ww,pas,we
    }
}


