package dao.impl;

import dao.Db;
import dao.IBaseDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 接口的实现
 */
/**
 * 接口的实现
 */
public class GoodsDaoImpl extends Db implements IBaseDao<Goods> {
    private Connection conn = getConn();
    private Statement stmt = null;
    private ResultSet rs = null;

    @Override
    public List<Goods> findByProp(HashMap<String, Object> prop) {
//      如果查询参数为null，则是全部查询
        String sql = null;
        if (prop==null){
            sql= "select * from goods";
        }else if(prop.containsKey("id")){
            sql= "select * from goods where id="+prop.get("id");
        }
        Goods goods=null;
        List<Goods> goodsList=new ArrayList<>();
        try {
            stmt = conn.createStatement();
//           String sql= "select * from goods";
            rs = stmt.executeQuery(sql);
            // 展开结果集数据库
            while (rs.next()) {
                goods=new Goods();

                goods.setId(rs.getInt("id"));
                goods.setStock(rs.getInt("stock"));
                goods.setKeywords(rs.getString("keywords"));
                goods.setCode(rs.getString("code"));
                goods.setSpec(rs.getString("spec"));
                goods.setImgUrl(rs.getString("imgUrl"));
                goods.setIn_price(rs.getDouble("in_price"));
                goods.setOut_price(rs.getDouble("out_price"));
                goods.setCh_spec(rs.getString("ch_spec"));
                goodsList.add(goods);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return goodsList;
    }
    @Override
    public int insert(Goods goods) {
        return 0;
    }

    @Override
    public int update(Goods goods) {
        return 0;
    }

    @Override
    public int delete(int id) {
        return 0;
    }

    // 新增方法：根据关键词搜索商品
    public List<Goods> findByKeywords(String keywords) {
        String sql = "SELECT * FROM goods WHERE keywords LIKE ?";
        List<Goods> goodsList = new ArrayList<>();
        PreparedStatement prst = null;
        try {
            prst = conn.prepareStatement(sql);
            prst.setString(1, "%" + keywords + "%");
            rs = prst.executeQuery();
            while (rs.next()) {
                Goods goods = new Goods();
                goods.setId(rs.getInt("id"));
                goods.setStock(rs.getInt("stock"));
                goods.setKeywords(rs.getString("keywords"));
                goods.setCode(rs.getString("code"));
                goods.setSpec(rs.getString("spec"));
                goods.setImgUrl(rs.getString("imgUrl"));
                goods.setIn_price(rs.getDouble("in_price"));
                goods.setOut_price(rs.getDouble("out_price"));
                goodsList.add(goods);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (prst != null) prst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return goodsList;
    }
}
