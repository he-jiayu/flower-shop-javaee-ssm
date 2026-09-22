package service;

import entity.Customer;

import java.util.HashMap;
import java.util.List;

public interface ICustomerServiceImpl {

    //查询所有
    List<Customer> findAll(HashMap<String,Object> prop);

    Customer login(String u,String p);
    //根据ID精准查询
    Customer findById(int id);


    // 添加用户（注册）
    int addCustomer(Customer customer);

    int updatePassword(int id, String newPassword);

    int update(Customer customer);

    int updateUserInfo(int id, String realname, String intro, String tel, String email, String gender, String cardID);

    int updateAddress(int customerId, String detailAddress, boolean isDefault);
}
