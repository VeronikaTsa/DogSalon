<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 13.05.2018
  Time: 15:02
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
    <fmt:message bundle="${local}" key="message.signupExpert" var="signUpExpert"/>
    <fmt:message bundle="${local}" key="message.signUp" var="signUp"/>
    <fmt:message bundle="${local}" key="message.enterEmail" var="enterEmail"/>
    <fmt:message bundle="${local}" key="message.enterLogin" var="enterLogin"/>
    <title>${signUpExpert}</title>
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

    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>

<div style="margin-left: 600px">
<form action="<c:url value="/ServletController"/>" method="post" charset="UTF-8">
    ${enterEmail} <Input type="Text" name="email" value=""/>
    <br>
    <br>
    ${enterLogin} <Input type="Text" name="login" value=""/>
    <br>
    <br>

    <Input type="submit" class="submit-link" value="${signUp}"/>
    <input type="hidden" name="command" value="signupexpert" />
    <br>
    <br>
</form>
</div>

</body>
</html>
