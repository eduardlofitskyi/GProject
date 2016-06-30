<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${sessionScope.user.role.name ne 'admin'}">
    <c:redirect url="home.jsp"/>
</c:if>
<c:if test="${empty param.type}">
    <c:redirect url="home.jsp"/>
</c:if>

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
    <c:if test="${not empty requestScope.editUser}">
        <h1>Edit user</h1>
    </c:if>
    <c:if test="${empty requestScope.editUser}">
        <h1>Add user</h1>
    </c:if>
    <div id="edit-container">

        <c:if test="${param.type == 'edit'}">
            <form method="post" action="/upd">
                <input type="hidden" name="id" value="${param.id}">
                <input type="hidden" name="username" value="${param.username}">
        </c:if>
        <c:if test="${param.type == 'add'}">
            <form method="post" action="/add">
        </c:if>
            <div class="block">
                <label>Login</label>
                <c:if test="${param.type == 'edit'}">
                    <input type="text" name="username_showed" value="${requestScope.editUser.login}" disabled/>
                </c:if>
                <c:if test="${param.type == 'add'}">
                    <input type="text" name="username" required/>
                </c:if>
            </div>
            <div class="block">
                <label>Password</label>
                <input id="password" type="password" name="password" onkeyup="validatePassword()" required/>
            </div>
            <div class="block">
                <label>Password again</label>
                <input id="confirm_password" type="password" name="password_again" onkeyup="validatePassword()" required/>
            </div>
            <div class="block">
                <label>E-mail</label>
                <c:if test="${param.type == 'edit'}">
                    <input type="text" name="email" value="${requestScope.editUser.email}" required/>
                </c:if>
                <c:if test="${param.type == 'add'}">
                    <input type="text" name="email" required/>
                </c:if>
            </div>
            <div class="block">
                <label>First name</label>
                <c:if test="${param.type == 'edit'}">
                    <input type="text" name="f_name" value="${requestScope.editUser.firstName}" required/>
                </c:if>
                <c:if test="${param.type == 'add'}">
                    <input type="text" name="f_name" required/>
                </c:if>
            </div>
            <div class="block">
                <label>Last name</label>
                <c:if test="${param.type == 'edit'}">
                    <input type="text" name="l_name" value="${requestScope.editUser.lastName}" required/>
                </c:if>
                <c:if test="${param.type == 'add'}">
                    <input type="text" name="l_name" required/>
                </c:if>
            </div>
            <div class="block">
                <label>Birthday</label>
                <input type="date" name="birthday" value="${requestScope.editUser.birthday}" required/>
            </div>
            <div class="block">
                <label>Role</label>
                <c:if test="${param.type == 'edit'}">
                    <c:if test="${requestScope.editUser.role.name eq 'admin'}">
                        <select size="1" name="role">
                            <option value="user">User</option>
                            <option selected value="admin">Admin</option>
                        </select>
                    </c:if>
                    <c:if test="${requestScope.editUser.role.name eq 'user'}">
                        <select size="1" name="role">
                            <option selected value="user">User</option>
                            <option value="admin">Admin</option>
                        </select>
                    </c:if>
                </c:if>
                <c:if test="${param.type == 'add'}">
                    <select size="1" name="role">
                        <option value="user">User</option>
                        <option value="admin">Admin</option>
                    </select>
                </c:if>
            </div>
            <div class="block">
                <input id="sbm_btn" type="submit" value="Ok" disabled>
                <input type="reset" value="Cancel"> //button 'Ok' will enable if password and password again match
            </div>
        </form>
    </div>
</div>
</body>
</html>
