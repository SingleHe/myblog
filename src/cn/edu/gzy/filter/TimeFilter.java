package cn.edu.gzy.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器：记录Servlet处理请求与响应的时间差
 */
@WebFilter("/*")
public class TimeFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        long begin = current();
        chain.doFilter(request,response);//如果调用了FilterChain的doFilter()方法，就会运行下一个过滤器，如果没有下一个过滤器了，就调用请求目标的Servlet的service()方法。
        getServletContext().log(String.format("请求经过 %d ms处理完成",current()-begin));
    }

    private long current() {
        return System.currentTimeMillis();
    }
}
