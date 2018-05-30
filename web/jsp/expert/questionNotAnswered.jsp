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
<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>
${requestScope.answerAddSuccess}
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
            <form action="ServletController" method="post">
                <input type="hidden" name="id" value="${element.id}"/>
                <Input type="submit" value="Удалить"/>
                <input type="hidden" name="command" value="questionDelete" />
            </form>


            <a class="link" href="/jsp/expert/answerAdd.jsp?questionContent=${element.content}&questionCreateTime=${element.createTime}&questionAuthor=${element.userLogin}&questionId=${element.id}">Ответить</a>

            <br>
        </c:if>
        <br>
    </c:forEach>
</div>
</c:if>
</body>
</html>
