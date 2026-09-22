package dao.impl;

import entity.Order;
import entity.OrderItem;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    /**
     * 保存订单信息
     * @param order 订单主表信息
     * @param orderItems 订单明细列表
     * @return 返回生成的订单ID
     */
    int saveOrder(Order order, List<OrderItem> orderItems);

    /**
     * 根据订单ID查询订单
     * @param orderId 订单ID
     * @return 订单对象
     */
    Order findOrderById(int orderId);

    /**
     * 根据用户ID查询订单列表
     * @param customerId 用户ID
     * @return 订单列表
     */
    List<Order> findOrdersByCustomerId(int customerId);

    /**
     * 根据订单ID查询订单明细
     * @param orderId 订单ID
     * @return 订单明细列表
     */
    List<OrderItem> findOrderItemsByOrderId(int orderId);

    /**
     * 更新订单状态
     * @param orderId 订单ID
     * @param state 新状态
     * @return 更新是否成功
     */
//    boolean updateOrderState(int orderId, int state);

    boolean updateOrderState(int orderId, int state);

    /**
     * 删除订单
     * @param orderId 订单ID
     * @return 删除是否成功
     */
    boolean deleteOrder(int orderId);

    boolean updateOrderState(int orderId, int state, Date payTime, int payType);
}