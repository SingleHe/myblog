package cn.edu.gzy.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注销登录
 */
@WebServlet(urlPatterns = "/logout",
                initParams = {
                    @WebInitParam(name = "LOGIN_PATH" ,value = "/myblog/index.html")
                }
            )
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果有对应的登录session
        /*if(req.getSession().getAttribute("login") != null){
            req.getSession().invalidate();//手动让session失效。
        }*/
        //已经由过滤器实现了
        req.getSession().invalidate();
        resp.sendRedirect(getInitParameter("LOGIN_PATH"));//直接跳转回登录界面
    }
}
