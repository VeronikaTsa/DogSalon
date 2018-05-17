<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 24.04.2018
  Time: 13:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>

    <title>Add feedback</title>
    <meta charset="UTF-8">
</head>
<body>
<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>
<form action="/ServletController" method="post">
    <Input type="Text" name="feedback" value="" width="100px" height="50px"/>
    <Input type="submit" value="Добавить отзыв"/>
    <input type="hidden" name="command" value="feedbackAdd" />
</form>

<br>
${requestScope.feedbackAddSuccess}


</body>
</html>
