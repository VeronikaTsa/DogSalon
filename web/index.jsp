<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 01.04.2018
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
  <c:set var="user" value="${sessionScope.user}"/>
  <c:if test = "${empty sessionScope.language}">
    <c:set var="language" value='en_US' scope="session"/>
  </c:if>
  <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
  <fmt:setLocale value="${language}" />
  <fmt:setBundle basename="text" var="local"/>
  <title>Happy Puppy</title>

</head>
<body>

<jsp:include page="/WEB-INF/jspf/head.jsp"/>

<div id="wrapper">
  <jsp:include page="/WEB-INF/jspf/logo.jsp"/>
  <section class="posts">

    <jsp:include page="/ServletController" flush="true">
      <jsp:param name="command" value="service" />
    </jsp:include>
</section>
</div>
</body>
</html>
