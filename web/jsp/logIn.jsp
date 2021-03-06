<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 10.04.2018
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        .submit-link {
            background:none!important;
            color:inherit;
            border:none;
            font: inherit;
            padding:0!important;
            border-bottom: 1px solid #222;
            cursor: pointer;
            text-decoration:none;
        }
    </style>
    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.enterEmail" var="enterEmail"/>
    <fmt:message bundle="${local}" key="message.enterPassword" var="enterPassword"/>
    <fmt:message bundle="${local}" key="message.logIn" var="logIn"/>
    <title>${logIn}</title>
</head>
<body>
<jsp:include page="../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../WEB-INF/jspf/logo.jsp"/>
<div style="margin-left: 600px">
<form action="<c:url value="/ServletController"/>" method="post" charset="UTF-8">
    ${enterEmail}:          <Input type="Text" name="email" value="" />
    <br>
    <br>
    ${enterPassword}: <input type="password" name ="password" value="" />
    <br>
    <br>
    <Input type="submit" class="submit-link" value="${logIn}"/>
    <input type="hidden" name="command" value="login" />
    <br>
    <br>
</form>
</div>
</body>
</html>
