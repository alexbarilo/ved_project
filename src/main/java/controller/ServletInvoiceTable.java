package controller;

import model.InvoicesBean;
import model.PaymentsBean;
import service.ManagementDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/*
*The servlet process requests from contractor_table.jsp and invoice_edit.jsp
*The application is not multi-threading thus all the attributes are stored in application-scope.
*/
public class ServletInvoiceTable extends HttpServlet {

    private ManagementDAO managementDAO;

    public ServletInvoiceTable() {
        super();
        managementDAO = new ManagementDAO();
    }

    RequestDispatcher requestDispatcher;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String number = req.getParameter("number");
        float amount = Float.parseFloat(req.getParameter("amount"));
        String date = req.getParameter("date");
        String identifier = req.getParameter("jsp-identifier");
        String invoiceNumToUpdate = req.getParameter("invoice-to-update");

        ServletContext context = req.getSession().getServletContext();
        String currentContractor = (String) context.getAttribute("currentContractor");

        if (identifier.equals("invoice-edit")) {
            managementDAO.deleteInvoiceByID(currentContractor, invoiceNumToUpdate);
            managementDAO.addInvoicesData(number, date, amount, currentContractor);
        } else if (identifier.equals("invoices-table")) {
            managementDAO.addInvoicesData(number, date, amount, currentContractor);
        } else if (identifier.equals("payments-table")) {
            managementDAO.addPaymentsData(amount, date, currentContractor);
        }

        ArrayList<InvoicesBean> listOfInvoicesWithData = (ArrayList) managementDAO.getInvoicesData(currentContractor);
        ArrayList<PaymentsBean> listOfPaymentsWithData = (ArrayList) managementDAO.getPaymentsData(currentContractor);
        context.setAttribute("listOfInvoices", listOfInvoicesWithData);
        context.setAttribute("listOfPayments", listOfPaymentsWithData);
        requestDispatcher = req.getRequestDispatcher("contractor_table.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        String invoiceID = req.getParameter("invoice_id");

        ServletContext context = req.getSession().getServletContext();
        String currentContractor = (String) context.getAttribute("currentContractor");

        if (action.equals("delete")) {
            managementDAO.deleteInvoiceByID(currentContractor, invoiceID);
            ArrayList<InvoicesBean> listOfInvoicesWithData = (ArrayList) managementDAO.getInvoicesData(currentContractor);
            context.setAttribute("listOfInvoices", listOfInvoicesWithData);
            requestDispatcher = req.getRequestDispatcher("contractor_table.jsp");
            requestDispatcher.forward(req, resp);
        } else if (action.equals("edit")) {
            req.setAttribute("currentInvoice", invoiceID);
            requestDispatcher = req.getRequestDispatcher("invoice_edit.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
