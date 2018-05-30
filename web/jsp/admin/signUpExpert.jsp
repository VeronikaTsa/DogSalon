<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 13.05.2018
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign Up Expert</title>
    <link rel="stylesheet" href="/css/cc.css">
</head>
<body>
<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>

<div style="margin-left: 600px">
<form action="/ServletController" method="post" charset="UTF-8">
    Введите email: <Input type="Text" name="email" value=""/>
    <br>
    <br>
    Введите login: <Input type="Text" name="login" value=""/>
    <br>
    <br>

    <Input type="submit" value="Зарегистрировать"/>
    <input type="hidden" name="command" value="signupexpert" />
    <br>
    <br>
</form>
</div>

</body>
</html>
