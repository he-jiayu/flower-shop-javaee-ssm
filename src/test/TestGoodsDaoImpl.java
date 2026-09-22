package test;

import dao.impl.Goods;
import dao.impl.GoodsDaoImpl;

public class TestGoodsDaoImpl {
    public static void main(String[] args) {
//        System.out.println(new GoodsDaoImpl().findByProp(null).size());
        for (Goods g : new GoodsDaoImpl().findByProp(null)){
            System.out.println(g);
        }
    }
}
