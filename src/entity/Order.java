package entity;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private String code;
    private Double totalSum;
    private Double carriage;
    private Double realSum;
    private Integer totalNum;
    private String receiverAddress;
    private String receiverName;
    private String receiverTel;
    private Integer state;
    private Date orderTime;
//    private List<OrderItem> orderItems; // 关联的订单项
    private String payTime;       // 支付时间，对应数据库 varchar(20)
    private int payType;          // 支付类型，对应数据库 int(1)
    private Integer customer_id;  // 添加客户ID字段

    // 构造方法
    public Order() {
    }

    // Getter和Setter方法


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    public Double getCarriage() {
        return carriage;
    }

    public void setCarriage(Double carriage) {
        this.carriage = carriage;
    }

    public Double getRealSum() {
        return realSum;
    }

    public void setRealSum(Double realSum) {
        this.realSum = realSum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverTel() {
        return receiverTel;
    }

    public void setReceiverTel(String receiverTel) {
        this.receiverTel = receiverTel;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

//    public List<OrderItem> getOrderItems() {
//        return orderItems;
//    }
//
//    public void setOrderItems(List<OrderItem> orderItems) {
//        this.orderItems = orderItems;
//    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", totalSum=" + totalSum +
                ", carriage=" + carriage +
                ", realSum=" + realSum +
                ", totalNum=" + totalNum +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverTel='" + receiverTel + '\'' +
                ", state=" + state +
                ", orderTime=" + orderTime +
                ", payTime='" + payTime + '\'' +
                ", payType=" + payType +
                ", customer_id=" + customer_id +
                '}';
    }
}