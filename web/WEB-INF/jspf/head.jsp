<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 11.05.2018
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
    <title>Title</title>
    <c:choose>
        <c:when test="${(sessionScope.user.role.getValue().equals('administrator')) or
        (sessionScope.user.role.getValue().equals('expert'))}">
                <style>
                    #menu ul li 			         { width:14.28%; }
                </style>
        </c:when>
        <c:otherwise>
            <style>
                #menu ul li 			         { width:16.6%; }
            </style>
        </c:otherwise>
    </c:choose>
    <style>
        #button-link{
            background:none;
            border:0;
            color:#666;
            text-decoration:underline;
        }

        #button-link:hover{
            background:none;
            border:0;
            color:#666;
            text-decoration:none;
            cursor:pointer;
            cursor:hand;
        }
    </style>
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.homeHead" var="home" />
    <fmt:message bundle="${local}" key="message.feedbacksHead" var="feedbacks" />
    <fmt:message bundle="${local}" key="message.questionsHead" var="questions" />
    <fmt:message bundle="${local}" key="message.logInHead" var="login" />
    <fmt:message bundle="${local}" key="message.logOutHead" var="logout" />
    <fmt:message bundle="${local}" key="message.signUpHead" var="signup" />
    <fmt:message bundle="${local}" key="message.meHead" var="me" />
    <fmt:message bundle="${local}" key="message.adminPanelHead" var="adminPanel" />
    <fmt:message bundle="${local}" key="message.expertPanelHead" var="expertPanel" />
</head>
<body>
<nav id="menu">
    <ul>
        <li>
            <a href="<c:url value="/index.jsp"/>">
                ${home}
            </a>
        </li>
        <li>
            <a href="<c:url value="/jsp/feedback/feedback.jsp"/>">${feedbacks}</a>
        </li>
        <li>
            <a href="<c:url value="/jsp/question/questionAnswer.jsp"/>">${questions}</a>

        </li>
        <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
            <li>
                <a href="<c:url value="/jsp/admin/adminPanel.jsp"/>">${adminPanel}</a>
            </li>
        </c:if>
        <c:if test="${sessionScope.user.role.getValue().equals('expert')}">
            <li>
                <a href="<c:url value="/jsp/expert/expertPanel.jsp"/>">${expertPanel}</a>
            </li>
        </c:if>

        <c:choose>
            <c:when test="${not empty user}">
                <li>
                    <a href="<c:url value="/jsp/limited/userInfo.jsp"/>"> ${me} </a>
                </li>
                <li>
                    <a href="<c:url value="/ServletController?command=logout"/>">${logout}</a>
                </li>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="<c:url value="/jsp/logIn.jsp"/>">${login}</a>
                </li>
                <li>
                    <a href="<c:url value="/jsp/signUp.jsp"/>">${signup}</a>
                </li>
            </c:otherwise>
        </c:choose>
        </li>
        <li>
            <c:choose>
                <c:when test="${sessionScope.language.equals('ru_RU')}">

                        <a  href="${pageContext.request.requestURL}?language=en_US&<ctg:decode>${pageContext.request.queryString}</ctg:decode>"> ENGLISH</a>

                </c:when>
                <c:otherwise>

                    <a  href="${pageContext.request.requestURL}?language=ru_RU&<ctg:decode>${pageContext.request.queryString}</ctg:decode>">РУССКИЙ</a>
                </c:otherwise>
            </c:choose>
        </li>
    </ul>
</nav>
</body>
</html>