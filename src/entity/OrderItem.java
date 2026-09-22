
package entity;


import dao.impl.Goods;

public class OrderItem {
    private int id;
    private int orders_id; // 关联的订单ID
    private int goods_id;  // 关联的商品ID
    private Double buyPrice; // 购买单价
    private Integer buyNum;  // 购买数量
    private Goods goods;  // 新增关联的商品对象

    // 构造方法
    public OrderItem() {
    }

    // Getter和Setter方法


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }
    // 新增getter/setter
    public Goods getGoods() {
        return goods;
    }
    public void setGoods(Goods goods) {
        this.goods = goods;
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orders_id=" + orders_id +
                ", goods_id=" + goods_id +
                ", buyPrice=" + buyPrice +
                ", buyNum=" + buyNum +
                '}';
    }
}