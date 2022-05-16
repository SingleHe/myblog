package cn.edu.gzy.web;

import cn.edu.gzy.model.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 上下文环境监听器，将UserService对象保存在域中，让所有的Servlet能够访问
 */
@WebListener
public class MyBlogInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String USERS = sce.getServletContext().getInitParameter("USERS");
        context.setAttribute("userService",new UserService(USERS));
    }
}
