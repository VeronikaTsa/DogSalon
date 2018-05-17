<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 26.04.2018
  Time: 0:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${sessionScope.someone.login}</title>
    <link rel="stylesheet" href="/css/cc.css">
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>

login: ${sessionScope.someone.login}
<br>
<br>

email: ${sessionScope.someone.email}
<br>
<br>
picture:  ${sessionScope.someone.userContent.picture}
<br>
<br>
first name: ${sessionScope.someone.userContent.firstName}
<br>
<br>
last name: ${sessionScope.someone.userContent.lastName}
<br>
<br>
telephone: ${sessionScope.someone.userContent.telephone}
<br>
<br>
birthday: ${sessionScope.someone.userContent.birthday}
<br>
<br>
sex:  ${sessionScope.someone.userContent.sex}
<br>
<br>
<br>
</body>
</html>
