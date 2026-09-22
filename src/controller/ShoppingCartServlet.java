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
import java.util.Objects;

@WebServlet(urlPatterns = "/shoppingCart")
public class ShoppingCartServlet extends HttpServlet {
    GoodsServiceImpl gsi = new GoodsServiceImpl();
    private boolean flag;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("customer") == null) {
            req.getRequestDispatcher("/login").forward(req, resp);
        } else {
            HttpSession session = req.getSession();
            List<GoodsItem> cart = (List<GoodsItem>) session.getAttribute("cart");

            // 处理前端传来的操作
            String action = req.getParameter("action");
            if (action != null) {
                String goodsId = req.getParameter("goodsId");

                if ("updateQuantity".equals(action)) {
                    int newQuantity = Integer.parseInt(req.getParameter("quantity"));
                    // 更新数量
                    for (GoodsItem item : cart) {
                        if (String.valueOf(item.getGoods().getId()).equals(goodsId)) {
                            item.setNum(newQuantity);
                            break;
                        }
                    }
                } else if ("deleteItem".equals(action)) {
                    // 删除商品
                    for (int i = 0; i < cart.size(); i++) {
                        if (String.valueOf(cart.get(i).getGoods().getId()).equals(goodsId)) {
                            cart.remove(i);
                            break;
                        }
                    }
                }

                session.setAttribute("cart", cart);
                return; // 直接返回，不需要重定向
            }

            if (cart == null) {
                cart = new ArrayList<>();
            }

            // 计算总计金额
            double totalAmount = 0;
            for (GoodsItem item : cart) {
                totalAmount += item.getGoods().getOut_price() * item.getNum();
            }

            session.setAttribute("totalAmount", totalAmount);
            req.setAttribute("totalAmount", totalAmount);

            req.getRequestDispatcher("shoppingCart.jsp").forward(req, resp);
        }
    }
}