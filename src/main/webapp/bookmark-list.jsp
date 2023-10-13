<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.BookmarkDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<%
    try {
        BookmarkDao dao = new BookmarkDao();
        pageContext.setAttribute("bookmarkList", dao.findAll());
    } catch (Exception e) {
    }
%>

<jsp:include page="header.jsp" flush="false"/>

<br/><br/>

<table>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>

    <c:if test="${bookmarkList == null or bookmarkList.size() == 0}">
        <tr>
            <td colspan="5" class="empty">정보가 존재하지 않습니다.</td>
        </tr>
    </c:if>

    <c:if test="${not empty bookmarkList}">
        <c:forEach var="bookmark" items="${bookmarkList}">
            <tr>
                <td>${bookmark.id}</td>
                <td>${bookmark.group.name}</td>
                <td><a href="./detail.jsp?mgrNo=${bookmark.wifi.manageNo}">${bookmark.wifi.mainName}</a></td>
                <td>${bookmark.registerDateTime}</td>
                <td style="text-align: center"><a href="./bookmark-delete.jsp?id=${bookmark.id}">삭제</a></td>
            </tr>
        </c:forEach>
    </c:if>
</table>
</body>
</html>
