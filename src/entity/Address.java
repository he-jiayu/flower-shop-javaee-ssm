package entity;

public class Address {
    private int id;
    private int customerId;
    private String address;
    private boolean isDefault;


    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", address='" + address + '\'' +
                ", isDefault=" + isDefault +
                '}';
    }
}