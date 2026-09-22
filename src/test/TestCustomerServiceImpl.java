package test;

import dao.impl.CustomerDaoImpl;
import service.CustomerServiceImpl;

import java.util.HashMap;

public class TestCustomerServiceImpl {
    public static void main(String[] args) {
        CustomerServiceImpl csi=new CustomerServiceImpl();

        System.out.println(csi.login("chen","chen"));


    }
}
