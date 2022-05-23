<%@ page import="cn.edu.gzy.listener.OnlineUsers" %>
<%@ page import="cn.edu.gzy.model.Message" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: TripleHe
  Date: 2022-05-24
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>你关注的人的微博动态</title>
    <link rel='stylesheet' href='<%= request.getServletContext().getContextPath()%>/css/member.css' type='text/css'>
</head>
<body>
    <div class='leftPanel'>
        <img class='my_icon' src='<%= request.getServletContext().getContextPath()%>/img/gzy_logo.jpg' alt='新浪微博' /><br><br>
        <h4>当前在线人数为: <%= OnlineUsers.counter %> 人</h4>
        <h3>${requestScope.userName}的微博</h3>
    </div>
    <table class="blog_table">
        <thead>
        <th><hr></th>
        </thead>
        <tbody>
            <%
                List<Message> messages = (List<Message>) request.getAttribute("messages");
                for(Message message : messages){
            %>
        <tr>
            <td style="vertical-align: top;">
                <%= message.getPhone()%><br/>
                <%= message.getMsg() %><br/><br/>
                <%= message.getLocalDateTime() %><br>
                <hr>
            </td>
        </tr>
            <%
            }
        %>
</body>
</html>
