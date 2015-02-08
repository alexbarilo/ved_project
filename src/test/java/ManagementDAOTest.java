import junit.framework.Assert;
import model.ContractorBean;
import model.InvoicesBean;
import model.PaymentsBean;
import org.junit.BeforeClass;
import org.junit.Test;
import service.ManagementDAO;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ManagementDAOTest {

    private static String contractorName;
    private static String contractNumber;
    private static String invoiceNumber;
    private static String invoiceDate;
    private static float invoiceAmount;
    private static float paymentAmount;
    private static String paymentDate;
    private static Connection connection;
    private static ManagementDAO managementDAO;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;

    @BeforeClass
    public static void setupBeans() {
        contractorName = "XXX";
        contractNumber = "000000";
        invoiceNumber = "INV000000";
        invoiceDate = "2000-10-10";
        invoiceAmount = 1000.0f;
        paymentDate = invoiceDate;
        paymentAmount = invoiceAmount;
        managementDAO = new ManagementDAO();
        connection = DBConnection.getConnection();
    }

    @Test
    public void addContractorTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        try {
            preparedStatement = connection.prepareStatement
                    ("SELECT contractor_name, contract_num FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Assert.assertEquals(contractorName, resultSet.getString(1));
                Assert.assertEquals(contractNumber, resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
                preparedStatement.setString(1, contractorName);
                preparedStatement.execute();
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void getListOfContractorsTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        List<ContractorBean> listOfContractors = managementDAO.getListOfContractors();
        Assert.assertNotNull(listOfContractors);
        Iterator iterator = listOfContractors.iterator();
        while (iterator.hasNext()) {
            ContractorBean iteratedBean = (ContractorBean)iterator.next();
            Assert.assertNotNull(iteratedBean.getContractNumber());
            Assert.assertNotNull(iteratedBean.getContractorsName());
        }
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void addInvoicesDataTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.addInvoicesData(invoiceNumber, invoiceDate, invoiceAmount, contractorName);
        try {
            preparedStatement = connection.prepareStatement("SELECT invoice_num, date, due_date, amount FROM invoices WHERE contractor_id IN " +
                    "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
            preparedStatement.setString(1, contractorName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Assert.assertEquals(resultSet.getString(1), invoiceNumber);
                Assert.assertEquals(resultSet.getString(2), invoiceDate);
                Assert.assertNotNull(resultSet.getString(3));
                Assert.assertEquals(resultSet.getFloat(4), invoiceAmount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM invoices WHERE invoice_num=?");
                preparedStatement.setString(1, invoiceNumber);
                preparedStatement.execute();
                preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
                preparedStatement.setString(1, contractorName);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void getInvoicesDataTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.addInvoicesData(invoiceNumber, invoiceDate, invoiceAmount, contractorName);
        List<InvoicesBean> listOfInvoices = managementDAO.getInvoicesData(contractorName);
        Assert.assertNotNull(listOfInvoices);
        Iterator iterator = listOfInvoices.iterator();
        while (iterator.hasNext()) {
            InvoicesBean iteratedBean = (InvoicesBean) iterator.next();
            Assert.assertNotNull(iteratedBean.getNumber());
            Assert.assertNotNull(iteratedBean.getDate());
            Assert.assertNotNull(iteratedBean.getDateFormatted());
            Assert.assertNotNull(iteratedBean.getDueDate());
            Assert.assertNotNull(iteratedBean.getDueDateFormatted());
            Assert.assertTrue(iteratedBean.getAmount() != 0.0f);
        }
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM invoices WHERE invoice_num=?");
            preparedStatement.setString(1, invoiceNumber);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void deleteInvoiceByIdTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.addInvoicesData(invoiceNumber, invoiceDate, invoiceAmount, contractorName);
        managementDAO.deleteInvoiceByID(contractorName, invoiceNumber);
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM invoices WHERE invoice_num=?");
            preparedStatement.setString(1, invoiceNumber);
            resultSet = preparedStatement.executeQuery();
            Assert.assertFalse(resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
                preparedStatement.setString(1, contractorName);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Test
    public void deleteContractorByIdTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.deleteContractorByID(contractorName);
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorName);
            resultSet = preparedStatement.executeQuery();
            Assert.assertFalse(resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void addPaymentsDataTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.addPaymentsData(paymentAmount, paymentDate, contractorName);
        try {
            preparedStatement = connection.prepareStatement("SELECT amount, date FROM payments WHERE amount=?");
            preparedStatement.setFloat(1, paymentAmount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Assert.assertEquals(paymentAmount, resultSet.getFloat(1));
                Assert.assertEquals(paymentDate, resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement = connection.prepareStatement("DELETE FROM payments WHERE amount=? AND contractor_id IN " +
                        "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
                preparedStatement.setFloat(1, paymentAmount);
                preparedStatement.setString(2, contractorName);
                preparedStatement.execute();
                preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
                preparedStatement.setString(1, contractorName);
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    try {
                        preparedStatement.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Test
    public void getPaymentsDataTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.addPaymentsData(paymentAmount, paymentDate, contractorName);
        List<PaymentsBean> listOfPayments = managementDAO.getPaymentsData(contractorName);
        Assert.assertNotNull(listOfPayments);
        Iterator iterator = listOfPayments.iterator();
        while(iterator.hasNext()) {
            PaymentsBean iteratedBean = (PaymentsBean) iterator.next();
            Assert.assertNotNull(iteratedBean.getDate());
            Assert.assertNotNull(iteratedBean.getDateFormatted());
            Assert.assertTrue(iteratedBean.getAmount() != 0.0f);
        }
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM payments WHERE amount=? AND contractor_id IN " +
                    "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
            preparedStatement.setFloat(1, paymentAmount);
            preparedStatement.setString(2, contractorName);
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void getPaymentTotalAmountTest() {
        managementDAO.addContractor(contractorName, contractNumber);
        managementDAO.addPaymentsData(100.0f, paymentDate, contractorName);
        managementDAO.addPaymentsData(100.0f, paymentDate, contractorName);
        float totalAmount = managementDAO.getPaymentTotalAmount(contractorName);
        Assert.assertTrue(totalAmount >= 200.0f);
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM payments WHERE contractor_id IN " +
                    "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
            preparedStatement.setString(1, contractorName);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
