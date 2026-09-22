package service;

import dao.impl.CustomerDaoImpl;
import entity.Address;
import entity.Customer;

import java.util.HashMap;
import java.util.List;

public class CustomerServiceImpl implements ICustomerServiceImpl {
    //引入持久层对象Db
    CustomerDaoImpl cai = new CustomerDaoImpl();

    @Override
    public List<Customer> findAll(HashMap<String, Object> prop){return null;}

    @Override
    public Customer login(String u, String p) {
//        HashMap<String,Object> prop =new HashMap<>();
//        prop.put("u",u);
//        prop.put("p",p);
        return cai.login(u, p);
    }


    @Override
    public Customer findById(int id) {
        return cai.findById(id);
    }
    @Override
    public int addCustomer(Customer customer){return cai.insert(customer); }

    public boolean isExist(String un) {
        return cai.isExist(un);
    }
    @Override
    public int updatePassword(int id, String newPassword) {
        //创建Customer 类的实例对象
        Customer customer = new Customer();

        //将传入的用户ID赋值给 customer 对象的 id 属性
        customer.setId(id);

        //将传入的新密码赋值给 customer 对象的 pass 属性
        customer.setPass(newPassword);
        // 确保获取当前用户的头像信息并设置到新密码的Customer对象中
        Customer currentCustomer = cai.findById(id);
        if (currentCustomer != null) {
            customer.setImgUrl(currentCustomer.getImgUrl());
        }

        //调用 Dao 层 update方法更新密码
        return cai.update(customer);
    }
    @Override
    public int update(Customer customer) {
        return cai.update(customer);
    }

    @Override
    public int updateUserInfo(int id, String realname, String intro, String tel, String email, String gender, String cardID) {
        return cai.updateUserInfo(id, realname, intro, tel, email, gender, cardID);
    }

    @Override
    public int updateAddress(int customerId, String detailAddress, boolean isDefault) {
        return cai.updateAddress(customerId, detailAddress, isDefault);
    }

    public int addAddress(Address address) {
        return cai.insertAddress(address);
    }

    public int getLastInsertId() {
        return cai.getLastInsertId();
    }

    public boolean isUniqueTel(String tel, int customerId) {
        return cai.isUniqueTel(tel, customerId);
    }

    public boolean isUniqueEmail(String email, int customerId) {
        return cai.isUniqueEmail(email, customerId);
    }

}
