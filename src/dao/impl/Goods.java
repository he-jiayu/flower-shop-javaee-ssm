package dao.impl;

public class Goods {
    private int id;
    private int stock;
    private String keywords;
    private String code;
    private String spec;
    private String imgUrl;
    private  double in_price;
    private  double out_price;
    private String ch_spec;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getIn_price() {
        return in_price;
    }

    public void setIn_price(double in_price) {
        this.in_price = in_price;
    }

    public double getOut_price() {
        return out_price;
    }

    public void setOut_price(double out_price) {
        this.out_price = out_price;
    }

    public String getCh_spec() {
        return ch_spec;
    }

    public void setCh_spec(String ch_spec) {
        this.ch_spec = ch_spec;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", stock=" + stock +
                ", keywords='" + keywords + '\'' +
                ", code='" + code + '\'' +
                ", spec='" + spec + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", in_price=" + in_price +
                ", out_price=" + out_price +
                ", ch_spec='" + ch_spec + '\'' +
                '}';
    }
}

