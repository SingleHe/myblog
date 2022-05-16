package cn.edu.gzy.web;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器，检查用户是否登录
 * 1. /member: 处理主界面中数据显示
 * 2. /showMember: 显示主界面（数据的显示）
 * 3. /new_message:  发送微博数据
 * 4. /logout: 登出
 * 5. /del_message: 删除微博数据
 */
@WebFilter(
        urlPatterns = {"/member","/showMember","/new_message","/logout","/del_message"},
        initParams = {
                @WebInitParam(name = "LOGIN_PATH", value = "/myblog/index.html")
        }

)
public class AccessController extends HttpFilter {
    private String LOGIN_PATH;

    @Override
    public void init() throws ServletException {
        this.LOGIN_PATH = getInitParameter("LOGIN_PATH");//从配置参数中获取登录界面的地址
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request.getSession().getAttribute("login") == null){
            response.sendRedirect(LOGIN_PATH);//未登录跳转回主界面
        }else{
            chain.doFilter(request,response);//已登录，继续完成下一个过滤器的调用。
        }
    }
}
