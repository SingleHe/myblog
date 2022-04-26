package cn.edu.gzy.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/login")
public class Login extends HttpServlet {
    private final String USERS = "D:/WorkSpace/Data/blog/users";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String loginName = req.getParameter("loginName");
        String password = req.getParameter("password");
        if(login(loginName,password)){
            //req.getRequestDispatcher("/admin/index.html").forward(req,resp);
            resp.sendRedirect("/myblog/admin/index.html");
        }else{
            resp.sendRedirect("error.html");
        }
    }

    private boolean login(String loginName, String password) throws IOException{
        if(loginName != null && loginName.trim().length() != 0 && password != null){
            Path userHome = Paths.get(USERS,loginName);
            return Files.exists(userHome) && isCorrectPassword(password, userHome);
        }
        return  false;
    }

    /**
     * 判断密码是否正确
     * @param password
     * @param userHome
     * @return
     * @throws IOException
     */
    private boolean isCorrectPassword(String password, Path userHome) throws IOException{
        Path profile = userHome.resolve("profile.txt");
        try(BufferedReader bufferedReader = Files.newBufferedReader(profile)){
            String[] data = bufferedReader.readLine().split("\t");
            System.out.println("后台获取的密码长度："+data.length);
            int encrypt = Integer.parseInt(data[1]);
            int salt = Integer.parseInt(data[2]);
            return password.hashCode() + salt == encrypt;
        }
    }
}
