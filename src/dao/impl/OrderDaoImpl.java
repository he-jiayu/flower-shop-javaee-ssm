package dao.impl;

import dao.Db;
import entity.Order;
import entity.OrderItem;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl extends Db implements OrderDao {
    private Connection conn = getConn();
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt = null;
    String sql = "";



    @Override
    public int saveOrder(Order order, List<OrderItem> orderItems) {
        int orderId = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 开启事务
            conn.setAutoCommit(false);

            // 1. 校验并计算订单金额
            if (order.getTotalSum() == null || order.getTotalSum() <= 0) {
                // 重新计算订单总金额
                double totalSum = 0;
                int totalNum = 0;

                for (OrderItem item : orderItems) {
                    if (item != null && item.getBuyPrice() != null && item.getBuyNum() != null) {
                        totalSum += item.getBuyPrice() * item.getBuyNum();
                        totalNum += item.getBuyNum();
                    }
                }

                order.setTotalSum(totalSum);
                order.setTotalNum(totalNum);

                // 计算运费和实际支付金额
                double carriage = calculateCarriage(totalSum);
                order.setCarriage(carriage);
                order.setRealSum(totalSum + carriage);
            }

            // 2. 保存订单主表
            String orderSql = "INSERT INTO orders(code, totalSum, carriage, realSum, totalNum, " +
                    "receiverAddress, receiverName, receiverTel, state, orderTime, customer_id, payTime, payType) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);

            // 设置订单参数
            pstmt.setString(1, generateOrderCode());
            pstmt.setDouble(2, order.getTotalSum());
            pstmt.setDouble(3, order.getCarriage());
            pstmt.setDouble(4, order.getRealSum());
            pstmt.setInt(5, order.getTotalNum());
            pstmt.setString(6, order.getReceiverAddress());
            pstmt.setString(7, order.getReceiverName());
            pstmt.setString(8, order.getReceiverTel());
            pstmt.setInt(9, order.getState());

            // 处理日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedOrderTime = sdf.format(order.getOrderTime());
            pstmt.setString(10, formattedOrderTime);

            pstmt.setInt(11, order.getCustomer_id());
            pstmt.setString(12, order.getPayTime());
            pstmt.setInt(13, order.getPayType());

            // 执行插入
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("创建订单失败，没有行受影响");
            }

            // 获取生成的订单ID
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                throw new SQLException("创建订单失败，无法获取ID");
            }

            // 3. 保存订单明细
            String itemSql = "INSERT INTO orderitem(orders_id, goods_id, buyPrice, buyNum) VALUES(?, ?, ?, ?)";
            pstmt = conn.prepareStatement(itemSql);

            for (OrderItem item : orderItems) {
                if (item == null) continue;

                pstmt.setInt(1, orderId);
                pstmt.setInt(2, item.getGoods_id());
                pstmt.setDouble(3, item.getBuyPrice());
                pstmt.setInt(4, item.getBuyNum());
                pstmt.addBatch();
            }

            int[] batchResults = pstmt.executeBatch();
            for (int result : batchResults) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    throw new SQLException("批量插入订单明细失败");
                }
            }

            // 提交事务
            conn.commit();
            return orderId;

        } catch (SQLException e) {
            try {
                conn.rollback();
                throw new RuntimeException("保存订单失败，已回滚事务", e);
            } catch (SQLException ex) {
                throw new RuntimeException("保存订单失败且回滚失败", ex);
            }
        } finally {
            // 关闭资源
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                // 记录日志但不要抛出异常
                e.printStackTrace();
            }
        }
    }

    // 运费计算方法
    private double calculateCarriage(double totalSum) {
        // 根据业务规则计算运费
        if (totalSum > 20) {
            return 0; // 满20免运费
        }
        return 10; // 默认运费10元
    }

    @Override
    public Order findOrderById(int orderId) {
        Order order = null;
        try {
            sql = "SELECT * FROM orders WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setCode(rs.getString("code"));
                // 设置其他订单属性...
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
        }
        return order;
    }

    @Override
    public List<Order> findOrdersByCustomerId(int customerId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ? ORDER BY orderTime DESC";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCode(rs.getString("code"));
                order.setTotalSum(rs.getDouble("totalSum"));
                order.setCarriage(rs.getDouble("carriage"));
                order.setRealSum(rs.getDouble("realSum"));
                order.setTotalNum(rs.getInt("totalNum"));
                order.setReceiverAddress(rs.getString("receiverAddress"));
                order.setReceiverName(rs.getString("receiverName"));
                order.setReceiverTel(rs.getString("receiverTel"));
                order.setState(rs.getInt("state"));
                order.setOrderTime(rs.getTimestamp("orderTime"));
                order.setCustomer_id(rs.getInt("customer_id"));
                order.setPayTime(rs.getString("payTime"));
                order.setPayType(rs.getInt("payType"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    @Override
    public List<OrderItem> findOrderItemsByOrderId(int orderId) {
        List<OrderItem> items = new ArrayList<>();

        String sql = "SELECT oi.*, g.keywords FROM orderitem oi " +  // 移除g.name
                "LEFT JOIN goods g ON oi.goods_id = g.id " +
                "WHERE oi.orders_id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, orderId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrders_id(rs.getInt("orders_id"));
                item.setGoods_id(rs.getInt("goods_id"));
                item.setBuyPrice(rs.getDouble("buyPrice"));
                item.setBuyNum(rs.getInt("buyNum"));

                // 填充Goods对象
                Goods goods = new Goods();
                goods.setKeywords(rs.getString("keywords"));  // 假设Goods类有keywords属性
//                goods.setName(rs.getString("name"));         // 其他商品字段
                item.setGoods(goods);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
    @Override
    public boolean updateOrderState(int orderId, int state) {
        String sql = "UPDATE orders SET state = ? WHERE id = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, state);
            pstmt.setInt(2, orderId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean deleteOrder(int orderId) {
        return false;
    }

    @Override
    public boolean updateOrderState(int orderId, int state, Date payTime, int payType) {
        try {
            conn.setAutoCommit(false); // 开启事务

            String sql = "UPDATE orders SET state = ?, payTime = ?, payType = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, state);

            // 修改日期处理方式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedPayTime = sdf.format(payTime);
            pstmt.setString(2, formattedPayTime);

            pstmt.setInt(3, payType);
            pstmt.setInt(4, orderId);

            boolean result = pstmt.executeUpdate() > 0;
            conn.commit(); // 提交事务
            return result;
        } catch (SQLException e) {
            try {
                conn.rollback(); // 回滚事务
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true); // 恢复自动提交
                if (pstmt != null) pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateOrderCode() {
        // 生成订单编号，如: ORD20230501123456
        return "ORD" + System.currentTimeMillis();
    }
}