<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <fmt:message bundle="${local}" key="message.edit" var="edit"/>

    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
    <title>${sessionScope.user.login}</title>
    <style>
        .user-info {
            text-align: center;
        }
        .user-info p {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<div class="user-info-wrap">
    <div class="user-info">
        <c:if test="${not empty sessionScope.user}">
            <a
                    style="border-bottom: 1px solid #222; text-decoration:none; color:#222;"
                    href="<c:url value="/jsp/user/userEdit.jsp"/>">
                ${edit}
            </a>
        </c:if>
        <p>${email}: <c:out value="${sessionScope.user.email}"/></p>
        <p> ${login}: <c:out value="${sessionScope.user.login}"/></p>
        <c:if test="${not empty sessionScope.user.userContent}">
            <c:if test="${not empty sessionScope.user.userContent.firstName}">
                <p>${firstName}: <c:out value="${sessionScope.user.userContent.firstName}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.lastName}">
                <p>${lastName}: <c:out value="${sessionScope.user.userContent.lastName}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.telephone}">
                <p>${telephone}: <c:out value="${sessionScope.user.userContent.telephone}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.birthday}">
                <p>${birthday}: <c:out value="${sessionScope.user.userContent.birthday}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.sex}">
                <p>${sex}: <c:out value="${sessionScope.user.userContent.sex}"/></p>
            </c:if>
    </c:if>
    </div>
</div>

</body>
</html>
