package analyse.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Calendar类是日历类，提供操作日历字段的方法
 */
public class CalendarTest {
    public static void main(String[] args) {
        //获取对象 无法用new
        Calendar calendar = Calendar.getInstance();

        //public Date getTime() ：得到当前日历时间，返回Date类型时间
        //public void setTime(Date date) ：使用给定的Date设置日历时间
        // void add(int field,int amount) ：按照日历的规则，给指定字段添加或减少时间量。当amount为负数时代表减少指定的时间量
        //public void set(int field, int value)：给日历类的指定字段赋值

        Date end = calendar.getTime();
        System.out.println(end);
        calendar.add(Calendar.MONTH, -1);
        Date begin = calendar.getTime();
        System.out.println(begin);

        System.out.println(findDates(begin, end));
    }

    // 根据给出的开始日期和结束日期，得到这段时间内每一天的具体日期
    public static List<String> findDates(Date dBegin, Date dEnd) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        List<String> lDate = new ArrayList<>();
        lDate.add(format.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            String tempDate = format.format(calBegin.getTime());
            lDate.add(tempDate);
        }
        return lDate;
    }
}
