<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 11.05.2018
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.delete" var="delete"/>
</head>
<body>
<c:set var="list" value="${requestScope.serviceList}"/>

<c:if test="${not empty list}">
    <c:forEach items="${list}" var="element">
        <div class="post">
            <div class="image_post">
                <a href="<c:url value="/jsp/service/serviceInfo.jsp?id=${element.id}&name=${element.name}&content=${element.content}&price=${element.price}&picture=${element.picture}"/>">
                    <img src="/resource/img/service/${element.picture}">
                </a>
            </div>
                <div class="caption_post">
                    <div class="index-caption">
                        <p>
                            <span>
                                <a href="<c:url value="/jsp/service/serviceInfo.jsp?id=${element.id}&name=${element.name}&content=${element.content}&price=${element.price}&picture=${element.picture}"/>">${element.name}<br></a>
                            </span>
                        </p>
                    </div>
                    <p class="date"> ${element.price}</p>
                    <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
                        <div class="index-caption">
                            <p>
                            <span>
                                <a href="<c:url value="/ServletController?id=${element.id}&command=serviceDelete"/>">${delete}<br></a>
                            </span>
                            </p>
                        </div>
                    </c:if>
                </div>

        </div>

    </c:forEach>
</c:if>
</body>
</html>
