<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="principal" property="principal"/>
<html>
<head>
    <meta charset="utf-8">

    <title>Home page</title>

    <link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div id="container" class="align-center">
    <h1>Hello, ${principal.username}</h1>
    <form action="/logout" method="post">
        <button type="submit">Click here to logout</button>
    </form>
</div>
</body>
</html>
