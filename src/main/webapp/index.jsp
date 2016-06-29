<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="utf-8">

        <title>Sign In</title>

        <link href="./css/style.css" rel="stylesheet" type="text/css">

    </head>
        <div id="container">
            <div id="signin_container" class="container">
                <form method="post">
                    <fieldset>

                        <div class="form-group">
                            <label class="form-control" for="login_field">Login: </label><br>
                            <label class="form-control" for="pass_field">Password: </label>
                        </div>

                        <div class="form-group">
                            <input id="login_field"  class="form-control" type="text"name="username" placeholder="Email address" required autofocus><br>
                            <input id="pass_field" class="form-control" type="password" name="password" placeholder="Password" required>
                        </div>

                        <button class="btn-submit" type="submit">Sign In</button>
                    </fieldset>
                </form>
            </div>
        </div>
    </body>
</html>
