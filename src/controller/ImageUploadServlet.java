package controller;

import entity.Customer;
import jdk.nashorn.internal.runtime.Scope;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.CustomerServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * 类;功能(方法)
 */
@WebServlet(urlPatterns = "/upload")
@MultipartConfig
public class ImageUploadServlet extends HttpServlet {

    //选择合适的方法
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/uploadFile.jsp").forward(req, resp);
    }

    //正确使用(入参;返回值)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        //构建工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //设置临时文件夹
        File f = new File("C:\\tempImg");
        if (!f.exists()) {
            f.mkdirs();
        }
        //设置文件缓存路径
        factory.setRepository(f);
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        fileUpload.setHeaderEncoding("utf-8");
        try {
            List<FileItem> fileItemList = fileUpload.parseRequest(req);
            PrintWriter writer = resp.getWriter();
            for (FileItem fileItem : fileItemList) {
                if (fileItem.isFormField()) {
                    // 此部分是输出上传者（书本代码），没有必要，可注释掉
                } else {
                    // 获取上传文件名
                    String filename = fileItem.getName();
                    // 处理上传文件
                    if (filename != null && !filename.equals("")) {
                        writer.println("upload file=" + filename + "<br>");
                        ///                    提取文件名，确保不重复
                        filename.substring(filename.lastIndexOf("\\") + 1);
//                        filename = UUID.randomUUID().toString() + "_" + filename;
                        // 设置上传存放路径目录（当然你也可放于web/images）
                        String webPath = "/upload/";
                        String filePath = getServletContext().getRealPath(webPath + filename);
                        File file = new File(filePath);
                        // 创建服务器上传路径目录
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                        // 构建输入流 输出流
                        InputStream in = fileItem.getInputStream();
                        FileOutputStream out = new FileOutputStream(file);
                        // 设置缓冲区大小
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = in.read(buffer)) > 0)
                            out.write(buffer, 0, len);
                        // 从session拿到customer
                        HttpSession session = req.getSession();
                        Customer customer = (Customer) session.getAttribute("customer");
                        if (customer != null) {
                            // 修改customer的图片名称customer.setImgUrl(filename)
                            customer.setImgUrl(webPath + filename);
                            // 回写customer进session
                            session.setAttribute("customer", customer);
                            // 调用service的update方法，改写customer表中的头像字段
                            CustomerServiceImpl customerService = new CustomerServiceImpl();
                            int updateResult = customerService.update(customer);
                            if (updateResult > 0) {
                                // 上传成功
                                writer.println("上传成功，数据库信息已更新。");
                            } else {
                                // 上传成功但数据库更新失败
                                writer.println("上传成功，但数据库更新失败。");
                            }
                        } else {
                            // 未找到customer对象
                            writer.println("未找到用户信息，上传失败。");
                        }
                        // 流关闭
                        in.close();
                        out.close();
                        // 删除临时文件
                        fileItem.delete();
                    }
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/uploadFile.jsp");
        dispatcher.forward(req, resp);
    }
}