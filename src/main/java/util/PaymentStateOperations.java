package util;

import model.InvoicesBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PaymentStateOperations {

    public String getPaymentState (float dueState, float sumBalance) {
        if (sumBalance >= 0) {return "paid";}
        else if (sumBalance < 0 && dueState < 0) {
            return "overdue";
        }
        return "- - -";
    }

    public float getDueState (String dueDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        long dueDateInLong = 0L;
        long currentDate = 0L;
        long result = 0L;

        try {
            dueDateInLong = simpleDateFormat.parse(dueDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        currentDate = new Date().getTime();
        result = dueDateInLong - currentDate;
        return result;
    }

    public List<InvoicesBean> setPaymentStateInBean(List invoicesData, float sumBalance) {
        float dueState;
        float currentInvoiceAmount;
        float sumBalanceAfterIteration = sumBalance;
        String paymentState;

        Collections.sort(invoicesData, new BeanSortByDate());

        Iterator iterator = invoicesData.iterator();

        while (iterator.hasNext()) {
            InvoicesBean invoicesBean = (InvoicesBean) iterator.next();
            dueState = getDueState(invoicesBean.getDueDate());
            currentInvoiceAmount = invoicesBean.getAmount();
            sumBalanceAfterIteration = sumBalanceAfterIteration - currentInvoiceAmount;
            paymentState = getPaymentState(dueState, sumBalanceAfterIteration);
            invoicesBean.setPaymentState(paymentState);
        }

        return invoicesData;
    }

}
