<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tomcat.apache.org/example-taglib" prefix="ct" %>
<html>
<head>
    <meta charset="utf-8">

    <title>Admin page</title>

    <link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="container">
        <jsp:include page="adminbar.jsp" />


        <jsp:include page="admin.html" />

    </div>
</body>
</html>
