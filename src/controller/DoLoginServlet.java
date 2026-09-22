package controller;

import entity.Customer;
import service.CustomerServiceImpl;

import javax.security.auth.login.LoginContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.AuthProvider;


    @WebServlet(urlPatterns = "/doLogin")
    public class DoLoginServlet extends HttpServlet {
        //引入服务层
         CustomerServiceImpl csi = new CustomerServiceImpl();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            // 处理 GET 请求
            doPost(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String u = req.getParameter("username");
            String p = req.getParameter("pass");
            Customer customer=csi.login(u,p);


            if (customer != null) {
                // 重新从数据库中获取最新的用户信息
                customer = csi.findById(customer.getId());
                //将正确登录的用户信息写进session，后续购物车及个人中心要判断是否登录过
               HttpSession session = req.getSession();
               session.setAttribute("customer",customer);
                session.setAttribute("customer_id", customer.getId()); // 设置 customer_id 到会话中
                // 设置全局消息
                session.setAttribute("loginSuccessMessage", "登录成功");
                req.getRequestDispatcher("/index").forward(req, resp);
            } else {
                //若登录失败
                req.setAttribute("message", "Tips:账户名或密码错误，请重新输入");
                req.getRequestDispatcher("/login").forward(req, resp);
            }
        }
    }

