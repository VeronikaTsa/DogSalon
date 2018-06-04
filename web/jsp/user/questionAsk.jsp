<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 22.04.2018
  Time: 19:47
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
    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.ask" var="ask" />
    <fmt:message bundle="${local}" key="message.askExpert" var="askExpert" />

    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
    <title>${askExpert}</title>
</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<form action="<c:url value="/ServletController"/>" method="post" charset="UTF-8">
    <Input type="Text" name="question" value="" width="100px" height="50px"/>
    <Input type="submit" class="submit-link" value="${ask}"/>
    <input type="hidden" name="command" value="questionAsk" />
</form>

<br>
${requestScope.questionAskSuccess}
<br>
</body>
</html>
