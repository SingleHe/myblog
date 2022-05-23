package cn.edu.gzy.controller;

import cn.edu.gzy.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 删除微博
 */
@WebServlet(urlPatterns = "/del_message",
            initParams = {
                @WebInitParam(name = "MEMBER_PATH", value = "/myblog/member")
            }
        )
public class DelMessageServlet extends HttpServlet {

    private final String MEMBER_PATH = "/myblog/member";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String millis = req.getParameter("millis");//在视图servlet中，会在删除空间中提交数据
        if(millis != null){
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            userService.deleteMessage(getUsername(req),millis);
        }
        resp.sendRedirect(MEMBER_PATH);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    private String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("login");
    }
}
