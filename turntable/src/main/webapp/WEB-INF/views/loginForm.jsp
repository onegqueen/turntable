<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Turntable - Login</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/login.css">
</head>
<body>
<div class="container">
    <div class="left">
        <h1>Turntable; 매일 당신만을 위한 플레이리스트</h1>
        <p>Turntable ; 은 매일 새로운 음악을 발견하고 싶은 당신을 위해</p>
        <p>맞춤형 플레이리스트를 제공합니다.</p>
        <p>당신의 취향과 분위기에 맞춘 신선한 곡들을 추천받아</p>
        <p>음악 감상의 즐거움을 누려보세요.</p>
    </div>
    <div class="right">
        <div class="login-container">
            <h2 class="text-center">Sign in</h2>
            <form action="/login" method="POST">
                <div class="form-group">
                    <input type="text" class="form-control" name="username" id="username" placeholder="아이디" required>
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" name="password" id="password" placeholder="비밀번호" required>
                </div>
                <div class="form-group text-center">
                    <button type="submit" class="btn btn-primary">로그인</button>
                    <a href="/oauth2/authorization/kakao" class="login-button">
                        <img src="/css/bgimg/kakao_login_medium_narrow.png" alt="Login with Kakao">
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
