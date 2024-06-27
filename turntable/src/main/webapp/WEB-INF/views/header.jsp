<%
    String username = (String) session.getAttribute("username");
%>
<%
    Long userId = (Long) session.getAttribute("userId");
%>
<div class="header">
    <div class="top-bar">
        <i class="fas fa-user user-icon"></i>
        <i class="fas fa-home home-icon" id="home-icon"></i>
        <i class="fa-solid fa-compact-disc" id="settings-icon"></i>
    </div>
</div>
<script>
  const username = "<%= username %>";
  const userId="<%= userId %>";
</script>