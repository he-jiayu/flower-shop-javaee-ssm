package controller;

import dao.impl.Goods;
import entity.GoodsItem;
import service.GoodsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/checkout")
public class CheckoutServlet extends HttpServlet {
    private GoodsServiceImpl goodsService = new GoodsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        Goods product = goodsService.findById(productId);

        // 创建购物项
        GoodsItem item = new GoodsItem();
        item.setGoods(product);
        item.setGoodsId(productId);
        item.setNum(1); // 默认购买数量为1

        // 创建购物车列表（只包含当前商品）
        List<GoodsItem> cart = new ArrayList<>();
        cart.add(item);

        // 设置到session
        HttpSession session = req.getSession();
        session.setAttribute("checkoutCart", cart);

        // 重定向到结算页
        resp.sendRedirect("/shoppingCart");
    }
}