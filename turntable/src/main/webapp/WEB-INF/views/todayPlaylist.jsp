<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Today's Playlist</title>
    <link rel="stylesheet" href="/css/global.css">
    <link rel="stylesheet" href="/css/styles.css">
    <link rel="stylesheet" href="/css/todayPlaylist.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<jsp:include page="background.jsp" />

<div class="container">
    <div id="playlist-section" class="content-box">
        <div class="playlist-header">
            <button class="back-button" id="backToMain"><</button>
            <h2>Today's Playlist</h2>
        </div>
        <div id = "recommend-select">
            <div class="search-section">
                <div class="search-box">
                    <i class="fas fa-search"></i>
                    <input type="text" id="artist-search" placeholder="가수 검색">
                    <div id="artist-results" class="search-results"></div>
                </div>
                <div class="search-box">
                    <i class="fas fa-search"></i>
                    <input type="text" id="track-search" placeholder="노래 검색">
                    <div id="track-results" class="search-results"></div>
                </div>
                <div class="search-box">
                    <input type="text" id="genre-search" placeholder="장르 검색" readonly>
                    <i class="fas fa-chevron-down" id="toggle-genre-btn"></i>
                    <div id="genre-results" class="search-results hidden"></div>
                </div>
            </div>
<%--            선택항목--%>
                <div class="selected-section">
                    <div class="selected-category">
                        <h3>가수</h3>
                        <hr>
                        <div id="selected-artists"></div>
                    </div>
                    <div class="selected-category">
                        <h3>노래</h3>
                        <hr>
                        <div id="selected-tracks"></div>
                    </div>
                    <div class="selected-category">
                        <h3>장르</h3>
                        <hr>
                        <div id="selected-genres"></div>
                    </div>
                </div>
                <button class="recommend-button">추천받기</button>
            </div>

        <div id="recommendations-section" class="recommendations-section">
            <div id="recommendations-list" class="recommendations-list"></div>
            <button class="save-playlist-button">플레이리스트 등록</button>
        </div>

    </div>
    <div id="footer-placeholder"></div>
</div>

