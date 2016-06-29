<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<div class="align-right">
    <p>Admin ${sessionScope.user.firstName} (<a href="/logout">Log out</a>)</p>
</div>
</body>
</html>
