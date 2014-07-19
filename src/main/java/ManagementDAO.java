import java.sql.Connection;
import java.sql.*;
import java.sql.Statement;
import java.util.*;

public class ManagementDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    private String contractorID;

    List<ContractorBean> listOfContractors;
    List<InvoicesBean> invoicesData;
    List<PaymentsBean> paymentsData;


    public ManagementDAO() {
        connection = DBConnection.getConnection();
    }

    public List<ContractorBean> addContractor(String contractorsName,String contractNumber) {
        if (contractorsName.equals("") || contractNumber.equals("")) {
            return getListOfContractors();
        }
        try {
            preparedStatement = connection.prepareStatement("insert into contractors(contractor_name, contract_num) values(?, ?)");
            preparedStatement.setString(1, contractorsName);
            preparedStatement.setString(2, contractNumber);
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
        return getListOfContractors();
    }

    public List<ContractorBean> getListOfContractors () {
        listOfContractors = new ArrayList<ContractorBean>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from contractors;");
            while (resultSet.next()) {
                ContractorBean contractorBean = new ContractorBean();
                contractorBean.setContractorsName(resultSet.getString(2));
                contractorBean.setContractNumber(resultSet.getString(3));
                listOfContractors.add(contractorBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listOfContractors;
    }

    public int getContractorsId(String selectedContractor){
        int contractorsID = 0;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT contractor_id FROM contractors WHERE contractor_name=" + selectedContractor);
            while (resultSet.next()){
                contractorsID = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contractorsID;
    }

    public List<InvoicesBean> getInvoicesData(String selectedContractor){

        float sumBalance = getPaymentTotalAmount(selectedContractor);
        List<InvoicesBean> invoicesDataWithPaymentsState;
        PaymentStateOperations paymentStateOperations = new PaymentStateOperations();

        try {
            preparedStatement = connection.prepareStatement("SELECT invoice_num, date, due_date, amount FROM invoices WHERE contractor_id IN" +
                    "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
            preparedStatement.setString(1, selectedContractor);
            resultSet = preparedStatement.executeQuery();
            invoicesData = new ArrayList<InvoicesBean>();
            while (resultSet.next()) {
                InvoicesBean invoicesBean = new InvoicesBean();
                invoicesBean.setNumber(resultSet.getString(1));
                invoicesBean.setDate(resultSet.getString(2));
                invoicesBean.setDueDate(resultSet.getString(3));
                invoicesBean.setAmount(resultSet.getFloat(4));
                invoicesData.add(invoicesBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        invoicesDataWithPaymentsState = paymentStateOperations.setPaymentStateInBean(invoicesData, sumBalance);
        return invoicesDataWithPaymentsState;
    }

    public void addInvoicesData(String invoiceNum, String date, float amount, String contractorsName){
        //String contractorID = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT contractor_id FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorsName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contractorID = resultSet.getString(1);
            }
            preparedStatement = connection.prepareStatement("INSERT INTO invoices(contractor_id, invoice_num, date, amount) VALUES (?,?,?,?)");
            preparedStatement.setString(1, contractorID);
            preparedStatement.setString(2, invoiceNum);
            preparedStatement.setString(3, date);
            preparedStatement.setFloat(4, amount);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    preparedStatement.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteInvoiceByID(String currentContractor, String invoiceID) {
        int contractorID = 0;
        try {
            preparedStatement = connection.prepareStatement("SELECT contractor_id FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, currentContractor);
            resultSet  =preparedStatement.executeQuery();
            while (resultSet.next()) {
                contractorID = resultSet.getInt(1);
            }
            preparedStatement = connection.prepareStatement("DELETE FROM invoices WHERE contractor_id=? AND invoice_num=?");
            preparedStatement.setInt(1, contractorID);
            preparedStatement.setString(2, invoiceID);
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

    public void deleteContractorByID(String currentContractor) {
        boolean isDeleted = false;
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, currentContractor);
            isDeleted = preparedStatement.execute();
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

    public void addPaymentsData(float amount, String date, String contractorsName) {
        try {
            preparedStatement = connection.prepareStatement("SELECT contractor_id FROM contractors WHERE contractor_name=?");
            preparedStatement.setString(1, contractorsName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                contractorID = resultSet.getString(1);
            }
            preparedStatement = connection.prepareStatement("INSERT INTO payments(contractor_id, amount, date) VALUES(?,?,?)");
            preparedStatement.setString(1, contractorID);
            preparedStatement.setFloat(2, amount);
            preparedStatement.setString(3, date);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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

    public List<PaymentsBean> getPaymentsData(String contractorsName) {
        paymentsData = new ArrayList<PaymentsBean>();
        try {
            preparedStatement = connection.prepareStatement("SELECT amount, date FROM payments WHERE contractor_id IN" +
                    "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
            preparedStatement.setString(1, contractorsName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PaymentsBean paymentsBean = new PaymentsBean();
                paymentsBean.setAmount(resultSet.getFloat(1));
                paymentsBean.setDate(resultSet.getString(2));
                paymentsData.add(paymentsBean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        Collections.sort(paymentsData, new BeanSortByDate());
        return paymentsData;
    }

    public float getPaymentTotalAmount (String selectedContractor) {
        float paymentAmount = 0.00f;
        try {
            preparedStatement = connection.prepareStatement("SELECT SUM(amount) FROM payments WHERE contractor_id IN" +
                    "(SELECT contractor_id FROM contractors WHERE contractor_name=?)");
            preparedStatement.setString(1, selectedContractor);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paymentAmount = resultSet.getFloat(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
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
        return paymentAmount;
    }
}

