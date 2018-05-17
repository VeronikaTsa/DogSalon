<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 10.04.2018
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Sign Up</title>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>
<div style="margin-left: 300px">
<form action="/ServletController" method="post" charset="UTF-8">
    Вам на почту отправлено письмо с кодом. Введите код: <Input type="Text" name="codeToCompare" value=""/>
    <Input type="submit" value="Отправить"/>
    <input type="hidden" name="command" value="signupContinue" />
    <br>
    <br>
    <br>
</form>
</div>
</body>
</html>
