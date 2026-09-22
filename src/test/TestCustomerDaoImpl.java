package test;

import dao.impl.CustomerDaoImpl;
import dao.impl.Goods;
import dao.impl.GoodsDaoImpl;
import entity.Customer;

import java.util.HashMap;

public class TestCustomerDaoImpl {
    public static void main(String[] args) {
        //引入持久层
        CustomerDaoImpl cdi=new CustomerDaoImpl();
//        Customer c=new Customer();
//        c.setUsername("teacher888");
//        c.setTel("33333");
//        c.setPass("55555");

//        HashMap<String,Object> prop =new HashMap<>();
//        prop.put("u","admin");
//        prop.put("p","admin");
//        System.out.println(cdi.findByProp(prop));

       System.out.println(cdi.isExist("admin"));

    }
}
