<%--
  Created by IntelliJ IDEA.
  User: Veronichka
  Date: 11.05.2018
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/css/cc.css">

    <title>Add Service</title>
    <style>
        .no-spinners {
            -moz-appearance:textfield;
        }
        .no-spinners::-webkit-inner-spin-button,
        .no-spinners::-webkit-outer-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }
    </style>
</head>
<body>
<jsp:include page="../head.jsp"/>
<jsp:include page="../logo.jsp"/>
<form action="/ServletController" method="post" charset="UTF-8">
    Введите имя услуги: <Input type="Text" name="name" value=""/>${requestScope.map.name}
    <br>
    <br>
    Введите описание: <Input type="Text" name="content" value=""/>${requestScope.map.content}
    <br>
    <br>
    Введите цену: <input type="number" class="no-spinners" name ="price"  value=""/>${requestScope.map.price}
    <br>
    <br>


    <br>
    <Input type="submit" value="Создать услугу"/>
    <input type="hidden" name="command" value="serviceadd" />
    <br>
    <br>
</form>
</body>
</html>
