<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div id="login-form">
    <form action="ServletVerification" method="post" enctype="application/x-www-form-urlencoded">
        <h4>Please input login and password to verify your account</h4><br>
        Login
        <input name="login" type="text">
        <input type="submit" value="Submit">
    </form>
    </div>
</body>
</html>
