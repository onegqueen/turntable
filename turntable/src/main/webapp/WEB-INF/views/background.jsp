<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Background Image</title>
    <link rel="stylesheet" href="/css/global.css">
</head>
<body>
<div id="header-placeholder"></div>

<script>
  $(function(){
    $("#header-placeholder").load("header.jsp", function() {
      document.getElementById("home-icon").addEventListener("click", function() {
        window.location.href = "main.html";
      });
      document.getElementById("settings-icon").addEventListener("click", function() {
        document.getElementById("settings-drawer").classList.toggle("open");
      });
    });
    $("#footer-placeholder").load("footer.html");
  });

  $(document).ready(function() {
    fetch(`/imgurl`)
    .then(response => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.text();
    })
    .then(imageUrl => {
      console.log("Image URL:", imageUrl); // URL을 로그로 출력
      document.body.style.backgroundImage = "url('" + imageUrl + "')";
      document.body.style.backgroundSize = "cover";
    })
    .catch(error => console.error('Error fetching image URL:', error));
  });
</script>
</body>
</html>
