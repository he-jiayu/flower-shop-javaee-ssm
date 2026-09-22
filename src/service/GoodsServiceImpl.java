package service;

import dao.impl.Goods;
import dao.impl.GoodsDaoImpl;

import java.util.HashMap;
import java.util.List;

//服务层的实现
public class GoodsServiceImpl implements IGoodsService {
    //  引入持久层对象
    GoodsDaoImpl dai = new GoodsDaoImpl();

    @Override
    public List<Goods> findAll(HashMap<String, Object> prop) {
        return dai.findByProp(prop);
    }

    @Override
    public Goods findById(int id) {
        HashMap<String, Object> prop = new HashMap<>();
        prop.put("id", id);
        List<Goods> goodsList = dai.findByProp(prop);
        return goodsList.isEmpty() ? null : goodsList.get(0);
    }

}