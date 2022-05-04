package cn.edu.gzy.controller;

import javax.servlet.ServletException;
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
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 登录成功后，跳转到本servlet,主要负责视图中需要显示的微博数据。
 */
@WebServlet(name="MemberServlet", urlPatterns = "/member")
public class MemberServlet extends HttpServlet {
    //定义微博信息的保存路径
    //private final String USERS = "D:/WorkSpace/Data/blog/users/";
    private final String USERS = "C:/Code/Data/blog/users";
    //显示微博详情页
    private final String MEMBER_PATH = "/showMember";
    private final String LOGIN_PATH="/myblog/index.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req,resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        //判断用户是通过记住密码登录了
        if(req.getSession().getAttribute("login") == null){
            resp.sendRedirect(LOGIN_PATH);//跳转回登录页面
            return;
        }
        Map<Long,String> messages = messages(getUsername(req));//获取微博信息
        req.setAttribute("messages",messages);
        req.getRequestDispatcher(MEMBER_PATH).forward(req,resp);//将数据连同请求转发给负责显示页面的servlet,来显示微博信息。
    }



    /**
     * 获取登录用户
     * @param req
     * @return
     */
    private String getUsername(HttpServletRequest req) {
        return (String) req.getSession().getAttribute("login");
    }

    /**
     * 获取登录用户的微博信息，注意微博信息是保存在文件中的
     * @param username
     * @return
     */
    private Map<Long, String> messages(String username) throws IOException{
        //1. 构造保存路径
        Path userHome = Paths.get(USERS, username);
        //构造保存微博信息的Map对象，为了排序，使用了TreeMap
        //Comparator.reverseOrder是Java 8中引入的一个静态方法，它返回比较器，对对象集合进行反向自然排序。
        Map<Long, String> messages = new TreeMap<>(Comparator.reverseOrder());
        //显示指定目录中的所有.txt文件
        try(DirectoryStream<Path> logs = Files.newDirectoryStream(userHome, "*.log")){
            for(Path log : logs){
                String millis = log.getFileName().toString().replace(".log", "");//获得文件名，这里保存的时候，文件名用的是自1970年1月1日0时0分以来的毫秒数
                //从文件中读取字节流，并用系统分隔符进行连接
                String msg = Files.readAllLines(log).stream()
                        .collect(Collectors.joining(System.lineSeparator()));
                messages.put(Long.parseLong(millis),msg);
            }
        }
        return messages;
    }

}
