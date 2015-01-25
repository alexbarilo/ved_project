package controller;

import model.ContractorBean;
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

public class ServletContractorsList extends HttpServlet{

    private ManagementDAO managementDAO;

    public ServletContractorsList() {
        super();
        managementDAO = new ManagementDAO();
    }

    RequestDispatcher requestDispatcher;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext context = req.getSession().getServletContext();
        String currentContractor = req.getParameter("contractor-selection");
        String action = req.getParameter("action");

        if (action.equals("Del contractor")) {
            managementDAO.deleteContractorByID(currentContractor);
            ArrayList<ContractorBean> listOfContractors = (ArrayList) managementDAO.getListOfContractors();
            context.setAttribute("listOfContractors", listOfContractors);
            requestDispatcher = req.getRequestDispatcher("contractors_list.jsp");
            requestDispatcher.forward(req, resp);
        } else if (action.equals("Get report")) {
            ArrayList<InvoicesBean> listOfInvoicesWithData = (ArrayList) managementDAO.getInvoicesData(currentContractor);
            ArrayList<PaymentsBean> listOfPaymentsWithData = (ArrayList) managementDAO.getPaymentsData(currentContractor);
            context.setAttribute("currentContractor", currentContractor);
            context.setAttribute("listOfInvoices", listOfInvoicesWithData);
            context.setAttribute("listOfPayments", listOfPaymentsWithData);
            requestDispatcher = req.getRequestDispatcher("contractor_table.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext context = req.getSession().getServletContext();
        String contractorsName = req.getParameter("contractors-name");
        String contractNumber = req.getParameter("contract-number");

        ArrayList<ContractorBean> listOfContractors = (ArrayList) managementDAO.addContractor(contractorsName, contractNumber);
        context.setAttribute("listOfContractors", listOfContractors);
        requestDispatcher = req.getRequestDispatcher("contractor_table.jsp");
        requestDispatcher.forward(req,resp);
    }

}
