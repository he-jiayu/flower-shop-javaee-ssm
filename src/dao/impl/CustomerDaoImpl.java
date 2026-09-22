package dao.impl;

import dao.Db;
import entity.Address;
import entity.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomerDaoImpl extends Db implements ICustomerDao, ICustomerDaoImpl {
    private Connection conn = getConn();
    private Statement stmt=null;
    private ResultSet rs =null;
    private  PreparedStatement prst=null;
    String sql ="";

    @Override
    public List<Customer> findByProp(HashMap<String,Object>prop) {
//        //      如果查询参数为null，则是全部查询
//        String sql = null;
////        if (prop==null){
////            sql= "select * from goods";
////        }else if(prop.containsKey("id")){
//            sql= "select * from customer where username=? and pass=?";
////        }
//        Customer customer=null;
//        List<Customer> customerList=new ArrayList<>();
//        try {
//            prst = conn.prepareStatement(sql);
////           String sql= "select * from goods";
//            prst.setString(1,(String)prop.get("u"));
//            prst.setString(2,(String)prop.get("p"));
//            rs=prst.executeQuery();
//            // 展开结果集数据库
//            while (rs.next()) {
//                customer=new Customer();
//
//                customer.setId(rs.getInt("id"));
//                customer.setPass(rs.getString("pass"));
//                customer.setUsername(rs.getString("username"));
//
//                customerList.add(customer);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return null;
    }
//    @Override
//    public int insert(Customer customer) {
//        int i = 0;
//        // 首先检查数据库中是否已存在相同的 username
//        String sql="insert into customer(username,pass,tel) values(?,?,?)";
//        try {
//            prst=   conn.prepareStatement(sql);
//            //表中最小字段（非空）插入
//            prst.setString(1,customer.getUsername());
//            prst.setString(2,customer.getPass());
//            prst.setString(3,customer.getTel());
//            i=  prst.executeUpdate();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return i;
//    }
@Override
public int insert(Customer customer) {
    int i = 0;
    // 首先检查数据库中是否已存在相同的 username
    String sql = "insert into customer(username, pass, tel) values(?,?,?)";
    try {
        prst = conn.prepareStatement(sql);
        // 表中最小字段（非空）插入
        prst.setString(1, customer.getUsername());
        prst.setString(2, customer.getPass());
        prst.setString(3, customer.getTel());
        i = prst.executeUpdate();
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    } finally {
        try {
            if (prst != null) {
                prst.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return i;
}
    public Customer login(String u,String p){
        //      如果查询参数为null，则是全部查询
        String sql = null;
//        if (prop==null){
//            sql= "select * from goods";
//        }else if(prop.containsKey("id")){
        sql= "select * from customer where username=? and pass=?";
//        }
        Customer customer=null;
        List<Customer> customerList=new ArrayList<>();
        try {
            prst = conn.prepareStatement(sql);
//           String sql= "select * from goods";
            prst.setString(1,u);
            prst.setString(2,p);
            rs=prst.executeQuery();
            // 展开结果集数据库
            if (rs.next()) {
                customer=new Customer();

                customer.setId(rs.getInt("id"));
                customer.setPass(rs.getString("pass"));
                customer.setUsername(rs.getString("username"));

//                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;

    }

    public boolean isExist(String un){
        boolean f=false;
        sql= "select username from customer where username=?";
        Customer customer=null;
        try {
            prst = conn.prepareStatement(sql);
            prst.setString(1,un);
            rs=prst.executeQuery();
            // 展开结果集数据库
            if (rs.next()) {
                f=true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  f;
    }
    @Override
    public int update(Customer customer) {
        // 新增更新密码的方法
        int i = 0;
        String sql = "UPDATE customer SET pass = ?, imgUrl = ? WHERE id = ?";
        try {
            prst = conn.prepareStatement(sql);
            prst.setString(1, customer.getPass());
            prst.setString(2, customer.getImgUrl());
            prst.setInt(3, customer.getId());
            i = prst.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (prst != null) {
                    prst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }


    @Override
    public int delete(int id){return 0;}

    public Customer findById(int id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        Customer customer = null;
        try {
            prst = conn.prepareStatement(sql);
            prst.setInt(1, id);
            rs = prst.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setPass(rs.getString("pass"));
                customer.setUsername(rs.getString("username"));
                customer.setImgUrl(rs.getString("imgUrl")); // 确保获取头像信息
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    // 新增更新用户信息的方法
    @Override
    public int updateUserInfo(int id, String realname, String intro, String tel, String email, String gender, String cardID) {
        int i = 0;
        String sql = "UPDATE customer SET realname = ?, intro = ?, tel = ?, email = ?, gender = ?, cardID = ? WHERE id = ?";
        try {
            prst = conn.prepareStatement(sql);
            prst.setString(1, realname);
            prst.setString(2, intro);
            prst.setString(3, tel);
            prst.setString(4, email);
            prst.setString(5, gender);
            prst.setString(6, cardID);
            prst.setInt(7, id);
            i = prst.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                if (prst != null) {
                    prst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
    @Override
    public int updateAddress(int customerId, String detailAddress, boolean isDefault) {
        int i = 0;
        String sql = "UPDATE address SET address = ?, isDefault = ? WHERE customer_id = ?";
        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, detailAddress);
            prst.setBoolean(2, isDefault);
            prst.setInt(3, customerId);
            i = prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prst != null) {
                    prst.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public int insertAddress(Address address) {
        int i = 0;
        String sql = "INSERT INTO address (customer_id, address, isDefault) VALUES (?, ?, ?)";
        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setInt(1, address.getCustomerId());
            prst.setString(2, address.getAddress());
            prst.setBoolean(3, address.isDefault());
            i = prst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public int getLastInsertId() {
        int id = 0;
        try {
            ResultSet rs = conn.getMetaData().getPrimaryKeys(null, null, "customer");
            if (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                Statement stmt = conn.createStatement();
                ResultSet generatedKeys = stmt.executeQuery("SELECT LAST_INSERT_ID();");
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean isUniqueTel(String tel, int customerId) {
        boolean isUnique = true;
        String sql = "SELECT tel FROM customer WHERE tel = ? AND id <> ?";
        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, tel);
            prst.setInt(2, customerId);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                isUnique = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUnique;
    }

    public boolean isUniqueEmail(String email, int customerId) {
        boolean isUnique = true;
        String sql = "SELECT email FROM customer WHERE email = ? AND id <> ?";
        try (PreparedStatement prst = conn.prepareStatement(sql)) {
            prst.setString(1, email);
            prst.setInt(2, customerId);
            ResultSet rs = prst.executeQuery();
            if (rs.next()) {
                isUnique = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUnique;
    }




    @Override
    public List<Customer> findByProp(Object prop) {
        return null;
    }

}
