package entity;

import java.sql.Timestamp;

public class Customer {
    private String username;
    private String pass;
    private String tel;
    private String email;
    private String gender;
    private String cardID;
    private String state;
    private Timestamp regTime;
    private String realname;
    private int id;
    private  String inro;
    private char level;
    private String imgUrl;


    // username的getter和setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // pass的getter和setter
    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    // tel的getter和setter
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInro() {
        return inro;
    }

    public void setInro(String inro) {
        this.inro = inro;
    }

    public char getLevel() {
        return level;
    }

    public void setLevel(char level) {
        this.level = level;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    // toString方法，用于打印Customer对象信息

    @Override
    public String toString() {
        return "Customer{" +
                "username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", cardID='" + cardID + '\'' +
                ", state=" + state +
                ", regTime=" + regTime +
                ", realname='" + realname + '\'' +
                ", id=" + id +
                ", inro='" + inro + '\'' +
                ", level=" + level +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}