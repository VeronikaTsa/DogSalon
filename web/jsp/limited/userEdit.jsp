<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 29.05.2018
  Time: 0:56
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
    <fmt:message bundle="${local}" key="message.oldPassword" var="oldPassword"/>
    <fmt:message bundle="${local}" key="message.newPassword" var="newPassword"/>
    <fmt:message bundle="${local}" key="message.chooseSex" var="chooseSex"/>
    <fmt:message bundle="${local}" key="message.female" var="female"/>
    <fmt:message bundle="${local}" key="message.male" var="male"/>
    <fmt:message bundle="${local}" key="message.edit" var="edit"/>

    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
    <title>Title</title>
    <style>
        .user-info {
            text-align: center;
        }
        .user-info p {
            margin-top: 20px;
        }
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
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<div class="user-info-wrap">
    <div class="user-info">
        <form action="<c:url value="/ServletController"/>" method="post">
            <input type="hidden" name="command" value="userUpdate">
            <input class="submit-link" type="submit" value="${edit}">
            <p>${email}: <Input type="Text" name="emailToEdit" value="<c:out value="${sessionScope.user.email}"/>"/></p>
            <p>${login}: <Input type="Text" name="loginToEdit" value="<c:out value="${sessionScope.user.login}"/>"/></p>
            <p>${firstName}: <Input type="Text" name="firstNameToEdit" value="<c:out value="${sessionScope.user.userContent.firstName}"/>"/></p>
            <p>${lastName}: <Input type="Text" name="lastNameToEdit" value="<c:out value="${sessionScope.user.userContent.lastName}"/>"/></p>
            <p>${telephone}: <Input type="Text" name="telephoneToEdit" value="<c:out value="${sessionScope.user.userContent.telephone}"/>"/></p>
            <p>${birthday}: <Input type="date" name="birthdayToEdit" value="<c:out value="${sessionScope.user.userContent.birthday}"/>"/></p>
            <p>${sex}:
            <select name="sexToEdit">
                <option selected value="none">${chooseSex}</option>
                <c:if test="${empty sessionScope.user.userContent.sex}">
                    <option value="male">${male}</option>
                    <option value="female">${female}</option>
                </c:if>
                <c:if test="${sessionScope.user.userContent.sex.getValue().equals('male')}">
                    <option selected value="male">${male}</option>
                    <option value="female">${female}</option>
                </c:if>
                <c:if test="${sessionScope.user.userContent.sex.getValue().equals('female')}">
                    <option value="male">${male}</option>
                    <option selected value="female">${female}</option>
                </c:if>
            </select>
            </p>
            <p>${oldPassword}: <Input type="password" name="oldPassword" value="<c:out value=""/>"/></p>
            <p>${newPassword}: <Input type="password" name="newPassword" value="<c:out value=""/>"/></p>

        </form>
    </div>
</div>
</body>
</html>
