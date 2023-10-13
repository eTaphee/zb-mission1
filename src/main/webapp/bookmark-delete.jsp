<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.BookmarkDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크 삭제</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<%
    String bookmarkIdParam = request.getParameter("id");
    if (bookmarkIdParam != null) {
        try {
            Integer bookmarkId = Integer.parseInt(bookmarkIdParam);
            BookmarkDao dao = new BookmarkDao();
            pageContext.setAttribute("bookmark", dao.findById(bookmarkId));
        } catch (Exception e) {
        }
    }
%>

<jsp:include page="header.jsp" flush="false"/>

<br/><br/>

<c:if test="${not empty bookmark}">
    <div>북마크를 삭제하시겠습니까?</div>

    <br/>

    <table>
        <tr>
            <th style="width: 30%">북마크 이름</th>
            <td>${bookmark.group.name}</td>
        </tr>
        <tr>
            <th>와이파이명</th>
            <td>${bookmark.wifi.mainName}</td>
        </tr>
        <tr>
            <th>등록일자</th>
            <td>${bookmark.registerDateTime}</td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <a href="./bookmark-list.jsp">돌아가기</a>
                |
                <form method="post" action="./bookmark-delete-submit.jsp" style="display: contents">
                    <input type="hidden" name="bookmark_id" value="${bookmark.id}">
                    <input type="submit" value="삭제"/>
                </form>
            </td>
        </tr>
    </table>
</c:if>
</body>
</html>
