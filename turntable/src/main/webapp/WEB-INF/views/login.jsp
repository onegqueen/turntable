<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Turntable - Login</title>
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="container">
    <div class="login-box">
        <h1>Turntable</h1>
        <h2>Signin</h2>
        <form action="/login" method="POST">
            <input type="text" name="username" id="username" placeholder="id" required>
            <input type="password" name="password" id="password" placeholder="pw" required>
            <button type="submit">signin</button>
        </form>
    </div>
</div>
</body>
</html>
