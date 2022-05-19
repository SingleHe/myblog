<%--
  Created by IntelliJ IDEA.
  User: TripleHe
  Date: 2022-05-19
  Time: 23:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    //JSP 脚本元素：声明标识
    String name = "CoupleHe";
    String password = "123456";
    boolean checkUser(String name, String password){
        return this.name.equals(name) && this.password.equals(password);
    }
%>
<html>
<head>
    <title>JSP的基本语法学习</title>
</head>
<body>
    <%
        // JSP 中的脚本代码片段
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if(checkUser(name, password)){
    %>
        <h1><%= name %>，登录成功！</h1>
    <%
        }else{
    %>
        <h1 style="color: red">登录失败！</h1>
    <%
        }
    %>
</body>
</html>
