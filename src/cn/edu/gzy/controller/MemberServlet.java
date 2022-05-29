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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 登录成功后，跳转到本servlet,主要负责视图中需要显示的微博数据。
 */
@WebServlet(name="MemberServlet", urlPatterns = "/member",
    initParams = {
        @WebInitParam(name = "MEMBER_PATH", value = "/WEB-INF/jsp/member.jsp")
    }
)
public class MemberServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        //Map<Long,String> messages = messages(getUsername(req));//获取微博信息
        //Map<Long,String> messages = userService.messages(getUsername(req));//获取微博信息
        List<Message> messages = userService.messages(getUsername(req));
        req.setAttribute("messages",messages);
        req.getRequestDispatcher(getInitParameter("MEMBER_PATH")).forward(req,resp);//将数据连同请求转发给负责显示页面的servlet,来显示微博信息。
    }



    /**
     * 获取登录用户
     * @param req
     * @return
     */
    private String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("login");
    }

}
