<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib tagdir='/WEB-INF/tags' prefix='sc'%>

<html>
<head>
    <meta charset="utf-8">
    <title>Sign up</title>
    <link href="./css/style.css" rel="stylesheet" type="text/css">
    <script src="./js/utils.js"></script>
    <script src="https://www.google.com/recaptcha/api.js"></script>
</head>
<body>
<div class="align-center">
<form:form method="post" action="/signup" commandName="user">
    <div class="block">
        <label>Login</label>
        <form:input path="login" type="text" name="login" required="required"/>
        <form:errors path="login"/>
    </div>
    <div class="block">
        <label>Password</label>
        <form:input path="password" id="password" type="password" name="password" onkeyup="validatePassword()"
                    required="required"/>
        <form:errors path="password"/>
    </div>
    <div class="block">
        <label>Password again</label>
        <input id="confirm_password" type="password" name="password_again" onkeyup="validatePassword()"
               required/>
    </div>
    <div class="block">
        <label>E-mail</label>
        <form:input path="email" type="text" name="email" required="required"/>
        <form:errors path="email"/>
    </div>
    <div class="block">
        <label>First name</label>
        <form:input path="firstName" type="text" name="firstName" required="required"/>
        <form:errors path="firstName"/>
    </div>
    <div class="block">
        <label>Last name</label>
        <form:input path="lastName" type="text" name="lastName" required="required"/>
        <form:errors path="lastName"/>
    </div>
    <div class="block">
        <label>Birthday</label>
        <form:input path="birthday" type="date" name="birthday" required="required"/>
        <form:errors path="birthday"/>
    </div>
    <div class="block">
        <label>Role</label>
        <form:select path="role" size="1" name="role">
            <form:option value="user">User</form:option>
            <form:option value="admin">Admin</form:option>
        </form:select>
    </div>
    <div class="block">
        <sc:captcha/>
        <form:button path="sbm_btn" id="sbm_btn" type="submit" disabled="true">Ok</form:button>
        <form:button path="clean_btn" type="reset">Cancel</form:button> //button 'Ok' will enable if password and
        password again match
    </div>
</form:form>
</div>
</body>
</html>
