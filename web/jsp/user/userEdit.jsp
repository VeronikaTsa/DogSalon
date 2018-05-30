<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>
<div class="user-info-wrap">
    <div class="user-info">
        <form action="/ServletController" method="post">
            <input type="hidden" name="command" value="userUpdate">
            <input class="submit-link" type="submit" value="Изменить">
            <p>email: <Input type="Text" name="emailToEdit" value="<c:out value="${sessionScope.user.email}"/>"/></p>
            <p>login: <Input type="Text" name="loginToEdit" value="<c:out value="${sessionScope.user.login}"/>"/></p>
            <p>picture:  <c:out value="${sessionScope.user.userContent.picture}"/></p>
            <p>first name: <Input type="Text" name="firstNameToEdit" value="<c:out value="${sessionScope.user.userContent.firstName}"/>"/></p>
            <p>last name: <Input type="Text" name="lastNameToEdit" value="<c:out value="${sessionScope.user.userContent.lastName}"/>"/></p>
            <p>telephone: <Input type="Text" name="telephoneToEdit" value="<c:out value="${sessionScope.user.userContent.telephone}"/>"/></p>
            <p>birthday: <Input type="date" name="birthdayToEdit" value="<c:out value="${sessionScope.user.userContent.birthday}"/>"/></p>
            <p>
            <select name="sexToEdit">
                <option selected value="none">Выберите пол</option>
                <c:if test="${empty sessionScope.user.userContent.sex}">
                    <option value="male">Мужской</option>
                    <option value="female">Женский</option>
                </c:if>
                <c:if test="${sessionScope.user.userContent.sex.getValue().equals('male')}">
                    <option selected value="male">Мужской</option>
                    <option value="female">Женский</option>
                </c:if>
                <c:if test="${sessionScope.user.userContent.sex.getValue().equals('female')}">
                    <option value="male">Мужской</option>
                    <option selected value="female">Женский</option>
                </c:if>
            </select>
            </p>
            <p>old password: <Input type="password" name="oldPassword" value="<c:out value=""/>"/></p>
            <p>new password: <Input type="password" name="newPassword" value="<c:out value=""/>"/></p>

        </form>
    </div>
</div>
</body>
</html>
