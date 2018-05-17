<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 13.05.2018
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test = "${empty sessionScope.language}">
        <c:set var="language" value='en_US' scope="session"/>
    </c:if>
    <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
    <fmt:setLocale value="${language}" />
    <fmt:setBundle basename="text" var="local"/>
    <fmt:message bundle="${local}" key="message.edit" var="edit" />
    <link rel="stylesheet" href="/css/cc.css">
    <title></title>
</head>
<body>
<jsp:include page="head.jsp"/>
<div id="wrapper">
    <!-- LOGO FIGURE -->
    <jsp:include page="logo.jsp"/>

    <div class="permalink-post">
        <div class="permalink-image_post">
            <div class="permalink-caption_post">
                <div class="permalink-caption">
                    <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
                        <div class="caption_post">
                            <p>
                                    <span>
                                        <a style="border-bottom: 1px solid #222;" href="/jsp/serviceEdit.jsp?id=${element.id}&name=${element.name}&content=${element.content}&price=${element.price}&picture=${element.picture}">Изменить</a>
                                    </span>
                            </p>
                        </div>
                    </c:if>
                </div>
                <div class="permalink-caption">
                    <p style="font-size: 30px">
                        <Input type="Text" name="codeToCompare" value="${param.name}"/>
                    </p>
                </div>
            </div>
            <img src="/resource/img/service/${param.picture}"></div>
        <div class="permalink-caption_post">
            <Input type="Text" name="codeToCompare" value="${param.price}"/>
            <div class="permalink-caption">
                <p style="font-size: 25px">
                    <Input type="Text" name="codeToCompare" value="${param.content}"/>
                </p>
            </div>
        </div>
    </div>

    <div class="clear"></div>
    <section class="categories"></section>
    <section class="pagination"></section><div class="clear"></div></div>
</body>
</html>
