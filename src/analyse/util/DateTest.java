package analyse.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) throws ParseException {
        Date date = new Date();

        //SimpleDateFormat
        //yyyy-MM-dd分别代表了日期时间中的年-月-日。如果想获取到更加精确的时间，例如24小时制的时-分-秒，便可以在参数中加入对应的HH:mm:ss，
        // 用来代表对应的时、分、秒
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

        //date -- 字符串
        String format = simpleDateFormat.format(date);
        //字符串 -- date
        Date parse = simpleDateFormat.parse(format);


        System.out.println(format);
        System.out.println(parse);
    }
}
