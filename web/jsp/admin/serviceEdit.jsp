<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 13.05.2018
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <c:choose>
        <c:when test="${not empty param.id}">
            <c:set value="${param.picture}" var="picture"/>
            <c:set var="name" value="${param.name}"/>
            <c:set var="content" value="${param.content}"/>
            <c:set var="price" value="${param.price}"/>
            <c:set var="id" value="${param.id}"/>
        </c:when>
        <c:otherwise>
            <c:set value="${requestScope.picture}" var="picture"/>
            <c:set var="name" value="${requestScope.name}"/>
            <c:set var="content" value="${requestScope.content}"/>
            <c:set var="price" value="${requestScope.price}"/>
            <c:set var="id" value="${requestScope.id}"/>
        </c:otherwise>
    </c:choose>
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.edit" var="edit" />
    <fmt:message bundle="${local}" key="message.chooseFile" var="chooseFile" />


    <link rel="stylesheet" href="/css/cc.css">
    <style>
        .no-spinners {
            -moz-appearance:textfield;
        }
        .no-spinners::-webkit-inner-spin-button,
        .no-spinners::-webkit-outer-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
    </style>
    <title></title>
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<div id="wrapper">
    <jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
    <form action="/ServletUploader" enctype = "multipart/form-data" method="post">
    <div class="permalink-post">
        <div class="permalink-image_post">
            <div class="permalink-caption_post">
                <div class="permalink-caption">
                    <p>
                        <Input type="Text" name="nameToEdit" value="<c:out value="${name}"/>"/><div class="error-message">${requestScope.errorMap.serviceName}</div>
                    </p>
                </div>
            </div>
            <img src="/resource/img/service/${picture}">
        </div>
        <div class="permalink-caption_post">
            <input type="number" class="no-spinners" name="priceToEdit" value="<c:out value="${price}"/>"/><div class="error-message">${requestScope.errorMap.servicePrice}</div>
            <div class="permalink-caption">
                <p>
                    <Input type="Text" name="contentToEdit" value="${content}"/><div class="error-message">${requestScope.errorMap.serviceContent}</div>
                </p>
            </div>
        </div>
    </div>
        <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
            ${chooseFile}:<INPUT type="file" name="picture">${requestScope.map.picture}
            <Input type="submit" value="${edit}">
            <input type="hidden" name="command" value="serviceUpdate" />
            <input type="hidden" name="serviceId" value="${id}" />
            <input type="hidden" name="pictureName" value="${picture}" />
        </c:if>
    </form>

    <div class="clear"></div>
    <section class="categories"></section>
    <section class="pagination"></section><div class="clear"></div></div>
</body>
</html>
