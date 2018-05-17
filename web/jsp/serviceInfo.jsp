<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 13.05.2018
  Time: 16:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${param.name}</title>
    <link rel="stylesheet" href="/css/cc.css">

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
                                        <a style="border-bottom: 1px solid #222;" href="/ServletController?id=${param.id}&command=serviceDelete">Удалить</a> / <a style="border-bottom: 1px solid #222;" href="/jsp/serviceEdit.jsp?id=${param.id}&name=${param.name}&content=${param.content}&price=${param.price}&picture=${param.picture}">Изменить</a>
                                    </span>
                                </p>
                            </div>
                        </c:if>
                    </div>
                    <div class="permalink-caption">
                        <p style="font-size: 30px">
                            <c:out value="${param.name}"/>
                        </p>
                    </div>
                </div>
                <img src="/resource/img/service/${param.picture}"></div>
            <div class="permalink-caption_post">
                <p class="date" style="font-size: 20px">${param.price}</p>
                <div class="permalink-caption">
                    <p style="font-size: 25px">
                        <c:out value="${param.content}"/>
                    </p>
                </div>
            </div>
        </div>

    <div class="clear"></div>
    <section class="categories"></section>
    <section class="pagination"></section><div class="clear"></div></div>
</body>
</html>
