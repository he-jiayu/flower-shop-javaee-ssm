package controller;

import dao.impl.Goods;
import dao.impl.GoodsDaoImpl;
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
import java.util.HashMap;
import java.util.List;

@WebServlet(urlPatterns = "/productDetail")
public class ProductDetailServlet extends HttpServlet {
    private GoodsServiceImpl goodsService = new GoodsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取商品ID
        int id = Integer.parseInt(req.getParameter("id"));

        // 获取商品详情
        Goods product = goodsService.findById(id);

        // 获取商品评价（假设有评价服务）
        // List<Review> reviews = reviewService.getReviewsByProductId(id);

        // 设置商品详情到request
        req.setAttribute("product", product);
        // req.setAttribute("reviews", reviews);

        // 转发到商品详情页
        req.getRequestDispatcher("productDetail.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 处理添加到购物车或立即购买请求
        int id = Integer.parseInt(req.getParameter("id"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String action = req.getParameter("action"); // "addToCart" 或 "buyNow"

        Goods product = goodsService.findById(id);
        HttpSession session = req.getSession();

        if("addToCart".equals(action)) {
            // 添加到购物车逻辑
            List<GoodsItem> cart = (List<GoodsItem>) session.getAttribute("cart");
            if(cart == null) {
                cart = new ArrayList<>();
            }

            boolean found = false;
            for(GoodsItem item : cart) {
                if(item.getGoods().getId() == id) {
                    item.setNum(item.getNum() + quantity);
                    found = true;
                    break;
                }
            }

            if(!found) {
                GoodsItem newItem = new GoodsItem();
                newItem.setGoods(product);
                newItem.setGoodsId(id);
                newItem.setNum(quantity);
                cart.add(newItem);
            }

            session.setAttribute("cart", cart);
            resp.sendRedirect("/shoppingCart");
        } else if("buyNow".equals(action)) {
            // 立即购买逻辑 - 创建独立的buyNowCart
            List<GoodsItem> buyNowCart = new ArrayList<>();
            GoodsItem item = new GoodsItem();
            item.setGoods(product);
            item.setGoodsId(id);
            item.setNum(quantity);
            buyNowCart.add(item);

            // 计算总金额并存入session
            double totalAmount = product.getOut_price() * quantity;
            session.setAttribute("buyNowTotalAmount", totalAmount);

            // 将buyNowCart存入session
            session.setAttribute("buyNowCart", buyNowCart);
            // 清除之前的checkout标记
            session.removeAttribute("fromBuyNow");
            // 设置当前是立即购买流程
            session.setAttribute("fromBuyNow", true);
            // 直接跳转到订单结算页
            resp.sendRedirect("/order");
        }
    }
}