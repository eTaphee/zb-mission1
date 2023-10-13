<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.BookmarkDao" %>
<%@ page import="dao.LocationHistoryDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>위치 히스토리 목록</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<%
    try {
        LocationHistoryDao dao = new LocationHistoryDao();
        pageContext.setAttribute("historyList", dao.findAll());
    } catch (Exception e) {
    }
%>

<jsp:include page="header.jsp" flush="false"/>

<br/><br/>

<table>
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>

    <c:if test="${historyList == null or historyList.size() == 0}">
        <tr>
            <td colspan="5" class="empty">정보가 존재하지 않습니다.</td>
        </tr>
    </c:if>

    <c:if test="${not empty historyList}">
        <c:forEach var="history" items="${historyList}">
            <tr>
                <td>${history.id}</td>
                <td>${history.latitude}</td>
                <td>${history.longitude}</td>
                <td>${history.searchDateTime}</td>
                <td style="text-align: center"><a href="./history-delete.jsp?id=${history.id}">삭제</a></td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
