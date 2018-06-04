<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 09.04.2018
  Time: 10:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            background:#fff url(/resource/img/error/404.jpg) no-repeat;
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
    <title>Error 404 – Page not found</title>
</head>

<body>
<div id="page">
    <div id="message">
        <h1>404</h1>
        <p>...каждый вечер Ежик с Медвежонком собирались то у Ежика, то у Медвежонка и о чем-нибудь говорили. Вот и сегодня Ежик сказал Медвежонку:
            <br>
            <br>
            - Как все-таки хорошо, что мы друг у друга есть!
            <br>
            Медвежонок кивнул.
            <br>
            - Ты только представь себе: меня нет, ты сидишь один и поговорить не с кем.
            <br>
            - А ты где?
            <br>
            - А меня нет.
            <br>
            - Так не бывает, - сказал Медвежонок.
            <br>
            - Я тоже так думаю, - сказал Ежик. - Но вдруг вот - меня совсем нет. Ты один. <br> Ну что ты будешь делать?
            <br>
            - Я побегу, обшарю весь лес и тебя найду!
            <br>
            - Ты все уже обшарил, - сказал Ежик. - И не нашел.
            <br>
            - Побегу в соседний лес!
            <br>
            - И там нет.
            <br>
            - Переверну все вверх дном, и ты отыщешься!
            <br>
            - Нет меня. Нигде нет.
            <br>
            - Тогда, тогда... Тогда я выбегу в поле, - сказал Медвежонок. - И закричу: "Е-е-е-жи-и-и-к!", и ты услышишь и закричишь: "Медвежоно-о-о-к!.." Вот.
            <br>
            - Нет, - сказал Ежик. - Меня ни капельки нет. Понимаешь?
            <br>
            - Что ты ко мне пристал? - рассердился Медвежонок. - Если тебя нет, то и меня нет. Понял?
            </p>
        <p><a href="<c:url value="/index.jsp"/>" class="beginning">${startAgain}</a></p>
    </div>
</div>

</body></html>