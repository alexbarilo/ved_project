import junit.framework.Assert;
import model.PaymentsBean;
import org.junit.Before;
import org.junit.Test;
import util.BeanSortByDate;
import util.BeansInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BeanSortByDateTest {

    PaymentsBean beanToTest1;
    PaymentsBean beanToTest2;
    PaymentsBean beanToTest3;
    List<BeansInterface> listOfPayments = new ArrayList<BeansInterface>();

    @Before
    public void setupBeans() {
        beanToTest1 = new PaymentsBean();
        beanToTest2 = new PaymentsBean();
        beanToTest3 = new PaymentsBean();

        beanToTest1.setDate("2014-02-15");
        beanToTest2.setDate("2014-05-15");
        beanToTest3.setDate("2014-03-15");

        listOfPayments.add(beanToTest1);
        listOfPayments.add(beanToTest2);
        listOfPayments.add(beanToTest3);
    }

    @Test
    public void compareTest() {
        Collections.sort(listOfPayments, new BeanSortByDate());
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        long currentDate = 0L;
        long nextDate = 0L;
        for (int i = 0; i < listOfPayments.size() - 1; i++) {
            try {
                currentDate = formatDate.parse(listOfPayments.get(i).getDate()).getTime();
                nextDate = formatDate.parse(listOfPayments.get(i + 1).getDate()).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Assert.assertTrue(currentDate < nextDate);
        }
    }
}
