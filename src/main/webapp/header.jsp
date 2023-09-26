<%--
  Created by IntelliJ IDEA.
  User: thlee
  Date: 2023/09/26
  Time: 8:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1 id="title"></h1>
<a href="./index.jsp">홈</a> |
<a href="./history.jsp">위치 히스토리 목록</a> |
<a href="./load-wifi.jsp">Open API 와이파이 정보 가져오기</a> |
<a href="./bookmark-list.jsp">즐겨 찾기 보기</a> |
<a href="./bookmark-group.jsp">즐겨 찾기 그룹 관리</a>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("title").innerHTML = document.title;
    });
</script>