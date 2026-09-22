package controller;

import entity.Customer;
import entity.Order;
import entity.OrderItem;
import service.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/myorders")
public class MyOrdersServlet extends HttpServlet {
    private OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            resp.sendRedirect("/login");
            return;
        }

        List<Order> orders = orderService.findOrdersByCustomerId(customer.getId());

        // 加载每个订单的订单项
        Map<Integer, List<OrderItem>> orderItemsMap = new HashMap<>();
        for (Order order : orders) {
            List<OrderItem> items = orderService.findOrderItemsByOrderId(order.getId());
            orderItemsMap.put(order.getId(), items);
        }

        req.setAttribute("orders", orders);
        req.setAttribute("orderItemsMap", orderItemsMap);
        req.getRequestDispatcher("myorders.jsp").forward(req, resp);
    }
}