package controller;

import entity.Address;
import entity.Customer;
import service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/doReg")
public class DoRegServlet extends HttpServlet {
    //引入服务层
    CustomerServiceImpl csi = new CustomerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理 GET 请求
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username=req.getParameter("username");
        String pass=req.getParameter("pass");
        String tel=req.getParameter("tel");
        Customer cus=new Customer();
        cus.setPass(pass);
        cus.setTel(tel);
        cus.setUsername(username);
        int i=csi.addCustomer(cus);
        // 若i>0 代表添加成功
        if (i > 0) {
            // 获取新插入的customer的id
            int newCustomerId = csi.getLastInsertId();

            // 创建一个新的Address对象并设置customer_id
            Address newAddress = new Address();
            newAddress.setCustomerId(newCustomerId);
            newAddress.setAddress("默认地址"); // 可以设置一个默认地址
            newAddress.setDefault(true); // 默认设为默认地址

            // 将新的Address对象添加到数据库中
            csi.addAddress(newAddress);

            req.getRequestDispatcher("/index").forward(req, resp);
        } else {
            req.getRequestDispatcher("/reg").forward(req, resp);
        }
    }
}