package cn.edu.gzy.controller;

import cn.edu.gzy.model.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet(name = "RegisterServlet",urlPatterns = {"/register"},
            initParams = {
                @WebInitParam(name = "SUCCESS_PATH", value = "/WEB-INF/jsp/register_success.jsp"),
                @WebInitParam(name = "ERROR_PATH", value = "/WEB-INF/jsp/register_error.jsp")
            })
public class RegisterServlet extends HttpServlet {
    // ():圆括号()是组，主要应用在限制多选结构的范围/分组/捕获文本/环视/特殊模式处理
    // +:匹配前面的子表达式一次或多次。
    //*:匹配前面的子表达式零次或多次。
    //?:匹配前面的子表达式零次或一次，或指明一个非贪婪限定符。
    //{n,m}:m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。例如，"o{1,3}" 将匹配 "fooooood" 中的前三个 o。
    // 'o{0,1}' 等价于 'o?'。请注意在逗号和两个数之间不能有空格。
    private final Pattern emailRegex = Pattern.compile("^[_a-z0-9-]+([.][_a-z0-9-]+)*@[a-z0-9-]+([.][a-z0-9-]+)*$");
    // \w: 匹配所有的文字字符：所有字母、数字或下划线。
    private final Pattern passwdRegex = Pattern.compile("^\\w{8,16}$");//密码必须是8-16位的数字、字母或下划线
    private final Pattern usernameRegex = Pattern.compile("^\\w{1,16}$");//账号必须是1-16位的数字、字母或下划线
    private final Pattern phoneRegex =
            Pattern.compile("^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\\d{8}$");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phone = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirmPwd = req.getParameter("confirmPwd");
        System.out.printf("后台获取的注册手机号:%s，密码：%s,确认密码：%s",phone,password,confirmPwd);
        List<String> errors = new ArrayList<>();
        if(!validatePhone(phone)){
            errors.add("未填写手机号码或手机号码不正确！");
        }
        if(!validatePassword(password,confirmPwd)){
            errors.add("请确认密码是否符合格式并再次确认密码！");
        }
        String path;
        if(errors.isEmpty()){
            path = getInitParameter("SUCCESS_PATH");
            //tryCreateUser(phone,password);
            UserService userService = (UserService) getServletContext().getAttribute("userService");
            userService.tryCreateUser(phone,password);
        }else{
            //path = ERROR_PATH;
            path = getInitParameter("ERROR_PATH");
            req.setAttribute("errors",errors);
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }
    /**
     * 判断前后密码是否一致
     * @param password
     * @param confirmPwd
     * @return
     */
    private boolean validatePassword(String password, String confirmPwd) {
        return password != null && passwdRegex.matcher(password).find() && password.equals(confirmPwd);
    }

    //后端判断用户提交手机号码是否正确
    private boolean validatePhone(String phone) {
        return phone != null && phoneRegex.matcher(phone).find();
    }

}
