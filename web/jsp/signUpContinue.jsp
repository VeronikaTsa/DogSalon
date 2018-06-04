<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 10.04.2018
  Time: 17:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <fmt:message bundle="${local}" key="message.signUpContinue" var="signUpContinue"/>
    <fmt:message bundle="${local}" key="message.signUp" var="signUp"/>
    <fmt:message bundle="${local}" key="message.send" var="send"/>

    <title>${signUp}</title>
</head>
<body>
<jsp:include page="../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../WEB-INF/jspf/logo.jsp"/>
<div style="margin-left: 300px">
<form action="<c:url value="/ServletController"/>" method="post" charset="UTF-8">
    ${signUpContinue} <Input type="Text" name="codeToCompare" value=""/>
    <Input type="submit" class="submit-link" value="${send}"/>
    <input type="hidden" name="command" value="signupContinue" />
    <br>
    <br>
    <br>
</form>
</div>
</body>
</html>
