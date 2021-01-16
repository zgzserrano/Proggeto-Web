<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/myStyle.css">
    <title>Login</title>
    <meta http-equiv="Cache-Control" content="no-store" />
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <div class="login">
        <form id="login" method="get" action="login.php">
            <label><b>User Name
            </b>
            </label>
            <input type="text" name="Uname" id="Uname" placeholder="Username">
            <br><br>
            <label><b>Password
            </b>
            </label>
            <input type="Password" name="Pass" id="Pass" placeholder="Password">
            <br><br>
            <input type="button" name="log" id="log" value="Log In Here">
            <br><br>
            <input type="checkbox" id="check">
            <span>Remember me</span>
            <br><br>
            Forgot <a href="#">Password</a>
        </form>
    </div>

</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>