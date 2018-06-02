<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 14.05.2018
  Time: 7:41
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
    <fmt:message bundle="${local}" key="message.delete" var="delete"/>
    <fmt:message bundle="${local}" key="message.addAnswer" var="addAnswer"/>

    <title>Title</title>
</head>
<body>
<c:set var="list" value="${requestScope.questionNotAnsweredList}"/>
<c:if test="${not empty list}">
    <div style="margin-left: 600px">
        <c:forEach items="${list}" var="element">
            <br><br>
            <a href="/ServletController?command=userInfo&userLogin=${element.userLogin}"
               class="user">
                <c:out value="${element.userLogin}"/>
            </a>
            <br>
            ${element.createTime}
            <br>
            <p style="font-size: 20px">
                <c:out value="${element.content}"/>
            </p>

            <br>
            <c:if test="${sessionScope.user.role.getValue().equals('expert')}">
                <form action="/ServletController" method="post">
                    <input type="hidden" name="id" value="${element.id}"/>
                    <Input type="submit" value="${delete}"/>
                    <input type="hidden" name="command" value="questionDelete" />
                </form>
                <a class="link" href="<c:url value="/jsp/expert/answerAdd.jsp?questionContent=${element.content}&questionCreateTime=${element.createTime}&questionAuthor=${element.userLogin}&questionId=${element.id}"/>">${addAnswer}</a>
                <br>
            </c:if>
            <br>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
