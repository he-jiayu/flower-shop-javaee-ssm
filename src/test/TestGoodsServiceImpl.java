package test;

import dao.impl.Goods;
import service.GoodsServiceImpl;

public class TestGoodsServiceImpl {
    public static void main(String[] args) {
//        System.out.println(new GoodsDaoImpl().findByProp(null).size());
        for (Goods g : new GoodsServiceImpl().findAll(null)){
            System.out.println(g);
        }
    }

}
