package dao.impl;

import entity.Customer;

import java.util.List;

public interface ICustomerDao {
    int insert(Customer customer);
    List<Customer> findByProp(Object prop);
}
