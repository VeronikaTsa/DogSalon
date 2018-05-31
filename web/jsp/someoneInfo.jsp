<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <style>
        .user-info p {
            margin-top: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<jsp:include page="head.jsp"/>
<jsp:include page="logo.jsp"/>
<div class="user-info-wrap">
    <form>
        <div class="user-info">
            <p>login: <c:out value="${sessionScope.someone.login}"/></p>
            <p>email: <c:out value="${sessionScope.someone.email}"/></p>
            <c:if test="${not empty sessionScope.someone.userContent}">
                <c:if test="${not empty sessionScope.someone.userContent.firstName}">
                    <p>first name: <c:out value="${sessionScope.someone.userContent.firstName}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.lastName}">
                    <p>last name: <c:out value="${sessionScope.someone.userContent.lastName}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.telephone}">
                    <p>telephone: <c:out value="${sessionScope.someone.userContent.telephone}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.birthday}">
                    <p>birthday: <c:out value="${sessionScope.someone.userContent.birthday}"/></p>
                </c:if>
                <c:if test="${not empty sessionScope.someone.userContent.sex}">
                    <p>sex: <c:out value="${sessionScope.someone.userContent.sex}"/></p>
                </c:if>
            </c:if>
        </div>
    </form>
</div>
</body>
</html>
