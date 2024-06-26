<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
    <link rel="stylesheet" href="/css/signup.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class = "background-box">
    <div class="signup-container">
        <h2>회원가입</h2>
        <form id="signup-form" action="/signup" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" required>
            </div>
            <div class="form-group">
                <label for="username">아이디:</label>
                <input type="text" id="username" name="username" required>
                <button type="button" id="check-username">중복확인</button>
                <span id="username-message"></span>
            </div>
            <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="confirm-password">비밀번호 확인:</label>
                <input type="password" id="confirm-password" name="confirm-password" required>
                <span id="password-message"></span>
            </div>
            <div class="form-group">
                <label for="bgImg">배경화면 업로드:</label>
                <input type="file" id="bgImg" name="bgImg" accept="image/*">
            </div>
            <button type="submit">회원가입</button>
        </form>
    </div>
</div>

<script>
  $(document).ready(function() {
    $('#username').on('input', function() {
      $('#username-message').text('');
    });

    $('#check-username').click(function() {
      const username = $('#username').val();
      $.ajax({
        url: '/check-username',
        method: 'GET',
        data: { username: username },
        success: function(response) {
          if (response.available) {
            $('#username-message').text('사용 가능한 아이디입니다.').css('color', 'green');
          } else {
            $('#username-message').text('이미 사용 중인 아이디입니다.').css('color', 'red');
          }
        },
        error: function() {
          $('#username-message').text('오류가 발생했습니다. 다시 시도해주세요.').css('color', 'red');
        }
      });
    });

    $('#signup-form').submit(function(event) {
      const password = $('#password').val();
      const confirmPassword = $('#confirm-password').val();
      if (password !== confirmPassword) {
        $('#password-message').text('비밀번호가 일치하지 않습니다.').css('color', 'red');
        event.preventDefault();
      }
    });
  });
</script>
</body>
</html>
