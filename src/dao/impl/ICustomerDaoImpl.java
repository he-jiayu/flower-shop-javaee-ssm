package dao.impl;

import entity.Customer;

import java.util.HashMap;
import java.util.List;

public interface ICustomerDaoImpl {
    List<Customer> findByProp(HashMap<String, Object> prop);
    int insert(Customer customer);
    int update(Customer customer);


    int delete(int id);

    // 新增更新用户信息的方法
    int updateUserInfo(int id, String realname, String intro, String tel, String email, String gender, String cardID);

    int updateAddress(int customerId, String detailAddress, boolean isDefault);
}
