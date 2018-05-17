<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 12.05.2018
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <title>Title</title>
    <link rel="stylesheet" href="/css/cc.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
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


<c:set var="list" value="${requestScope.feedbackList}"/>

${requestScope.feedbackDelSuccess}
<br><br>

<div class="feedbacks-wrap"></div>
<c:if test="${not empty list}">
    <c:forEach items="${list}" var="element">
        <div class="feedback">
            <div class="feedback-top">
                <a class="feedback__author" href="/ServletController?command=userInfo&userLogin=${element.userLogin}"
                   class="user"><c:out value="${element.userLogin}"/></a>
                <span class="feedback__date">${element.createTime}</span>
            </div>
            <div class="feedback__content">
                <c:out value="${element.content}"/>
            </div>
            <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
                <a class="feedback__btn-delete" href="/ServletController?id=${element.id}&command=feedbackDelete">
                    <i class="far fa-trash-alt"></i>
                </a>
            </c:if>
        </div>
    </c:forEach>
</c:if>
</body>
</html>
