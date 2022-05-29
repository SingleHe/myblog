package cn.edu.gzy.controller;

import cn.edu.gzy.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

/**
 * 用于添加用户所发送的微博
 */
@WebServlet(urlPatterns = "/new_message",
    initParams = {
        @WebInitParam(name="MEMBER_PATH", value = "/myblog/member")
    }
)
public class NewMessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String msg = req.getParameter("msg");//取得用户发送的微博数据
        //如果用户什么都没写，就直接点击了发送按钮
        if(msg == null || msg.length() == 0){
            resp.sendRedirect(getInitParameter("MEMBER_PATH"));//如果什么数据都没有，直接跳转回信息界面 。
            return;
        }
        //所发送的微博信息不能超过140个字符
        if(msg.length() <= 140){
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            userService.addMessage(getUsername(req),msg);//添加登录用户发送的微博
            resp.sendRedirect(getInitParameter("MEMBER_PATH"));
        }else{
            req.getRequestDispatcher(getInitParameter("MEMBER_PATH")).forward(req,resp);
        }

    }

    /**
     * 判断用户是否已登录（检查从session中嫩不能渠道对应的session 对象）
     * @param req
     * @return
     */
    private String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("login");
    }
}
