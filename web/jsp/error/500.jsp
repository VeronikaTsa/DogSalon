<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 09.04.2018
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="ru">
<head>
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.startAgain" var="startAgain"/>
    <style type="text/css">
        body {color:#222;
            font-size:13px;
            font-family: sans-serif;

            background:#fff url(/resource/img/error/500.jpg) no-repeat;

            background-size: cover;}
        h1 {font-size:300%;font-family:'Trebuchet MS', Verdana, sans-serif; color:#000}
        #page {font-size:122%;width:720px; margin:144px auto 0 auto;text-align:left;line-height:1.2;}
    </style>
    <style>
        a.beginning {
            color:#222; /* Отменяем подчеркивание у ссылки */
            display:block;
            font-size:18px;
        }
        a.beginning:hover {
            color:#d64431; /* Цвет ссылок при наведении на них курсора мыши */
        }
    </style>
    <title>Error 500</title>
</head>

<body>
<div id="page" >
    <div id="message" style="float: left;">

        <h1>500</h1>
        <p> Упс, проблемы на сервере. Мы уже работаем над этим.
            <br>
            <br>
            — Да что они там, заснули?!
            <br>— Винни, ведь это же твой дом.
            <br>— Да? А, ну да.
        </p>
        <p><a href="<c:url value="/index.jsp"/>" class="beginning">${startAgain}</a></p>
    </div>
</div>

</body></html>