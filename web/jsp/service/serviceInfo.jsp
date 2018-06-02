<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 13.05.2018
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}"/>
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.delete" var="delete"/>
    <fmt:message bundle="${local}" key="message.edit" var="edit"/>

    <title>${param.name}</title>
    <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">

    <c:choose>
        <c:when test="${not empty param.name}">
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

</head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>

<div id="wrapper">
    <jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
    <div class="permalink-post">
        <div class="permalink-image_post">
            <div class="permalink-caption_post">
                <div class="permalink-caption">
                    <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
                        <div class="caption_post">
                            <p>
                               <span>
                                   <a
                                           style="border-bottom: 1px solid #222;"
                                           href="<c:url value="/ServletController?id=${id}&command=serviceDelete"/>">
                                       ${delete}
                                   </a> /
                                   <a
                                           style="border-bottom: 1px solid #222;"
                                           href="<c:url value="/jsp/admin/serviceEdit.jsp?id=${id}&name=${name}&content=${content}&price=${price}&picture=${picture}"/>">
                                       ${edit}
                                   </a>
                               </span>
                            </p>
                        </div>
                    </c:if>
                </div>
                <div class="permalink-caption">
                    <p style="font-size: 30px">
                        <c:out value="${name}"/>
                    </p>
                </div>
            </div>
            <img src="/resource/img/service/${picture}">
        </div>
        <div class="permalink-caption_post">
            <p class="date" style="font-size: 20px">
                <c:out value="${price}"/>
            </p>
            <div class="permalink-caption">
                <p style="font-size: 25px">
                    <c:out value="${content}"/>
                </p>
            </div>
        </div>
    </div>
    <div class="clear"></div>
    <section class="categories"></section>
    <section class="pagination"></section><div class="clear"></div></div>
</body>
</html>
