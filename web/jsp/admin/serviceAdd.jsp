<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 11.05.2018
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/cc.css">
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.addService" var="addService"/>
    <fmt:message bundle="${local}" key="message.enterServiceName" var="enterServiceName"/>
    <fmt:message bundle="${local}" key="message.enterServicePrice" var="enterServicePrice"/>
    <fmt:message bundle="${local}" key="message.enterServiceDescription" var="enterServiceDescription"/>


    <title>${addService}</title>
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
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<form action="/ServletController" method="post" charset="UTF-8">
    ${enterServiceName}: <Input type="Text" name="name" value=""/>${requestScope.map.name}
    <br>
    <br>
    ${enterServiceDescription}: <Input type="Text" name="content" value=""/>${requestScope.map.content}
    <br>
    <br>
    ${enterServicePrice}: <input type="number" class="no-spinners" name ="price"  value=""/>${requestScope.map.price}
    <br>
    <br>


    <br>
    <Input type="submit" class="submit-link" value="${addService}"/>
    <input type="hidden" name="command" value="serviceadd" />
    <br>
    <br>
</form>
</body>
</html>
