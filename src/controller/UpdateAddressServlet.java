package controller;

import entity.Address;
import entity.Customer;
import service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/updateAddress")
public class UpdateAddressServlet extends HttpServlet {
    private CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理 GET 请求，跳转到地址管理页面
        req.getRequestDispatcher("/address.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            // 用户未登录或会话已失效
            req.setAttribute("message", "用户未登录或会话已失效");
            req.getRequestDispatcher("/login").forward(req, resp);
            return;
        }

        // 处理 POST 请求，更新地址信息
        int customerId = Integer.parseInt(req.getParameter("customerId"));
        String detailAddress = req.getParameter("detailAddress");
        boolean isDefault = Boolean.parseBoolean(req.getParameter("isDefault"));

        // 调用服务层更新地址
        int result = customerService.updateAddress(customerId, detailAddress, isDefault);

        if (result > 0) {
            // 更新成功
            req.setAttribute("message", "地址更新成功");
        } else {
            // 更新失败
            req.setAttribute("message", "地址更新失败");
        }

        // 重定向到地址管理页面
        req.getRequestDispatcher("/address.jsp").forward(req, resp);
    }
}