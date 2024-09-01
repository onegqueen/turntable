<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="background.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>플레이리스트</title>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/mypage.css">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Noto+Serif:ital,wght@0,100..900;1,100..900&family=Playwrite+IS:wght@100..400&display=swap" rel="stylesheet">
</head>
<body>
<div class="mypage-container">
    <h2 class="title"> my page </h2>
    <div class="profile-logout">
        <div class="profile-info">
            <div class="profile-picture"></div>
            <div class = profile-details>
                <div class="username"></div>
                <div class="playlist-count">플레이리스트 개수를 불러오는 중...</div>
            </div>
        </div>
        <button class="logout-button" onclick="logout()">로그아웃</button>
    </div>
    <div class="form-section">
        <div class="form-group">
            <input type="text" id="new-nickname" placeholder="닉네임 변경">
            <button onclick="changeNickname()">변경하기</button>
        </div>
        <div class="form-group">
            <input type="file" id="bg-image" placeholder="배경화면 변경">
            <button onclick="changeBackground()">업로드</button>
        </div>
    </div>
    <button class="delete-button" onclick="deleteAccount()">탈퇴하기</button>
</div>

<script>
  function fetchPlaylistCount() {
    const userId = "<%= userId %>";
    $.ajax({
      url: '/user/playlist-count',
      method: 'GET',
      data: {
        userId: userId
      },
      success: function(response) {
        $('.playlist-count').text(response + '개의 플레이리스트');
      },
      error: function(error) {
        console.error('플레이리스트 개수 가져오기 실패:', error);
        $('.playlist-count').text('플레이리스트 개수를 가져오지 못했습니다.');
      }
    });
  }

  function fetchProfilePicture() {
    const pageOwnerId = "<%= pageOwnerId %>";
    fetch('/imgurl?pageOwnerId=' + pageOwnerId, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text();
    })
    .then(imageUrl => {
      console.log("Image URL:", imageUrl); // URL을 로그로 출력
      $('.profile-picture').css('background-image', 'url(' + imageUrl + ')');
      $('.profile-picture').css('background-size', 'cover');
    })
    .catch(error => console.error('Error fetching image URL:', error));
  }

  $(document).ready(function() {
    const userId = "<%= userId %>";

    fetchPlaylistCount();
    fetchProfilePicture();
    fetchUserName(userId);
  });

  function fetchUserName(pageOwnerId){
    $.ajax({
      url: '/username',
      method: 'GET',
      data: { memberId: pageOwnerId },
      success: function(response) {
        if (response) {
          console.log("username:"+response.memberNickname);
          $('.username').text("@" + response.memberNickname);
        }
      },
      error: function(error) {
        console.error('Error fetching username:', error);
      }
    });
  }

  function changeNickname() {
    var newNickname = $('#new-nickname').val();
    $.ajax({
      url: '/user/change-nickname',
      type: 'POST',
      data: newNickname,
      contentType: 'application/json; charset=utf-8',
      success: function(response) {
        alert('닉네임이 변경되었습니다.');
        // 성공 시 페이지 리다이렉트
        window.location.href = "/main";
      },
      error: function(error) {
        alert('이미 존재하는 아이디 입니다.');
      }
    });
  }

    function changeBackground() {
      var formData = new FormData();
      formData.append('newBgImg', $('#bg-image')[0].files[0]);

      $.ajax({
        url: 'user/change-bgimg',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function(response) {
          alert('배경화면이 변경되었습니다.');
          // 성공 시 페이지 리다이렉트
          window.location.href = "/main"; //
        },
        error: function(error) {
          alert('배경화면 변경에 실패했습니다.');
        }
      });
    }

    function logout() {
      $.ajax({
        url: '/logout',
        type: 'POST',
        success: function(response) {
          window.location.href = '/login';
        },
        error: function(error) {
          alert('로그아웃에 실패했습니다.');
        }
      });
    }

  function deleteAccount() {
    $.ajax({
      url: '/withdraw',
      type: 'POST',
      success: function(response) {
        alert('계정이 성공적으로 탈퇴되었습니다.');
        window.location.href = '/';
      },
      error: function(error) {
        alert('계정 탈퇴에 실패했습니다.');
      }
    });
  }
</script>
</body>
</html>
