<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
    <meta charset="utf-8">
    <title>Edit user</title>
    <link href="./css/style.css" rel="stylesheet" type="text/css">
    <script src="./js/script.js"></script>
</head>
<body>

<jsp:include page="adminbar.jsp"/>

<div id="container">

    <h1>Add user</h1>

    <div id="edit-container">
        <form:form method="post" action="/upd" commandName="user">
            <form:hidden path="id" name="id" value="${param.id}"/>
            <div class="block">
                <label>Login</label>
                <form:input path="login" type="text" name="login_showed" value="${requestScope.editUser.login}" readonly="true"/>
            </div>
            <div class="block">
                <label>Password</label>
                <form:input path="password" id="password" type="password" name="password" onkeyup="validatePassword()" required="required"/>
                <form:errors path="password"/>
            </div>
            <div class="block">
                <label>Password again</label>
                <input id="confirm_password" type="password" name="password_again" onkeyup="validatePassword()"
                       required/>
            </div>
            <div class="block">
                <label>E-mail</label>
                <form:input path="email" type="text" name="email" value="${requestScope.editUser.email}" required="required"/>
                <form:errors path="email"/>
            </div>
            <div class="block">
                <label>First name</label>
                <form:input path="firstName" type="text" name="firstName" value="${requestScope.editUser.firstName}" required="required"/>
                <form:errors path="firstName"/>
            </div>
            <div class="block">
                <label>Last name</label>
                <form:input path="lastName" type="text" name="lastName" value="${requestScope.editUser.lastName}" required="required"/>
                <form:errors path="lastName"/>
            </div>
            <div class="block">
                <label>Birthday</label>
                <form:input path="birthday" type="date" name="birthday" value="${requestScope.editUser.birthday}" required="required"/>
                <form:errors path="birthday"/>
            </div>
            <div class="block">
                <label>Role</label>
                <form:select path="role" size="1" name="role">
                    <c:if test="${requestScope.editUser.role.name eq 'admin'}">
                        <form:option value="user">User</form:option>
                        <form:option value="admin" selected="true">Admin</form:option>
                    </c:if>
                    <c:if test="${requestScope.editUser.role.name ne 'admin'}">
                        <form:option value="user" selected="true">User</form:option>
                        <form:option value="admin">Admin</form:option>
                    </c:if>

                </form:select>
            </div>
            <div class="block">
                <form:button path="sbm_btn" id="sbm_btn" type="submit" disabled="true">Ok</form:button>
                <form:button path="clean_btn" type="reset">Cancel</form:button> //button 'Ok' will enable if password and password again match
            </div>
        </form:form>
    </div>
</div>
</body>
</html>