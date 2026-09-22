package controller;

import entity.GoodsItem;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    private OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 检查用户是否登录
        if(req.getSession().getAttribute("customer") == null) {
            resp.sendRedirect("/login");
            return;
        }

        HttpSession session = req.getSession();

        // 判断是否是立即购买流程
        Boolean fromBuyNow = (Boolean) session.getAttribute("fromBuyNow");
        List<GoodsItem> cartToDisplay = null;

        if(fromBuyNow != null && fromBuyNow) {
            // 立即购买流程 - 只显示buyNowCart中的商品
            cartToDisplay = (List<GoodsItem>) session.getAttribute("buyNowCart");
            // 从session获取预先计算的金额
            Double totalAmount = (Double) session.getAttribute("buyNowTotalAmount");
            // 设置一个标志，让页面知道这是立即购买流程
            req.setAttribute("isBuyNow", true);
        } else {
            // 普通购物车结算流程 - 显示购物车中的所有商品
            cartToDisplay = (List<GoodsItem>) session.getAttribute("cart");
        }

        double totalAmount = 0;
        if(cartToDisplay != null) {
            for(GoodsItem item : cartToDisplay) {
                totalAmount += item.getGoods().getOut_price() * item.getNum();
            }
        }

        req.setAttribute("totalAmount", totalAmount);
        req.setAttribute("cart", cartToDisplay);
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        HttpSession session = req.getSession();

        // 判断是立即购买还是购物车结算
        Boolean fromBuyNow = (Boolean) session.getAttribute("fromBuyNow");
        List<GoodsItem> cartToProcess = null;

        if(fromBuyNow != null && fromBuyNow) {
            // 处理立即购买的商品
            cartToProcess = (List<GoodsItem>) session.getAttribute("buyNowCart");
        } else {
            // 处理购物车中的商品
            cartToProcess = (List<GoodsItem>) session.getAttribute("cart");
        }

        // 检查购物车是否为空
        if(cartToProcess == null || cartToProcess.isEmpty()) {
            req.setAttribute("error", "购物车为空，无法提交订单");
            req.getRequestDispatcher("shoppingCart.jsp").forward(req, resp);
            return;
        }

        // 获取当前登录用户的ID
        Integer customerId = (Integer) session.getAttribute("customer_id");
        if (customerId == null) {
            resp.sendRedirect("/login");
            return;
        }

        // 获取表单数据
        String receiverName = req.getParameter("receiverName");
        String receiverTel = req.getParameter("receiverTel");
        String receiverAddress = req.getParameter("receiverAddress");
        String paymentMethod = req.getParameter("paymentMethod");

        // 创建订单对象
        Order order = new Order();
        order.setReceiverName(receiverName);
        order.setReceiverTel(receiverTel);
        order.setReceiverAddress(receiverAddress);
        order.setOrderTime(new Date());
        order.setState(0); // 0表示未支付
        order.setCustomer_id(customerId);
        order.setPayType(Integer.parseInt(paymentMethod));

        // 从购物车转换为订单项
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for(GoodsItem item : cartToProcess) {
            OrderItem orderItem = new OrderItem();
            orderItem.setGoods_id(item.getGoods().getId());
            orderItem.setBuyPrice(item.getGoods().getOut_price());
            orderItem.setBuyNum(item.getNum());
            orderItems.add(orderItem);

            totalAmount += item.getGoods().getOut_price() * item.getNum();
        }

        order.setTotalSum(totalAmount);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if ("1".equals(paymentMethod)) {
            order.setPayTime(sdf.format(new Date()));
        } else {
            order.setPayTime("0000-00-00 00:00:00");
        }

        // 调用服务层创建订单
        int orderId = orderService.saveOrder(order, orderItems);

        if(orderId > 0) {

            // 清除购物车数据
            if(fromBuyNow != null && fromBuyNow) {
                session.removeAttribute("buyNowCart");
                session.removeAttribute("fromBuyNow");
            } else {
                session.removeAttribute("cart");
            }

            session.setAttribute("currentOrderId", orderId);
            resp.sendRedirect("/payment?orderId=" + orderId);
            session.setAttribute("orderTotalAmount", totalAmount);
        } else {
            req.setAttribute("error", "订单创建失败，请重试");
            req.getRequestDispatcher("order.jsp").forward(req, resp);
        }
    }
}