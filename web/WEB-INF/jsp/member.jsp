<%@ page import="cn.edu.gzy.listener.OnlineUsers" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="cn.edu.gzy.model.Message" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: TripleHe
  Date: 2022-05-23
  Time: 23:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>赣职微博</title>
    <link rel='stylesheet' href='<%= request.getServletContext().getContextPath()%>/css/member.css' type='text/css'>
</head>
<body>
    <div class='leftPanel'>
        <img class='my_icon' src='<%= request.getServletContext().getContextPath()%>/img/gzy_logo.jpg' alt='新浪微博' /><br><br>
        <h4>当前在线人数为: <%= OnlineUsers.counter %> 人</h4>
        <a href="/myblog/logout">注销 ${sessionScope.login}</a>
    </div>
    <form method='post' action='/myblog/new_message'>
        <h5>分享新鮮事...</h5>
        <%
            String preMsg = request.getParameter("msg");
            if(preMsg != null){
        %>
        <h6 style="color: red;">请注意，发送的微博不能超过140字！</h6>
        <%
            }
        %>
        <textarea cols="60" rows="4" name="msg">${param.msg}</textarea><br/>
        <button type="submit">发送</button>
    </form>
    <table class="blog_table">
        <thead>
            <th><hr></th>
        </thead>
        <tbody>
            <%--
                Map<Long, String> messages = (Map<Long, String>) request.getAttribute("messages");
                for(Map.Entry<Long, String> msgEntry : messages.entrySet()){
                    Long millis = msgEntry.getKey();
                    String msg = msgEntry.getValue();
                    LocalDateTime dateTime = Instant.ofEpochMilli(millis).atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    String msgTime = dateTime.format(formatter);
            --%>
            <%
                List<Message> messages = (List<Message>) request.getAttribute("messages");
                for(Message message : messages){
            %>
            <tr>
                <td style="vertical-align: top;">
                    <h3><%= message.getPhone()%><br><%= message.getMsg() %></h3>
                    <%= message.getLocalDateTime() %><br>
                    <a href="/myblog/del_message?millis=<%= message.getMillis() %>">删除</a>
                    <hr>
                </td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
</body>
</html>
