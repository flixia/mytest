<%--
  Created by IntelliJ IDEA.
  User: m
  Date: 2021/11/26
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; charset=UTF-8" %>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<h1>hello，${sessionScope.name}！</h1>--%>
<%--<h1>hello，${requestScope.msg}！</h1>--%>
<h1>hello，${sessionScope.user.username}!</h1>
<div>
    <p>${CRUDmsg}</p>
    <a href="${pageContext.request.contextPath}/user/welcome.do">返回欢迎页面</a>
</div>

<div>
    <form action="${pageContext.request.contextPath}/user/upload.do" enctype="multipart/form-data" method="post">
        上传头像:<input type="file" name="image"><br>
               <input type="submit" value="上传">
    </form>
</div>

</body>
<body>
<%--hello,${requestScope.user.username}!--%>
<%--你今年${requestScope.user.age}岁。--%>
<%--年龄:${requestScope.user.age}<br>--%>
</body>
</html>
