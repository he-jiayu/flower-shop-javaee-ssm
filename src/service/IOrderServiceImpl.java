package service;

import entity.Order;
import entity.OrderItem;

import java.util.Date;
import java.util.List;

public interface IOrderServiceImpl {
    boolean createOrder(Order order, List<OrderItem> orderItems);

    int saveOrder(Order order, List<OrderItem> orderItems);

    Order findOrderById(int orderId);

    boolean updateOrderState(int orderId, int state);
    boolean updateOrderState(int orderId, int state, Date payTime, int payType);
    List<Order> findOrdersByCustomerId(int customerId); // 添加这个方法

    List<OrderItem> findOrderItemsByOrderId(int orderId);
}
