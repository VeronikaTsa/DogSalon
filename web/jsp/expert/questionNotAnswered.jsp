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
        <link rel="stylesheet" href="<c:url value="/css/cc.css"/>">
        <title>Questions</title>
    </head>
<body>
<jsp:include page="../../WEB-INF/jspf/head.jsp"/>
<jsp:include page="../../WEB-INF/jspf/logo.jsp"/>
<jsp:include page="/ServletController" flush="true">
    <jsp:param name="command" value="questionNotAnswered" />
</jsp:include>
</body>
</html>
