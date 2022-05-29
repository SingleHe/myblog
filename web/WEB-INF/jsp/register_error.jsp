<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: TripleHe
  Date: 2022-05-23
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>账号注册失败</title>
</head>
<body>
  <h1>Oops! 账号注册失败！</h1>
  <h3>具体错误信息如下：</h3>
  <ul style="color:red;">
    <%
      List<String> errors = (List<String>) request.getAttribute("errors");
      for(String error : errors){
    %>
    <li><%= error%></li>
    <%
      }
    %>
  </ul>
  <a href="/myblog/register.html">点击返回注册页面！</a>
</body>
</html>
