<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.login" var="login"/>
    <fmt:message bundle="${local}" key="message.email" var="email"/>
    <fmt:message bundle="${local}" key="message.firstName" var="firstName"/>
    <fmt:message bundle="${local}" key="message.lastName" var="lastName"/>
    <fmt:message bundle="${local}" key="message.telephone" var="telephone"/>
    <fmt:message bundle="${local}" key="message.birthday" var="birthday"/>
    <fmt:message bundle="${local}" key="message.sex" var="sex"/>
    <title>${sessionScope.someone.login}</title>
    <link rel="stylesheet" href="/css/cc.css">
    <style>
        .user-info p {
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:include page="../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../WEB-INF/jspf/logo.jsp"/>
<div class="user-info-wrap">
    <form>
        <div class="user-info">
            <p>${login}: <c:out value="${sessionScope.someone.login}"/></p>
            <p>${email}: <c:out value="${sessionScope.someone.email}"/></p>
            <c:if test="${not empty sessionScope.someone.userContent}">
                <c:if test="${not empty sessionScope.someone.userContent.firstName}">
                    <p>${firstName}: <c:out value="${sessionScope.someone.userContent.firstName}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.lastName}">
                    <p>${lastName}: <c:out value="${sessionScope.someone.userContent.lastName}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.telephone}">
                    <p>${telephone}: <c:out value="${sessionScope.someone.userContent.telephone}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.birthday}">
                    <p>${birthday}: <c:out value="${sessionScope.someone.userContent.birthday}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.sex}">
                    <p>${sex}: <c:out value="${sessionScope.someone.userContent.sex}"/></p>
                </c:if>
            </c:if>
        </div>
    </form>
</div>
</body>
</html>
