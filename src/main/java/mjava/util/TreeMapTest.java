package mjava.util;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * TreeMap 是按照红黑树实现的
 * key 需要实现Comparable 或者通过提供的 Comparator（比较器）
 * 对于Comparator/Comparable的逻辑要明确，不能出现不确定的结果，这能确保节点在树中左右位置是确定的
 * 否则就会出现插入时在左边，查找时却向右查找
 * <p>
 * 如果比较得到-1 则左节点
 * 如果比较得到1 则右节点
 * 如果比较得到0 则更换value
 */
public class TreeMapTest {
    public static void main(String[] args) {
        /*
          此时的Comparator先比较b b小的在左边 b大的在右面
          相同的话在比较p  ，此时的逻辑十分明确，无论是插入还是查找结果都不会出现 二相性
         */
        TreeMap<yy, Integer> map = new TreeMap<>(new Comparator<yy>() {
            @Override
            public int compare(yy o1, yy o2) {
                if(o1.b>o2.b){
                    return 1;
                }else if (o1.b < o2.b) {
                    return - 1;
                }else {
                    return o1.p.compareTo(o2.p);
                }
            }
        });

        map.put(new yy("aa",5),1);
        map.put(new yy("ab",4),1);
        map.put(new yy("bb",6),10);
        map.put(new yy("ab",5),1);

        System.out.println(map);

        System.out.println(map.get(new yy("bb", 3)));
    }
}

class yy{
    String p;
    int b;

    @Override
    public String toString() {
        return "yy{" +
                "p='" + p + '\'' +
                ", b=" + b +
                '}';
    }

    public yy(String p, int b) {
        this.p = p;
        this.b = b;
    }
}
