import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ServletVerification extends HttpServlet {

    private ManagementDAO managementDAO;

    public ServletVerification() {
        super();
        managementDAO = new ManagementDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        RequestDispatcher requestDispatcher;

        if (login.equals("aime")) {
            ServletContext context = req.getSession().getServletContext();
            ArrayList<ContractorBean> listOfContractors = (ArrayList) managementDAO.getListOfContractors();
            context.setAttribute("listOfContractors", listOfContractors);
            requestDispatcher = req.getRequestDispatcher("contractors_list.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            requestDispatcher = req.getRequestDispatcher("start_page.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
