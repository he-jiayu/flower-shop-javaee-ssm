package controller;

import dao.impl.OrderDaoImpl;
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
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentServlet extends HttpServlet {
    private OrderServiceImpl orderService = new OrderServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String orderId = req.getParameter("orderId");

            if (orderId == null || orderId.isEmpty()) {
                resp.sendRedirect("/order");
                return;
            }

            // 从数据库获取完整订单信息
            Order order = orderService.findOrderById(Integer.parseInt(orderId));
            if (order == null) {
                req.setAttribute("error", "订单不存在或已被删除");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
                return;
            }

            // 加载订单项
            List<OrderItem> orderItems = orderService.findOrderItemsByOrderId(order.getId());
            if (orderItems == null || orderItems.isEmpty()) {
                req.setAttribute("error", "订单中没有商品");
                req.getRequestDispatcher("error.jsp").forward(req, resp);
                return;
            }

            // 确保订单金额正确 - 优先使用数据库中的金额
            if (order.getTotalSum() == null || order.getTotalSum() <= 0) {
                // 重新计算订单总金额
                double calculatedTotal = 0;
                for (OrderItem item : orderItems) {
                    if (item != null && item.getBuyPrice() != null && item.getBuyNum() != null) {
                        calculatedTotal += item.getBuyPrice() * item.getBuyNum();
                    }
                }
                order.setTotalSum(calculatedTotal);
            }

            // 从session获取可能的立即购买金额（作为备份）
            HttpSession session = req.getSession();
            Double sessionAmount = (Double) session.getAttribute("orderTotalAmount");

            // 如果数据库金额异常，使用session中的金额
            if ((order.getTotalSum() == null || order.getTotalSum() <= 0) && sessionAmount != null) {
                order.setTotalSum(sessionAmount);
            }

            // 设置请求属性
            req.setAttribute("orderItems", orderItems);
            req.setAttribute("order", order);
            req.setAttribute("totalAmount", order.getTotalSum());

            req.getRequestDispatcher("payment.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "系统错误，请稍后再试");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String orderId = req.getParameter("orderId");
            String payType = req.getParameter("paymentMethod");

            if (orderId == null || orderId.isEmpty()) {
                resp.sendRedirect("/order");
                return;
            }

            // 获取订单当前信息
            Order currentOrder = orderService.findOrderById(Integer.parseInt(orderId));
            if (currentOrder == null) {
                req.setAttribute("error", "订单不存在");
                req.getRequestDispatcher("payment.jsp").forward(req, resp);
                return;
            }

            // 从session获取金额作为备份
            HttpSession session = req.getSession();
            Double sessionAmount = (Double) session.getAttribute("orderTotalAmount");

            // 确保订单金额正确
            if (currentOrder.getTotalSum() == null || currentOrder.getTotalSum() <= 0) {
                if (sessionAmount != null) {
                    currentOrder.setTotalSum(sessionAmount);
                } else {
                    // 如果session中也没有金额，重新计算
                    List<OrderItem> items = orderService.findOrderItemsByOrderId(currentOrder.getId());
                    double calculatedTotal = 0;
                    for (OrderItem item : items) {
                        if (item != null && item.getBuyPrice() != null && item.getBuyNum() != null) {
                            calculatedTotal += item.getBuyPrice() * item.getBuyNum();
                        }
                    }
                    currentOrder.setTotalSum(calculatedTotal);
                }
            }

            // 更新订单状态为已支付(1)，并记录支付时间和支付方式
            boolean success = orderService.updateOrderState(
                    Integer.parseInt(orderId),
                    1,                      // 状态：已支付
                    new Date(),             // 支付时间
                    Integer.parseInt(payType) // 支付方式
            );

            if (success) {
                // 获取更新后的订单信息
                Order order = orderService.findOrderById(Integer.parseInt(orderId));

                // 再次确保金额正确
                if (order.getTotalSum() == null || order.getTotalSum() <= 0) {
                    order.setTotalSum(currentOrder.getTotalSum());
                }

                // 清除购物车数据
                session.removeAttribute("cart");
                session.removeAttribute("checkoutCart");
                session.removeAttribute("buyNowCart");
                session.removeAttribute("buyNowTotalAmount");

                // 确保支付时间和方式已设置
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                order.setPayTime(sdf.format(new Date()));
                order.setPayType(Integer.parseInt(payType));

                // 设置订单信息并跳转到支付成功页面
                req.setAttribute("order", order);
                req.setAttribute("totalAmount", order.getTotalSum());
                req.getRequestDispatcher("payment-success.jsp").forward(req, resp);
            } else {
                // 支付失败，返回支付页面
                req.setAttribute("error", "支付处理失败，请重试");
                req.setAttribute("order", currentOrder);
                req.setAttribute("totalAmount", currentOrder.getTotalSum());
                req.getRequestDispatcher("payment.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "支付过程中发生错误");
            req.getRequestDispatcher("error.jsp").forward(req, resp);
        }
    }
}