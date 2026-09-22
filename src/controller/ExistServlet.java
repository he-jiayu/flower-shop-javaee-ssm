package controller;

import service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/isExistUsername")
public class ExistServlet extends HttpServlet {
    //    调用服务层
    CustomerServiceImpl csi = new CustomerServiceImpl();

    //选择合适的方法
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    //正确使用(入参;返回值)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String na= req.getParameter("username");
        resp.getWriter().print( csi.isExist(na));
    }
}
