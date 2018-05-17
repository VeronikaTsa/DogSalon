<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 22.04.2018
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/cc.css">
    <title>Ask question</title>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>
<form action="/ServletController" method="post" charset="UTF-8">
    <Input type="Text" name="question" value="" width="100px" height="50px"/>
    <Input type="submit" value="Задать вопрос"/>
    <input type="hidden" name="command" value="questionAsk" />
</form>

<br>
${requestScope.questionAskSuccess}
<br>
</body>
</html>
