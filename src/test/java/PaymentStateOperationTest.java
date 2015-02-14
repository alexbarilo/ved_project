import junit.framework.Assert;
import model.InvoicesBean;
import org.junit.BeforeClass;
import org.junit.Test;
import util.PaymentStateOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentStateOperationTest {

    static PaymentStateOperations stateOperations;

    @BeforeClass
    public static void setupState() {
        stateOperations = new PaymentStateOperations();
    }

    @Test
    public void getPaymentStateTest() {
        Assert.assertEquals("paid", stateOperations.getPaymentState(1.00f, 1.00f));
        Assert.assertEquals("overdue", stateOperations.getPaymentState(-1.00f, -1.00f));
        Assert.assertEquals("- - -", stateOperations.getPaymentState(1.00f, -1.00f));
    }

    @Test
    public void getDueStateTest() {
        String date = "2014-02-15";
        Assert.assertTrue(stateOperations.getDueState(date) == (float) stateOperations.getDueState(date));
    }

    @Test
    public void setPaymentSateInBeanTest() {
        List<InvoicesBean> initialListOfInvoices = new ArrayList<InvoicesBean>();
        InvoicesBean bean1 = new InvoicesBean();
        InvoicesBean bean2 = new InvoicesBean();
        InvoicesBean bean3 = new InvoicesBean();

        bean1.setDate("2015-02-10");
        bean1.setDueDate("2015-03-10");
        bean1.setAmount(100.00f);
        bean2.setDate("2014-06-10");
        bean2.setDueDate("2014-07-10");
        bean2.setAmount(100.00f);
        bean3.setDate("2014-10-12");
        bean3.setDueDate("2014-11-12");
        bean3.setAmount(100.00f);

        initialListOfInvoices.add(bean1);
        initialListOfInvoices.add(bean2);
        initialListOfInvoices.add(bean3);

        List<InvoicesBean> listOfInvoices = stateOperations.setPaymentStateInBean(initialListOfInvoices, 150.00f);

        Assert.assertTrue(listOfInvoices.get(0).getPaymentState() == "paid");
        Assert.assertTrue(listOfInvoices.get(1).getPaymentState() == "overdue");
        Assert.assertTrue(listOfInvoices.get(2).getPaymentState() == "- - -");
    }
}
