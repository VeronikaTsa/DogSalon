<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 27.04.2018
  Time: 15:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="/css/cc.css">
        <title>Questions</title>
        <style>
            a.user {
                color:#222;
                text-decoration: none; /* Отменяем подчеркивание у ссылки */
                display:block;
                font-size:18px;
            }
            a.user:hover {
                color:#d64431; /* Цвет ссылок при наведении на них курсора мыши */
            }
            a.link {
                color:#222;
                text-decoration: none; /* Отменяем подчеркивание у ссылки */
                font-size:18px;
                border-bottom: 1px solid #222;
                font-family:'Crimson Text';
                padding-bottom:2px;
            }
            a.link:hover {
                border-bottom:1px solid #222; padding-bottom:2px; color:#555;
            }
        </style>
    </head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<jsp:include page="/ServletController" flush="true">
    <jsp:param name="command" value="questionNotAnswered" />
</jsp:include>
</body>
</html>
