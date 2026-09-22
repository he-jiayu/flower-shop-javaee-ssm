package entity;

public class Sp {
    private int id;
    private double price;
    private String name;
    private String intro;
    private int stock;
    private String pic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Sp{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", intro='" + intro + '\'' +
                ", stock=" + stock +
                ", pic='" + pic + '\'' +
                '}';
    }
}
