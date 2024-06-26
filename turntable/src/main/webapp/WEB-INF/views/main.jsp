<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>플레이리스트</title>
    <link rel="stylesheet" href="/css/main.css">
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Noto+Serif:ital,wght@0,100..900;1,100..900&family=Playwrite+IS:wght@100..400&display=swap" rel="stylesheet">
    <script>
      document.addEventListener('DOMContentLoaded', (event) => {
        const username = "<%= username %>";
        console.log("Username from session: " + username); // 콘솔에 출력
        document.querySelector('.username-container').textContent = "@" + username;
      });
    </script>
</head>
<body>
<jsp:include page="background.jsp" />
<div class="container">
    <div id="main-content">
        <div class="playlist-info" id="loadNextScreen">
            <div class="icon-commentinfo">
                <div class="icon"><i class="fas fa-music music-icon"></i></div>
                <div class="comment-song">
                    <div class="message">
                        오늘 날씨가 너무 좋아요! 매콤한 떡볶이가 땡기네요 하핫 오늘은 기왕이면 소주도 탕탕후루루루루루 후루루루루
                    </div>
                    <div class="song-info">▶ 에스파 - supernova</div>
                </div>
            </div>
        </div>
        <div class="username-container" ></div>
        <button class="playlist-button" id="todayPlaylistBtn"><i class="fa-solid fa-compact-disc" id="settings-icon"></i>Today Playlist</button>

        <div class="input-section-container">
            <div class="input-section">
                <input type="text" class="input-field" placeholder="댓글을 입력하세요">
                <button class="submit-button">등록</button>
            </div>
        </div>

        <div class="comments">
            <div class="comment">
                <div class="comment-left">
                    <div class="comment-icon"><i class="fa-solid fa-circle"></i></div>
                    <div class="comment-right">
                        <div class="comment-username">@onegqueen</div>
                        <div class="comment-content">잘듣고가요! 채원님 행복하세요 ^^~</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <iframe id="comment-frame" style="width:100%; height:100%; border:none; display:none;"></iframe>
</div>

<div id="settings-drawer" class="settings-drawer">
    <div class="drawer-content">
        <ul>
            <li id="daily-turntable">dailyturntable</li>
            <li id="my-turntable">myturntable</li>
        </ul>
        <div id="playlist-container">
            <!-- 플레이리스트 아이템들이 여기에 동적으로 로드됩니다 -->
        </div>
    </div>
</div>

<script>
      document.addEventListener("DOMContentLoaded", function(){
        document.getElementById("loadNextScreen").addEventListener("click", function() {
          window.location.href = "/comment";
        });

        document.getElementById("todayPlaylistBtn").addEventListener("click", function() {
          window.location.href = "/todayplaylist";
        });


        setupSearch('artist');
        setupSearch('genre');
        setupSearch('track');
      });

      // 이벤트 위임을 사용하여 동적으로 생성된 playlist-item에 이벤트 리스너 추가
      document.querySelector(".drawer-content").addEventListener("click", function(e) {
        if (e.target.closest(".playlist-item")) {
          var playlistItem = e.target.closest(".playlist-item");
          var playlistId = playlistItem.getAttribute("data-playlist-id");
          togglePlaylistDetails(playlistItem, playlistId);
        }
      });

      document.getElementById("daily-turntable").addEventListener("click", function() {
        loadPlaylist('daily');
      });

      document.getElementById("my-turntable").addEventListener("click", function() {
        loadPlaylist('my');
      });

      // 기본으로 dailyturntable 로드
      loadPlaylist('daily');


      function loadPlaylist(type) {
        var playlistContainer = document.getElementById("playlist-container");
        playlistContainer.innerHTML = ''; // 기존 콘텐츠 제거

        var playlistData;
        if (type === 'daily') {
          playlistData = [
            { id: 1, date: '2024.06.01', madeBy: 'made by @codnjs_99' },
            { id: 2, date: '2024.06.02', madeBy: 'made by @codnjs_99' },
            { id: 3, date: '2024.06.03', madeBy: 'made by @codnjs_99' }
          ];
        } else if (type === 'my') {
          playlistData = [
            { id: 4, date: '2024.07.01', madeBy: 'made by @codnjs_99' },
            { id: 5, date: '2024.07.02', madeBy: 'made by @codnjs_99' },
            { id: 6, date: '2024.07.03', madeBy: 'made by @codnjs_99' }
          ];
        }

        playlistData.forEach(function(item) {
          var playlistItem = document.createElement("div");
          playlistItem.classList.add("playlist-item");
          playlistItem.setAttribute("data-playlist-id", item.id);
          playlistItem.innerHTML = `
                    <div class="item-header">
                        <div class="item-left">
                            <i class="fas fa-heart"></i>
                            <div class="item-text">
                                <div class="playlist-name">${item.date}</div>
                                <div class="madeby">${item.madeBy}</div>
                            </div>
                        </div>
                        <i class="fas fa-chevron-right"></i>
                    </div>
                `;
          playlistContainer.appendChild(playlistItem);
        });
      }

      function togglePlaylistDetails(item, id) {
        // 기존의 상세 목록이 있는지 확인하고 있으면 제거
        var existingDetails = item.nextElementSibling;
        if (existingDetails && existingDetails.classList.contains("playlist-details")) {
          existingDetails.remove();
          return;
        }

        // 예시 데이터를 사용하여 상세 목록 생성
        var details = document.createElement("div");
        details.classList.add("playlist-details");
        details.innerHTML = `
                <div class="song-item"><i class="fas fa-music"></i> Song 1</div>
                <div class="song-item"><i class="fas fa-music"></i> Song 2</div>
                <div class="song-item"><i class="fas fa-music"></i> Song 3</div>
            `;

        // 상세 목록을 클릭된 항목 아래에 추가
        item.insertAdjacentElement("afterend", details);
      }


      //todayplaylist
      function setupSearch(type) {
        const searchInput = document.getElementById(`${type}-search`);
        const resultsContainer = document.getElementById(`${type}-results`);
        const selectedContainer = document.getElementById(`selected-${type}s`);

        searchInput.addEventListener("input", function() {
          const query = searchInput.value.toLowerCase();
          resultsContainer.innerHTML = ''; // 기존 결과 초기화

          // 예시 데이터로 필터링 (여기서는 간단히 고정된 데이터 사용)
          const sampleData = type === 'artist' ? ['권진아', '백예린', '안지영'] :
              type === 'genre' ? ['인디음악', '힙합', '발라드'] :
                  ['나무', 'painkiller', '위로'];

          const filteredData = sampleData.filter(item => item.toLowerCase().includes(query));
          filteredData.forEach(item => {
            const resultItem = document.createElement('div');
            resultItem.textContent = item;
            resultItem.classList.add('result-item');
            resultItem.addEventListener('click', function() {
              const selectedItem = document.createElement('div');
              selectedItem.textContent = item;
              selectedItem.classList.add('selected-item');
              selectedContainer.appendChild(selectedItem);
              resultsContainer.innerHTML = ''; // 선택 후 결과 초기화
            });
            resultsContainer.appendChild(resultItem);
          });
        });
      }
    </script>
</body>
</html>
