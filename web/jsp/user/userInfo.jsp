<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>
<div class="user-info-wrap">
    <div class="user-info">
        <c:if test="${not empty sessionScope.user}">
            <a
                    style="border-bottom: 1px solid #222; text-decoration:none; color:#222;"
                    href="<c:url value="/jsp/user/userEdit.jsp"/>">
                Изменить
            </a>
        </c:if>
        <p>email: <c:out value="${sessionScope.user.email}"/></p>
        <p> login: <c:out value="${sessionScope.user.login}"/></p>
        <p>picture:  <c:out value="${sessionScope.user.userContent.picture}"/></p>
        <c:if test="${not empty sessionScope.user.userContent}">
            <c:if test="${not empty sessionScope.user.userContent.firstName}">
                <p>first name: <c:out value="${sessionScope.user.userContent.firstName}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.lastName}">
                <p>last name: <c:out value="${sessionScope.user.userContent.lastName}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.telephone}">
                <p>telephone: <c:out value="${sessionScope.user.userContent.telephone}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.birthday}">
                <p>birthday: <c:out value="${sessionScope.user.userContent.birthday}"/></p>
            </c:if>
            <c:if test="${not empty sessionScope.user.userContent.sex}">
                <p>sex: <c:out value="${sessionScope.user.userContent.sex}"/></p>
            </c:if>
    </c:if>
    </div>
</div>

</body>
</html>
