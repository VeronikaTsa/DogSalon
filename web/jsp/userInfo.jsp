<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 10.04.2018
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/cc.css">
    <title>${sessionScope.user.login}</title>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>
<form >
    email:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.email}"/>
    <br>
    <br>
    login:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.login}"/>
    <br>
    <br>
    picture:  ${sessionScope.user.userContent.picture}
    <br>
    <br>
    first name:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.userContent.firstName}"/>
    <br>
    <br>
    last name:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.userContent.lastName}"/>
    <br>
    <br>
    telephone:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.userContent.telephone}"/>
    <br>
    <br>
    birthday:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.userContent.birthday}"/>
    <br>
    <br>
    sex:
    <Input type="Text" name="codeToCompare" value="${sessionScope.user.userContent.sex}"/>
    <br>
    <br>
    <br>
    <br>
</form>


<br>
<br>
</body>
</html>
