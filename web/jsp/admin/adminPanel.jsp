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

<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<div style="margin-left: 650px">
<Input type="button" class="submit-link" onclick="location.href='/jsp/admin/signUpExpert.jsp'" value="${signupExpert}" />${signUpExpertSuccess}
<br>
<br>
<Input type="button" class="submit-link" onclick="location.href='/jsp/admin/serviceAdd.jsp'" value="${addService}" />${serviceAddSuccess}
</div>
</body>
</html>
