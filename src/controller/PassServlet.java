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

@WebServlet(urlPatterns = "/pass")
public class PassServlet extends HttpServlet {
    private CustomerServiceImpl csi = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理 GET 请求，直接转发到密码修改页面
        req.getRequestDispatcher("/pass.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取当前会话中的用户信息
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

//        // 如果用户未登录，提示用户登录
//        if (customer == null) {
//            req.setAttribute("message", "请先登录！");
//            req.getRequestDispatcher("/login").forward(req, resp);
//            return;
//        }

        // 获取表单参数
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        // 打印调试信息
        System.out.println("用户ID: " + customer.getId());
        System.out.println("会话中的密码: " + customer.getPass());
        System.out.println("用户输入的原密码: " + oldPassword);

        // 验证原密码是否正确
        if (!customer.getPass().equals(oldPassword)) {
            req.setAttribute("message", "原密码错误！");
            req.getRequestDispatcher("/pass.jsp").forward(req, resp);
            return;
        }

        // 验证新密码和确认新密码是否一致
        if (!newPassword.equals(confirmPassword)) {
            req.setAttribute("message", "新密码和确认新密码不一致！");
            req.getRequestDispatcher("/pass.jsp").forward(req, resp);
            return;
        }

        // 更新密码
        int result = csi.updatePassword(customer.getId(), newPassword);

        if (result > 0) {
            // 更新成功
            customer.setPass(newPassword);
            session.setAttribute("customer", customer);
            req.setAttribute("message", "密码更新成功！");
        } else {
            // 更新失败
            req.setAttribute("message", "密码更新失败，请稍后再试！");
        }

        // 转发回密码修改页面，显示消息
        req.getRequestDispatcher("/pass.jsp").forward(req, resp);
    }
}