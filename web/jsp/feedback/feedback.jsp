<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 22.04.2018
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/css/cc.css">
    <style>
        a.user {
            color:#222;
            text-decoration: none; /* Отменяем подчеркивание у ссылки */
            display:block;
            font-size:18px;
        }
        a.user:hover {
            color:#d64431; /* Цвет ссылок при наведении на них курсора мыши */
        }

    </style>

    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.feedbacks" var="feedbacks"/>
    <title>${feedbacks}</title>
</head>
<body>


<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>


<c:set var="user" value="${sessionScope.user}"/>
<c:if test="${not empty user}">
    <c:if test="${sessionScope.user.role.getValue().equals('user')}">
        <Input style="margin-left: 700px" type="button" onclick="location.href='/jsp/feedback/feedbackAdd.jsp'" value="Добавить отзыв" />
        <br>
    </c:if>
</c:if>

<jsp:include page="/ServletController" flush="true">
    <jsp:param name="command" value="feedback" />
</jsp:include>

<br>
<br>
<br>
<br>
</body>
</html>
