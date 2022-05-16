package cn.edu.gzy.view;

import cn.edu.gzy.listener.OnlineUsers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * 负责显示微博信息列表页，以及发送微博表单
 */
@WebServlet(urlPatterns = "/showMember")
public class MemberView extends HttpServlet {
    private final String LOGIN_PATH = "index.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        //1. 判断用户是否登录
        if(req.getSession().getAttribute("login") == null){
            resp.sendRedirect(LOGIN_PATH);
            return;
        }
        String username = getUsername(req);
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>新浪微博</title>");
        out.println("<link rel='stylesheet' href='css/member.css' type='text/css'>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='leftPanel'>");
        out.println("<img class='my_icon' src='img/gzy_logo.jpg' alt='新浪微博' /><br><br>");
        out.printf("<h4>目前在线人数:%d 人</h4>", OnlineUsers.counter);
        out.printf("<a href='logout'>登出 %s</a>", username);
        out.println("</div>");
        // 微博发送框，将用户填写的微博信息提交给/myblog/new_message处理
        out.println("<form method='post' action='/myblog/new_message'>");
        out.println("分享新鮮事...<br>");
        String preMsg = req.getParameter("msg");//判断是否有预先保留用户输入的微博信息
        if(preMsg == null){
            preMsg = "";
        }else{
            out.println("<h3 style='color:red;'>信息需要在140字以内</h3>");
        }
        out.printf("<textarea cols='60' rows='4' name='msg'>%s</textarea><br/>",preMsg);
        out.println("<button type='submit'>发送</button>");
        out.println("</form>");
        //微博发送框下的显示微博列表  使用的是table来显示
        out.println("<table border='0' cellpadding='2' cellspacing='2'>");
        out.println("<thead>");
        out.println("<tr><th><hr></th></tr>");
        out.println("</thead>");
        out.println("<tbody>");
        Map<Long, String> messages = (Map<Long, String>) req.getAttribute("messages");//获取从MemberServlet转发过来的微博信息
        //循环显示微博信息,
        messages.forEach((millis,msg)->{
            //Instant类它代表的是时间戳,你可已使用instant.toEpochMilli()来输出Long类型的毫秒数
            LocalDateTime dateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of("UTC+8")).toLocalDateTime();//获取东八区时区
            out.println("<tr><td style='vertical-align:top;'>");
            out.printf("%s<br/>",username);
            out.printf("%s<br>",msg);
            out.println(dateTime);
            out.println("<form method='post' action='/myblog/del_message'>");
            out.printf("<input type='hidden' name='millis' value='%s'>",millis);
            out.println("<button type='submit'>删除</button>");
            out.println("</form>");
            out.println("<hr>");
            out.println("</td>");
            out.println("</tr>");
        });
        out.println("</tbody>");
        out.println("</table>");
        out.println("<hr>");
        out.println("</body>");
        out.println("</html>");
    }

    private String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("login");
    }
}
