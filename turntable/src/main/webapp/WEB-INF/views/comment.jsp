<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Long userId = (Long) session.getAttribute("userId");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>플레이리스트</title>
    <link rel="stylesheet" href="/css/comment.css">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Noto+Serif:ital,wght@0,100..900;1,100..900&family=Playwrite+IS:wght@100..400&display=swap" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
      document.addEventListener('DOMContentLoaded', (event) => {
        const userId = "<%= userId %>";
        console.log("UserId from session: " + userId); // 콘솔에 출력
      });
    </script>
</head>
<body>
<jsp:include page="background.jsp" />
<div class="comment-container">
    <div class="main-content">
        <div class="input-section">
            <input type="text" class="input-field" placeholder="오늘의 기분을 입력해주세요">
            <button class="song-button"><i class="fas fa-music music-icon" ></i></button>
            <button class="submit-button"><i class="fa-solid fa-plus"></i></button>
        </div>
        <div class="comment-info">
            <div class="icon-commentinfo">
                <i class="fas fa-music music-icon" ></i>
                <div class="comment-song">
                    <div class="comment-message">
                    </div>
                    <div class="comment-song-info"></div>
                </div>
            </div>
            <div class="comment-footer">
                <a class="comment-count"></a>
                <div class="comment-date"></div>
            </div>
        </div>
        <!-- 모달 창 -->
        <div id="songModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <div class="search-box">
                    <i class="fas fa-search"></i>
                    <input type="text" id="track-search" placeholder="노래 검색">
                </div>
                <div id="track-results" class="search-results"></div>
                <div id="selected-tracks" class="selected-container"></div>
                <button id="add-music-button">음악 추가하기</button>
            </div>
        </div>
    </div>
</div>
<script>
  $(document).ready(function() {
    let selectedSpotifySongId = ""; // 선택된 노래의 ID를 저장할 변수

    // 댓글 작성 버튼 클릭 이벤트 처리
    $('.submit-button').click(function() {
      var commentInput = $('.input-field').val().trim();

      if (commentInput) {
        // 현재 날짜를 가져오기
        var currentDate = new Date().toISOString().slice(0,-1);

        // 서버에 댓글을 저장하는 AJAX 호출
        $.ajax({
          type: 'POST',
          url: '/comment', // 서버의 댓글 저장 엔드포인트
          contentType: 'application/json',
          data: JSON.stringify({
            comment: commentInput,
            date: currentDate,
            spotifySongId: selectedSpotifySongId
          }),
          success: function() {
            // 성공 시 페이지 리다이렉트
              window.location.href = "/comment"; // 댓글 목록 페이지로 리다이렉트
            },
          error: function(error) {
            console.error('Error saving comment:', error);
          }
        });
      }
    });

    // 모달 창 열기
    $('.song-button').click(function() {
      $('#songModal').css('display', 'block');
      setupSearch('track');
    });

    // 모달 창 닫기
    $('.close').click(function() {
      $('#songModal').css('display', 'none');
    });

    // 모달 창 외부 클릭 시 닫기
    $(window).click(function(event) {
      if (event.target.id == 'songModal') {
        $('#songModal').css('display', 'none');
      }
    });

    // "음악 추가하기" 버튼 클릭 이벤트 처리
    $('#add-music-button').click(function() {
      const selectedItem = $('.selected-item').first();
      if (selectedItem.length) {
        selectedSpotifySongId = selectedItem.data('id');
        $('#songModal').css('display', 'none');
      } else {
        alert("음악을 선택해주세요.");
      }
    });

    // 노래 검색 설정
    function setupSearch(type) {
      console.log(type);
      const searchInput = document.getElementById(type + '-search');
      const resultsContainer = document.getElementById(type + '-results');
      const selectedContainer = document.getElementById('selected-' + type + 's');

      function search() {
        const query = searchInput.value;
        if (!query.trim()) return; // 입력값이 비어있을 경우 요청하지 않음
        resultsContainer.innerHTML = ''; // 기존 결과 초기화

        // AJAX 요청을 통해 서버에서 검색 결과를 가져옴
        $.ajax({
          url: `/search/` + type,
          method: 'GET',
          data: { keyword: query },
          success: function(data) {
            data.forEach(item => {
              const artists = item.artists.join(", ");
              const displayName = item.name+ '-' + artists;
              const resultItem = document.createElement('div');
              resultItem.textContent = displayName;
              resultItem.classList.add('result-item');
              resultItem.dataset.id = item.id;
              resultItem.addEventListener('click', function() {
                const selectedItemsCount = document.querySelectorAll('.selected-item').length;
                if (selectedItemsCount >= 5) {
                  alert("최대 5개의 아이템만 선택할 수 있습니다.");
                  return;
                }
                const selectedItem = document.createElement('div');
                selectedItem.textContent = displayName;
                selectedItem.classList.add('selected-item');
                selectedItem.dataset.id = item.id;
                selectedItem.addEventListener('click', function() {
                  selectedItem.remove();
                });
                selectedContainer.appendChild(selectedItem);
                resultsContainer.innerHTML = '';
              });
              resultsContainer.appendChild(resultItem);
            });
          },
          error: function(xhr, status, error) {
            console.error(`Error: ${status}, ${error}`);
          }
        });
      }

      // 검색 입력의 Enter 키 이벤트에 대한 디버그 로그 추가
      searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
          console.log(`Enter key pressed for ${type} search`);
          search();
        }
      });
    }
  });
</script>
</body>
</html>
