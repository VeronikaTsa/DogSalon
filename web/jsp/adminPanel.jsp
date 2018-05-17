<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 12.04.2018
  Time: 11:05
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
    <fmt:message bundle="${local}" key="message.signupExpert" var="signupExpert"/>
    <fmt:message bundle="${local}" key="message.addService" var="addService"/>
    <fmt:message bundle="${local}" key="message.adminPanel" var="adminPanel"/>
    <title>${adminPanel}</title>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>

<Input type="button" onclick="location.href='/jsp/signUpExpert.jsp'" value="${signupExpert}" />${signUpExpertSuccess}

<br>
<br>

<Input type="button" onclick="location.href='/jsp/serviceAdd.jsp'" value="${addService}" />${serviceAddSuccess}

</body>
</html>
