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
    //private final String USERS = "D:/WorkSpace/Data/blog/users/";
    private final String USERS = "C:/Code/Data/blog/users";
    private final String LOGIN_PATH = "/myblog/index.html";
    private final String MEMBER_PATH = "/myblog/member";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        /*if(req.getSession().getAttribute("login") == null){
            resp.sendRedirect(LOGIN_PATH);//未登录需要重新跳转回登录界面
            return;
        }*/
        String millis = req.getParameter("millis");//在视图servlet中，会在删除空间中提交数据
        if(millis != null){
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            userService.deleteMessage(getUsername(req),millis);
        }
        resp.sendRedirect(MEMBER_PATH);
    }

    /**
     * 删除微博，也就是将指定的保存微博的文件删除。
     * @param username
     * @param millis
     * @throws IOException
     */
    private void deleteMessage(String username, String millis) throws IOException {
        //1. 构造保存路径
        Path txt = Paths.get(USERS, username, String.format("%s.log",millis));
        Files.delete(txt);
    }

    private String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("login");
    }
}
