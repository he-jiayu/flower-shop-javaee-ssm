package controller;

import entity.Customer;
import service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/myinfo")
public class MyInfoServlet extends HttpServlet {
    private CustomerServiceImpl customerService = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理 GET 请求，跳转到个人信息页面
        req.getRequestDispatcher("/myinfo.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        // 处理 POST 请求，更新个人信息

        // 获取session对象
        HttpSession session = req.getSession();
        // 从session中拿到购物车数据
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            // 如果没有登录用户，重定向到登录页面
            resp.sendRedirect("/login");
            return;
        }

        // 获取用户提交的个人信息
        String realname = req.getParameter("realname");
        String intro = req.getParameter("intro");
        String tel = req.getParameter("tel");
        String email = req.getParameter("email");
        String gender = req.getParameter("gender");
        // 处理性别字段的值
        if ("男".equals(gender)) {
            gender = "1";
        } else if ("女".equals(gender)) {
            gender = "2";
        } else {
            gender = "1"; // 默认值
        }

        String cardID = req.getParameter("cardID");



        // 更新用户信息
        int updateResult = customerService.updateUserInfo(customer.getId(), realname, intro, tel, email, gender, cardID);

        if (updateResult > 0) {
            // 更新成功，重新从数据库中获取最新的用户信息
            customer = customerService.findById(customer.getId());
            session.setAttribute("customer", customer);
            req.setAttribute("message", "个人信息更新成功");
        } else {
            // 更新失败
            req.setAttribute("message", "个人信息更新失败，请稍后再试");
        }

        // 跳转回个人信息页面
        req.getRequestDispatcher("/myinfo.jsp").forward(req, resp);
    }
}