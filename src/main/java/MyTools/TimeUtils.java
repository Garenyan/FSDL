package MyTools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by garen on 2019/4/8.
 */
public class TimeUtils {
    public static String getTimeAndDateStringFormat(Date date,String pattern){
        DateFormat df3 = new SimpleDateFormat(pattern);
        return df3.format(date);
    }

    public static String getTimeAndDateStr(Date date){
        return getTimeAndDateStringFormat(date,"yyy-MM-dd HH-mm-ss");
    }

}
