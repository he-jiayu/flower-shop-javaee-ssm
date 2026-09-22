package controller;

import entity.Sp;
import service.GoodsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/index")
public class indexServlet extends HttpServlet {
    //    引入服务层对象
    GoodsServiceImpl gsi = new GoodsServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//调用服务层方法，封装数据
        req.setAttribute("spList", gsi.findAll(null));
//        req.getRequestDispatcher("showData.jsp").forward(req, resp);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

}