<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="user" value="${sessionScope.user}"/>
<c:if test="${sessionScope.user.role.name eq 'admin'}">
    <c:redirect url="admin.jsp"/>
</c:if>

<html>
<head>
    <meta charset="utf-8">

    <title>Home page</title>

    <link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="container" class="align-center">
        <c:if test="${not empty user}">
            <h1>Hello, ${sessionScope.user.firstName}</h1>
            <p>Click <a href="/logout">here</a> to logout</p>
        </c:if>
        <c:if test="${empty user}">
            <h1>Hello, ANONYMOUS</h1>
            <p>Sign in <a href="index.jsp">here</a></p>
        </c:if>
    </div>
</body>
</html>
