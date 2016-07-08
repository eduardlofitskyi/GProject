<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
    <meta charset="utf-8">

    <title>Create new user</title>

    <link href="./css/style.css" rel="stylesheet" type="text/css">
    <script src="./js/script.js"></script>
</head>
<body>

<jsp:include page="adminbar.jsp"/>

<div id="container">

    <h1>Add user</h1>

    <div id="edit-container">
        <s:form action="add" method="POST">

            <s:textfield name="user.login" label="Login " pattern="[0-9a-zA-Z]{5,50}" required="true"/>
            <s:password id="password" name="user.password" pattern="[0-9a-zA-Z]{6,50}" label="Password "
                        onkeyup="validatePassword()" required="true"/>
            <s:password id="confirm_password" label="Confirm password" onkeyup="validatePassword()"/>
            <s:textfield name="user.email" label="E-mail "
                         pattern="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])"
                         required="true"/>
            <s:textfield name="user.firstName" pattern="[A-Za-z]{1,50}" label="First Name " required="true"/>
            <s:textfield name="user.lastName" pattern="[A-Za-z]{1,50}" label="Last Name" required="true"/>
            <s:textfield name="birthday" pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}" label="Birthday " format="yyyy-MM-dd"
                         required="true"/>
            <s:select name="user.role.name" label="Role " list="#{'user':'user','admin':'admin'}"/>

            <s:submit id="sbm_btn" disabled="true"/>

        </s:form>
    </div>
</div>
</body>
</html>