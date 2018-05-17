<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

</head>
<body>
<c:set var="list" value="${requestScope.serviceList}"/>

<c:if test="${not empty list}">
    <c:forEach items="${list}" var="element">
        <div class="post">
            <!-- PHOTO POST -->
            <div class="image_post">
                <a href="/jsp/serviceInfo.jsp?id=${element.id}&name=${element.name}&content=${element.content}&price=${element.price}&picture=${element.picture}">
                    <img src="/resource/img/service/${element.picture}">
                </a>
            </div>

                <div class="caption_post">
                    <div class="index-caption">
                        <p>
                        <span>
                            <a href="/jsp/serviceInfo.jsp?id=${element.id}&name=${element.name}&content=${element.content}&price=${element.price}&picture=${element.picture}">${element.name}<br></a>
                        </span>
                        </p>
                    </div>
                    <p class="date"> ${element.price}</p>
                    <c:if test="${sessionScope.user.role.getValue().equals('administrator')}">
                        <div class="index-caption">
                            <p>
                            <span>
                                <a href="/ServletController?id=${element.id}&command=serviceDelete">Удалить<br></a>
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
