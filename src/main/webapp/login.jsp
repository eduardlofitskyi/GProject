<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <meta charset="utf-8">

    <title>Sign In</title>

    <link href="./css/style.css" rel="stylesheet" type="text/css">

</head>
<div id="container">
    <div id="signin-container" class="container">
        <s:form action="login" method="post">
            <s:textfield id="login_field" name="login" label="Username" size="20"/>
            <s:password id="pass_field" name="password" label="Password" size="20"/>
            <s:submit method="execute" align="right"/>
        </s:form>
        <c:if test="${not empty param.error}">
            <h5>Invalid credential! Try again</h5>
        </c:if>
    </div>
</div>
</body>
</html>
