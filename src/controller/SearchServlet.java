package controller;

import dao.impl.GoodsDaoImpl;
import dao.impl.Goods;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private GoodsDaoImpl goodsDao = new GoodsDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户输入的关键词
        String keyword = request.getParameter("keyword");

        // 调用DAO查询商品
        List<Goods> goodsList = goodsDao.findByKeywords(keyword);

        // 将查询结果存入请求范围
        request.setAttribute("spList", goodsList);

        // 跳转到首页显示结果
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 如果需要支持POST请求，可以在这里处理
        doGet(request, response);
    }
}