<script>
  $(document).ready(function() {
      const username = "<%= username %>";
      console.log(username);

    $("#header-placeholder").load("header.jsp", function() {
      $("#home-icon").click(function() {
        window.location.href = "/main";
      });
      $("#settings-icon").click(function() {
        $("#settings-drawer").toggleClass("open");
      });
    });

    $("#footer-placeholder").load("footer.jsp");

    $("#backToMain").click(function() {
      if ($("#recommend-select").is(":visible")) {
        window.location.href = "/main";
      } else {
        $("#recommendations-section").hide();
        $("#recommend-select").show();
      }
    });

    setupSearch('artist');
    setupSearch('track');
    setupToggleSearch('genre');

    $(".recommend-button").click(function() {
      submitRecommendations();
    });
    $(".save-playlist-button").click(function() {
        savePlaylist();
    });
  });

  function setupSearch(type) {
    console.log(type)
    const searchInput = document.getElementById(type+`-search`);
    const resultsContainer = document.getElementById(type+`-results`);
    const selectedContainer = document.getElementById(`selected-`+type+`s`);

    function search() {
      const query = searchInput.value;
      if (!query.trim()) return; // 입력값이 비어있을 경우 요청하지 않음
      resultsContainer.innerHTML = ''; // 기존 결과 초기화

      // AJAX 요청을 통해 서버에서 검색 결과를 가져옴
      $.ajax({
        url: `/search/`+type,
        method: 'GET',
        data: { keyword: query },
        success: function(data) {
          data.forEach(item => {
            const resultItem = document.createElement('div');
            resultItem.textContent = item.name;
            resultItem.classList.add('result-item');
            resultItem.dataset.id = item.id;
            resultItem.addEventListener('click', function() {
              const selectedItemsCount = document.querySelectorAll('.selected-item').length;
              if (selectedItemsCount >= 5) {
                alert("최대 5개의 아이템만 선택할 수 있습니다.");
                return;
              }
              const selectedItem = document.createElement('div');
              selectedItem.textContent = item.name;
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

    function setupToggleSearch(type) {
      console.log("Setting up toggle search for " + type);
      const toggleButton = document.getElementById(`toggle-`+type+`-btn`);
      const resultsContainer = document.getElementById(type+`-results`);
      const selectedContainer = document.getElementById(`selected-`+type+`s`);

      if (!toggleButton || !resultsContainer || !selectedContainer) {
        console.error(`Missing element for ${type} toggle search`);
        return;
      }

      function search() {
        resultsContainer.innerHTML = '';

        $.ajax({
          url: `/search/`+ type,
          method: 'GET',
          success: function(data) {
            data.forEach(item => {
              const resultItem = document.createElement('div');
              resultItem.textContent = item.name;
              resultItem.classList.add('result-item');
              resultItem.addEventListener('click', function() {
                const selectedItemsCount = document.querySelectorAll('.selected-item').length;
                if (selectedItemsCount >= 5) {
                  alert("최대 5개의 아이템만 선택할 수 있습니다.");
                  return;
                }
                const selectedItem = document.createElement('div');
                selectedItem.textContent = item.name;
                selectedItem.classList.add('selected-item');
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

      toggleButton.addEventListener('click', function() {
        console.log(`Toggle button clicked for ${type} search`);
        search();
        resultsContainer.classList.toggle('hidden');
      });
    }

      function submitRecommendations() {
        const seedArtists = Array.from(document.querySelectorAll('#selected-artists .selected-item')).map(item => item.dataset.id);
        const seedTracks = Array.from(document.querySelectorAll('#selected-tracks .selected-item')).map(item => item.dataset.id);
        const seedGenres = Array.from(document.querySelectorAll('#selected-genres .selected-item')).map(item => item.textContent);

        const RecommendRequestDto = {
          seedArtists: seedArtists,
          seedTracks: seedTracks,
          seedGenres: seedGenres
        };

        console.log("Submitting recommendations with data:", RecommendRequestDto);

        $.ajax({
          url: '/recommendations',
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify(RecommendRequestDto),
          success: function(response) {
            console.log("Received recommendations:", response);
            displayRecommendations(response);
          },
          error: function(xhr, status, error) {
            console.error(`Error: ${status}, ${error}`);
          }
        });
    }

  function displayRecommendations(recommendations) {
    const recommendationsList = document.getElementById('recommendations-list');
    recommendationsList.innerHTML = '';
    recommendations.forEach(function(track) {
      var trackItem = document.createElement('div');
      trackItem.classList.add('recommendation-item');
      trackItem.dataset.spotifySongId = track.id;

      var trackInfo = document.createElement('div');
      trackInfo.classList.add('track-info');

      var trackTitle = document.createElement('div');
      trackTitle.classList.add('track-title');
      trackTitle.textContent = track.name;

      var trackArtist = document.createElement('div');
      trackArtist.classList.add('track-artist');
      trackArtist.textContent = track.artists.join(', ');

      var trackAlbum = document.createElement('div');
      trackAlbum.classList.add('track-album');
      trackAlbum.textContent = track.albumName;

      trackInfo.appendChild(trackTitle);
      trackInfo.appendChild(trackArtist);
      trackInfo.appendChild(trackAlbum);
      trackItem.appendChild(trackInfo);
      recommendationsList.appendChild(trackItem);
    });

    document.getElementById('recommend-select').style.display = 'none'; // 항목 선택 섹션 숨김
    document.getElementById('recommendations-section').style.display = 'block';
  }

  function savePlaylist() {
      const recommendedTracks = Array.from(document.querySelectorAll('.recommendation-item')).map(item => {
          return {
              spotifySongId: item.dataset.spotifySongId,
              name: item.querySelector('.track-title').textContent,
              artists: item.querySelector('.track-artist').textContent.split(', '),
              albumName: item.querySelector('.track-album').textContent
          };
      });



      const playlistData = {
          name: "Today's Playlist", // 예시로 이름 지정, 필요시 동적으로 설정
          tracks: recommendedTracks
      };

      console.log("Submitting playlist with data:", playlistData);

      $.ajax({
          url: '/api/playlists/Daily', // 1은 회원 ID로, 실제 구현에서는 동적으로 설정
          method: 'POST',
          contentType: 'application/json',
          data: JSON.stringify(playlistData),
          success: function(response) {
              console.log("Playlist saved successfully:", response);
              alert("플레이리스트가 성공적으로 저장되었습니다!");
          },
          error: function(xhr, status, error) {
              console.error(`Error: ${status}, ${error}`);
              alert("플레이리스트 저장 중 오류가 발생했습니다.");
          }
      });
  }

</script>
</body>
</html>
