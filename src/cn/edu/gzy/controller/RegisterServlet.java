package cn.edu.gzy.controller;

import javax.servlet.ServletException;
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

@WebServlet(name = "RegisterServlet",urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    //private final String USERS = "D:/WorkSpace/Data/blog/users";
    private final String USERS = "C:/Code/Data/blog/users";
    private final String SUCCESS_PATH = "/reg_success";
    private final String ERROR_PATH = "/reg_error";
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
            path = SUCCESS_PATH;
            tryCreateUser(phone,password);
        }else{
            path = ERROR_PATH;
            req.setAttribute("errors",errors);
        }
        req.getRequestDispatcher(path).forward(req,resp);
    }

    /**
     * 如果输入正确，则创建用户，尚未讲到数据库，先把数据保存到文件中
     * @param phone
     * @param password
     */
    private void tryCreateUser(String phone, String password) throws IOException{
        Path userHome = Paths.get(USERS, phone);
        if(Files.notExists(userHome)){//检查用户文件夹是否创建，以确认用户是否已经注册
            createUser(userHome, phone,password);
        }
    }

    /**
     * 创建用户
     * @param userHome
     * @param phone
     * @param password
     */
    private void createUser(Path userHome, String phone, String password) throws IOException{
        Files.createDirectories(userHome);
        int salt = (int)(Math.random() * 100);//随机生成0-100之间的整数
        String encrypt = String.valueOf(salt + password.hashCode());//密码做了简单的加密处理。
        Path profile = userHome.resolve("profile.txt");
        System.out.println("保存账号密码的文件路径为:"+profile.toAbsolutePath());
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(profile)){
            bufferedWriter.write(String.format("%s\t%s\t%d",phone,encrypt,salt));
        }
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
