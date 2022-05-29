package cn.edu.gzy.controller;

import cn.edu.gzy.model.Message;
import cn.edu.gzy.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 当用户通过URL 指定要查看某个用户的微博时，可以查看
 */
@WebServlet(
        urlPatterns = {"/user/*"},
        initParams = {
                @WebInitParam(name="USER_PATH", value = "/WEB-INF/jsp/otherBlog.jsp")
        }
)
public class showOtherMsgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getPathInfo().substring(1);
        getServletContext().log("要查看的发表了微博的用户名为："+userName);
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        List<Message> messages = userService.messages(userName);
        req.setAttribute("messages", messages);
        req.setAttribute("userName", userName);
        req.getRequestDispatcher(getInitParameter("USER_PATH")).forward(req,resp);
    }
}
