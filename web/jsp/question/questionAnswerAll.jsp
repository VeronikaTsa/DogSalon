<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 12.05.2018
  Time: 18:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Title</title>
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
</head>
<body>

<c:set var="list" value="${requestScope.questionAnswerList}"/>

<c:if test="${not empty list}">
    <c:forEach items="${list}" var="element">
<div style="margin-left: 650px">
        <a class="feedback__author" href="/ServletController?command=userInfo&userLogin=${element.questionUserLogin}"
           class="user">
                <c:out value="${element.questionUserLogin}"/>
        </a>

        ${element.questionCreateTime}
        <br>
        <c:out value="${element.questionContent}"/>
        <br>
        <br>
        <br>
        <a class="feedback__author" href="/ServletController?command=userInfo&userLogin=${element.answerUserLogin}"
           class="user">
                <c:out value="${element.answerUserLogin}"/>
        </a>
        ${element.answerCreateTime}
        <br>
        <c:out value="${element.answerContent}"/>
        <br>
        <br>
    <c:if test="${sessionScope.user.role.getValue().equals('expert')}">
    <a
            style="border-bottom: 1px solid #222; text-decoration:none; color:#222;"
            href="<c:url value="/ServletController?id=${element.questionAnswerId}&command=answerDelete"/>">
        Удалить
    </a> /
        <a
                style="border-bottom: 1px solid #222; text-decoration:none; color:#222;"
                href="<c:url value="/jsp/expert/answerAdd.jsp?questionContent=${element.questionContent}&questionCreateTime=${element.questionCreateTime}&questionAuthor=${element.questionUserLogin}&questionId=${element.questionAnswerId}&answerContent=${element.answerContent}"/>">
            Изменить
        </a>
    </c:if>

        <br>

        <br><br>
</div>
        <hr>
    </c:forEach>
</c:if>


</body>
</html>
