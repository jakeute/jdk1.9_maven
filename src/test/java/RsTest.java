import org.junit.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Scanner;

public class RsTest {

    @Test
    public void test1() throws FileNotFoundException, ParseException {
        String b = "{\"address\":\"1213\",\"brandIds\":[7569006],\"city\":\"北京市\",\"contactName\":\"齐永强\",\"contactWay\":\"15657181526\",\"coopActorNum\":64,\"coopType\":0,\"deliveryAddress\":\"hangzhou gudun road no 99, 浙商财富中心\",\"deliveryDesc\":\"3\",\"district\":\"东城区\",\"exprssName\":\"meili-inc\",\"extra\":{\"itemResource\":[694013223,693208010,693208210,695871600],\"lng\":116.41637,\"lat\":39.92855},\"hatchDesc\":\"ces\",\"id\":1052,\"isHatchActor\":1,\"isShelfItemNum\":143,\"itemUpdated\":1589009071,\"liveStudioNum\":2,\"mainCategory\":\"0\",\"mgjContactName\":\"其他\",\"moGuUserId\":305538353,\"name\":\"heishitest059\",\"operationDesc\":\"这家很厉害11\",\"picPath\":\"\",\"province\":\"北京市\",\"recommendDesc\":\"1212\",\"shopStatus\":1,\"status\":0,\"type\":3,\"videoPath\":\"\",\"wxPhone\":\"15657181526\"}";
        String a = "{\"address\":\"1213\",\"brandIds\":[7569006],\"city\":\"北京市\",\"contactName\":\"齐永强\",\"contactWay\":\"15657181526\",\"coopActorNum\":64,\"coopType\":0,\"deliveryAddress\":\"hangzhou gudun road no 99, 浙商财富中心\",\"deliveryDesc\":\"3\",\"district\":\"东城区\",\"exprssName\":\"meili-inc\",\"extra\":{\"itemResource\":[694013223,693208010,693208210,695871600],\"lng\":116.41637,\"lat\":39.92855},\"hatchDesc\":\"ces\",\"id\":1052,\"isHatchActor\":1,\"isShelfItemNum\":143,\"itemUpdated\":1589009071,\"liveStudioNum\":2,\"mainCategory\":\"0\",\"mgjContactName\":\"其他\",\"moGuUserId\":305538353,\"name\":\"heishitest059\",\"operationDesc\":\"这家很厉害11\",\"picPath\":\"\",\"province\":\"北京市\",\"recommendDesc\":\"1212\",\"shopStatus\":1,\"status\":0,\"type\":3,\"videoPath\":\"\",\"wxPhone\":\"15657181526\"}";
        System.out.println(a.equals(b));
    }
}


class Nhuanghouwenti {
    private static int queenNum;//皇后的个数
    private static int[] hash;//下标表示i号皇后（皇后i放在第i行）value表示放的列号
    private static int count = 0;//合法摆放方式的个数

    public static void placeQueen(int m) {
        if (m > queenNum) {//如果摆到了n+1行了，说明前n行都是不冲突的，合法的
            count++;
            for (int i = 1; i <= queenNum; i++) {
                System.out.print(hash[i]);
            }
            System.out.println();
            return;
        }
        for (int i = 1; i <= queenNum; i++) {
            //check the column is conflict with former ones or not
            //if so, check the next column until find a non-conflict column
            //or until the last column ,return;
            if (isConfilct(m, i)) {
                continue;
            } else {//如果检测到第i列不冲突，是安全的，
                hash[m] = i;//将皇后m放在第i列
                placeQueen(m + 1);//再放皇后m+1,
                //如果皇后m+1放完并返回了
                //两种可能：
                //1：冲突，返回了
                //2.一直将所有的皇后全部放完并安全返回了
                //将皇后m回溯，探索新的可能或者安全的位置
                hash[m] = -1;
                //其实这里没必要将m重新赋值的，因为检测到下一个
                //安全位置的时候会把hash[m]覆盖掉的
                //但是为了更好的体现“回溯”的思想，在这里画蛇添足了
            }
        }
    }

    /**
     * 检测冲突
     *
     * @param index 表示行号
     * @param hash  表示第i行放置皇后的列数
     *              值表示列号
     * @return
     */
    private static boolean isConfilct(int row, int column) {  //一行一个皇后，第n个皇后也代表着第n行
        if (row == 1) {//第1行永远不会冲突
            return false;
        }
        //只需要保证与那些已经就位的皇后不冲突即可
        for (int i = 1; i < row; i++) {  //当前的行数
            if (hash[i] == column || (column - row) == (hash[i] - i) || (row - column) == (i - hash[i])   //以前行数减列数与现在的是否相等
                    || (row + column) == (hash[i] + i)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        queenNum = sc.nextInt();
        hash = new int[queenNum + 1];
        for (int i = 0; i < hash.length; hash[i++] = -1) ;//初始化棋盘
        placeQueen(1);
        System.out.println(count);
    }

}

