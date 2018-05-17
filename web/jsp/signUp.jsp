<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 28.03.2018
  Time: 19:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/cc.css">

    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.enterEmail" var="enterEmail"/>
    <fmt:message bundle="${local}" key="message.enterPassword" var="enterPassword"/>
    <fmt:message bundle="${local}" key="message.enterLogin" var="enterLogin"/>
    <fmt:message bundle="${local}" key="message.repeatPassword" var="repeatPassword"/>
    <fmt:message bundle="${local}" key="message.signUp" var="signUp"/>
    <title>${signUp}</title>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>

${messageSendSuccess}
<div style="margin-left: 600px">
<form action="/ServletController" method="post" charset="UTF-8">
    ${enterEmail}:     <Input type="Text" name="email" value=""/>${requestScope.map.email}
    <br>
    <br>
    ${enterLogin}:     <Input type="Text" name="login" value=""/>${requestScope.map.login}
    <br>
    <br>
    ${enterPassword}:  <input type="password" name ="password"  value=""/>${requestScope.map.password}
    <br>
    <br>
    ${repeatPassword}: <input type="password" name ="passwordRepeat" value=""/>${requestScope.map.passwordRepeat}
    <br>
    <br>
    <Input type="submit" value="Зарегистрироваться"/>
    <input type="hidden" name="command" value="signup" />
    <br>
    <br>
</form>
</div>
</body>
</html>
