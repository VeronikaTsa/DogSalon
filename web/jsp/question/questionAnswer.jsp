<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 27.04.2018
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.questions" var="questions" />
    <fmt:message bundle="${local}" key="message.askExpert" var="askExpert" />

    <title>${questions}</title>
    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
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
<c:set var="user" value="${sessionScope.user}"/>

<c:if test="${not empty user}">
    <c:if test="${sessionScope.user.role.getValue().equals('user')}">
        <Input style="margin-left: 700px" type="button" class="submit-link" onclick="location.href='/jsp/user/questionAsk.jsp'" value="${askExpert}" /><br><br>
    </c:if>

</c:if>


<jsp:include page="/ServletController" flush="true">
    <jsp:param name="command" value="questionAnswer" />
</jsp:include>

</body>
</html>
