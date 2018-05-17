<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 27.04.2018
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/css/cc.css">
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.questionNotAnswered" var="questionNotAnswered"/>
    <fmt:message bundle="${local}" key="message.expertPanel" var="expertPanel"/>
    <title>${expertPanel}</title>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>

<form action="/ServletController" method="post">
    <Input type="submit" value="${questionNotAnswered}"/>
    <input type="hidden" name="command" value="question" />
</form>
</body>
</html>
