package mjava.lang;

public class MathTest {
    public static void main(String[] args) {
        System.out.println(Math.max(4,6));
        System.out.println(Math.min(4,6));
        //四舍五入
        System.out.println(Math.round(3.5));
        System.out.println(Math.round(-3.5));
        //向下取整
        System.out.println(Math.floor(3.5));
        System.out.println(Math.floor(-3.5));
        //ceil 向上取整
        System.out.println(Math.ceil(3.5));
        System.out.println(Math.ceil(-3.5));
        //sqrt 开方
        System.out.println(Math.sqrt(6));

        //幂函数
        System.out.println(Math.pow(2,4));

        //绝对值
        System.out.println(Math.abs(-9));
    }
}
