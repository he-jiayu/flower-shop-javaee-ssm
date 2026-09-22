package controller;

import entity.Customer;
import service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/checkUnique")
public class CheckUniqueServlet extends HttpServlet {
    private CustomerServiceImpl csi = new CustomerServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String field = req.getParameter("field");
        String value = req.getParameter("value");
        int customerId = Integer.parseInt(req.getParameter("customerId"));

        boolean isUnique = true;
        if ("tel".equals(field)) {
            isUnique = csi.isUniqueTel(value, customerId);
        } else if ("email".equals(field)) {
            isUnique = csi.isUniqueEmail(value, customerId);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().print("{\"isUnique\":" + isUnique + "}");
    }
}