package cn.edu.gzy.controller;

import cn.edu.gzy.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet(name="LoginServlet",urlPatterns = "/login",
                initParams = {
                    @WebInitParam(name = "SUCCESS_PATH", value = "/myblog/member"),
                    @WebInitParam(name = "ERROR_PATH", value = "/myblog/error.html")
                }
            )
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String loginName = req.getParameter("loginName");
        String password = req.getParameter("password");
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        String page;
        if(userService.login(loginName,password)){//如果校验通过了
            if(req.getSession(false) != null){//如果存在Session
                req.changeSessionId();//为了安全起见，登录成功后，改变sessionID
            }
            req.getSession().setAttribute("login", loginName);//将登录用户信息保存到login变量中。
            //req.getRequestDispatcher("/admin/index.html").forward(req,resp);
            //登录成功，需要跳转到显示微博列表以及发送微博的界面，这里会重定向到/member地址
            //resp.sendRedirect("/myblog/member");
            page = getInitParameter("SUCCESS_PATH");
        }else{
            //resp.sendRedirect("error.html");
            page = getInitParameter("ERROR_PATH");
        }
        resp.sendRedirect(page);
    }
}
