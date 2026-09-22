package service;

import dao.impl.OrderDao;
import dao.impl.OrderDaoImpl;
import entity.Order;
import entity.OrderItem;

import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements  IOrderServiceImpl {
    private OrderDao orderDao = new OrderDaoImpl();

    @Override
    public boolean createOrder(Order order, List<OrderItem> orderItems) {
        // 计算订单总金额、运费等
        calculateOrderAmount(order, orderItems);

        // 调用DAO层保存订单
        return orderDao.saveOrder(order, orderItems) > 0;
    }

    private void calculateOrderAmount(Order order, List<OrderItem> orderItems) {
        double totalSum = 0;
        int totalNum = 0;

        for(OrderItem item : orderItems) {
            totalSum += item.getBuyPrice() * item.getBuyNum();
            totalNum += item.getBuyNum();
        }

        // 设置订单金额
        order.setTotalSum(totalSum);
        order.setTotalNum(totalNum);
        // 计算运费等
        order.setCarriage(calculateCarriage(totalSum));
        order.setRealSum(totalSum + order.getCarriage());
    }

    private double calculateCarriage(double totalSum) {
        // 根据业务规则计算运费
        if(totalSum > 20) {
            return 0; // 满20免运费
        }
        return 10; // 默认运费10元
    }
    @Override
    public int saveOrder(Order order, List<OrderItem> orderItems) {
        calculateOrderAmount(order, orderItems);
        return orderDao.saveOrder(order, orderItems);
    }

    @Override
    public Order findOrderById(int orderId) {
        return orderDao.findOrderById(orderId);
    }

    @Override
    public boolean updateOrderState(int orderId, int state) {
        return orderDao.updateOrderState(orderId, state);
    }

    @Override
    public boolean updateOrderState(int orderId, int state, Date payTime, int payType) {
        return orderDao.updateOrderState(orderId, state, payTime, payType);
    }

    @Override
    public List<Order> findOrdersByCustomerId(int customerId) {
        return orderDao.findOrdersByCustomerId(customerId);
    }
    @Override
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        return orderDao.findOrderItemsByOrderId(orderId);
    }


}