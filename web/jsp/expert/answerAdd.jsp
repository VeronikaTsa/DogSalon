<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 26.04.2018
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
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
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.addAnswer" var="addAnswer"/>
    <c:choose>
        <c:when test="${not empty param.questionId}">
            <c:set var="questionAuthor" value="${param.questionAuthor}"/>
            <c:set var="questionCreateTime}" value="${param.questionCreateTime}}"/>
            <c:set var="questionContent" value="${param.questionContent}"/>
            <c:set var="questionId" value="${param.questionId}"/>
            <c:set var="answerContent" value="${param.answerContent}"/>
        </c:when>
        <c:otherwise>
            <c:set var="questionAuthor" value="${requestScope.questionAuthor}"/>
            <c:set var="questionCreateTime" value="${requestScope.questionCreateTime}}"/>
            <c:set var="questionContent" value="${requestScope.questionContent}"/>
            <c:set var="questionId" value="${requestScope.questionId}"/>
            <c:set var="answerContent" value="${requestScope.answerContent}"/>
        </c:otherwise>
    </c:choose>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="/css/cc.css">
    <title>${addAnswer}</title>
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
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<a href="/ServletController?command=userInfo&userLogin=${param.questionAuthor}"
   class="user">
    <c:out value="${param.questionAuthor}"/>
</a>
<br>
Вопрос:
<br>
${param.questionCreateTime}
<br>
<p>
<c:out value="${param.questionContent}"/>

<br>
<br>

<form action="/ServletController" method="post">
    <Input type="Text" name="answer" value="${answerContent}" width="100px" height="50px"/>
    <input type="hidden" name="questionId" value="${questionId}" />
    <Input type="submit" class="submit-link" value="${addAnswer}"/>
    <input type="hidden" name="command" value="answerAdd" />
</form>

<br>
${requestScope.answerAddSuccess}
</body>
</html>
