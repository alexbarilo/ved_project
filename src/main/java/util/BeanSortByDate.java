package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

public class BeanSortByDate implements Comparator<BeansInterface> {

    final long DAY_FACTOR = 1000L * 60L * 60L * 24L;
    @Override
    public int compare(BeansInterface o1, BeansInterface o2) {

        long o1DateInLong = 0L;
        long o2DateInLong = 0L;
        int result;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            o1DateInLong = simpleDateFormat.parse(o1.getDate()).getTime();
            o2DateInLong = simpleDateFormat.parse(o2.getDate()).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result = (int) ((o1DateInLong - o2DateInLong) / DAY_FACTOR);

        return result;
    }
}
