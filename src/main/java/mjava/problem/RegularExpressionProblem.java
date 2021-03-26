package mjava.problem;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式强调的是匹配，是字符串中是否有与正则表达式相匹配的部分
 * <p>
 * 特殊符号
 * <p>
 * \b   匹配一个字边界，即字与空格间的位置。例如，"er\b"匹配"never"中的"er"，但不匹配"verb"中的"er"。
 * \B   非字边界匹配。"er\B"匹配"verb"中的"er"，但不匹配"never"中的"er"。
 * \d	匹配 0~9 的所有数字
 * \D	匹配非数字
 * \s	匹配所有的空白字符，包括空格、制表符、回车符、换页符、换行符等
 * \S	匹配所有的非空白字符
 * \w	匹配所有的单词字符，包括 0~9 所有数字、26 个英文字母和下画线_
 * \W	匹配所有的非单词字符
 * <p>
 * $	匹配一行的结尾。要匹配 $ 字符本身，请使用\$
 * ^	匹配一行的开头。要匹配 ^ 字符本身，请使用\^
 * ()	标记子表达式的开始和结束位置。要匹配这些字符，请使用\(和\)
 * []	用于确定中括号表达式的开始和结束位置。要匹配这些字符，请使用\[和\]
 * [abc] 表示字母abc中的任意一个
 * [a-z] 表示字母a到z中的任意一个
 * [^abc] ^ 为取非， 除了abc， 剩下的全部字符
 * [a-cx-z]，表示 a~c、x~z 范围内的任意字符
 * {}	用于标记前面子表达式的出现频度。要匹配这些字符，请使用\{和\}
 * {n} 表示正好n个
 * {n,m} 表示n到m个，这是一个左闭右闭区间
 * {n,} 表示至少n个
 * *	指定前面子表达式可以出现零次或多次。要匹配 * 字符本身，请使用\*
 * +	指定前面子表达式可以出现一次或多次。要匹配 + 字符本身，请使用\+
 * ?	指定前面子表达式可以出现零次或一次。要匹配 ？字符本身，请使用\?
 * .	匹配除换行符\n之外的任何单字符。要匹配.字符本身，请使用\.
 * \	用于转义下一个字符，或指定八进制、十六进制字符。如果需匹配\字符，请用\\
 * |	指定两项之间任选一项。如果要匹配丨字符本身，请使用\|
 * <p>
 * 需要匹配所有的中文字符，就可以利用 [\\u0041-\\u0056]
 * <p>
 * Greedy（贪婪模式）：数量表示符默认采用贪婪模式，除非另有表示。贪婪模式的表达式会一直匹配下去，直到无法匹配为止。
 * Reluctant（勉强模式）：用问号后缀（?）表示，它只会匹配最少的字符。也称为最小匹配模式
 * ?? 表示1个或0个。换句话说，表示要不然没有，要不然只有1个
 * *? 表示0个或多个。
 * +? 表示1个或多个。
 * {n}? 表示正好n个
 * {n,m}? 表示n到m个，这是一个左闭右闭区间
 * {n,}? 表示至少n个
 *
 *
 *
 *
 * 在String中 \与之后的一个字符 表示为转义字符（\n \t），其本质是一个无法表达的char,在正则表达式中不需要转化
 * 两个\\ 代表的并不是转义字符 只是一个 ’\‘ 字符
 * 在Java中正则表达式需要用String表示，如果想正确的表达 ’\wabc‘ 就需要写成 ‘\\wabc’ 此时sout后就是正确的正则表达式
 * 所以流程是 编写正则表达式-》String
 *
 */
public class RegularExpressionProblem {
    public static void main(String[] args) {

        //想要在String中正确表示正则表达式  并且输出 就需要写成 ‘\\wabc’
        //此时sout后就是正确的正则表达式
        System.out.println("\\wabc");

        String p = "abcer{a\n";

        System.out.print(p.replaceFirst("\\w{3,}", "123"));
    }

    /**
     * Pattern 类：
     * pattern 对象是一个正则表达式的编译表示。Pattern 类没有公共构造方法。要创建一个 Pattern 对象，你必须首先调用其公共静态编译方法，
     * 它返回一个 Pattern 对象。该方法接受一个正则表达式作为它的第一个参数。
     * compile  返回一个 Pattern 对象
     * matches  尝试将字符串与正则表达式全匹配。部分匹配返回false
     * <p>
     * Matcher 类：
     * Matcher 对象是对输入字符串进行解释和匹配操作的引擎。与Pattern 类一样，Matcher 也没有公共构造方法。
     * 你需要调用 Pattern 对象的 matcher 方法来获得一个 Matcher 对象。
     * <p>
     * group(int)  代表 正则表达式 的分组    捕获组是通过从左至右计算其开括号来编号
     * 还有一个特殊的组（group(0)），它代表整个正则表达式。该组不包括在 groupCount 的返回值中。
     * <p>
     * 1    public int start()
     * 返回当前 部分匹配 的group0的开始索引。
     * 2	public int start(int group)
     * 返回当前 部分匹配 的groupN的开始索引。
     * 3	public int end()
     * 返回当前 部分匹配 的group0的 最后索引+1。
     * 4	public int end(int group)
     * 返回当前 部分匹配 的groupN的最后索引+1。
     * <p>
     * 适用于subString(int,int)
     * <p>
     * 1	public boolean lookingAt()
     * 从字符串的开头与正则表达式进行部分匹配检测。
     * 2	public boolean find()
     * 尝试查找是否存在下一个部分匹配。
     * 3	public boolean find(int start）
     * 从指定索引开始寻找下一个部分匹配。
     * 4	public boolean matches()
     * 尝试将字符串与正则表达式全匹配。部分匹配返回false。
     */
    @Test
    public void trys() {
        //构造函数
        String f = "123456";
        Pattern pattern = Pattern.compile("\\w(\\w(\\w(\\w))(\\w))");
        Matcher matcher = pattern.matcher(f);
        System.out.println(Pattern.matches("\\w\\w(\\w(\\w(\\w))(\\w))", f));
        System.out.println(matcher.matches());
        if (matcher.find(1)) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            //System.out.println(matcher.group(5));
            System.out.println(matcher.start(1));
            System.out.println(matcher.end(2));
        }
    }

    @Test
    public void test2(){
        String p= " 14536we";
        Pattern compile = Pattern.compile("\\w\\w");
        System.out.println(compile.matcher(p).lookingAt());
    }
}
