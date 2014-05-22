<%--
  User: Vladislav Povedyuk
  Date: 21.10.13 
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Access Denied !!!</title>
</head>
<body>
<center>
    <h1>Access to this page denied for you, you have no authorities to enter.</h1>

    <h3>If you want to enter please <a href="/register">Register</a></h3>


    <img id="img" src="<c:url value='/resources/img/access-denied.jpg'/>">
</center>

</body>
</html>