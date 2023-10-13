<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.BookmarkGroupDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크 그룹</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<%
    try {
        BookmarkGroupDao dao = new BookmarkGroupDao();
        pageContext.setAttribute("bookmarkGroupList", dao.findAll());
    } catch (Exception e) {
    }
%>

<jsp:include page="header.jsp" flush="false"/>

<br/><br/>

<form method="GET" action="./bookmark-group-add.jsp">
    <input type="submit" value="북마크 그룹 이름 추가"/>
</form>

<br/>

<table>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>

    <c:if test="${bookmarkGroupList == null or bookmarkGroupList.size() == 0}">
        <tr>
            <td colspan="6" class="empty">정보가 존재하지 않습니다.</td>
        </tr>
    </c:if>

    <c:if test="${not empty bookmarkGroupList}">
        <c:forEach var="bookmarkGroup" items="${bookmarkGroupList}">
            <tr>
                <td>${bookmarkGroup.id}</td>
                <td>${bookmarkGroup.name}</td>
                <td>${bookmarkGroup.order}</td>
                <td>${bookmarkGroup.registerDateTime}</td>
                <td>${bookmarkGroup.updatedDateTime}</td>
                <td style="text-align: center">
                    <a href="./bookmark-group-edit.jsp?id=${bookmarkGroup.id}">수정</a>
                    <a href="./bookmark-group-delete.jsp?id=${bookmarkGroup.id}">삭제</a>
                </td>
            </tr>
        </c:forEach>
    </c:if>
</table>

</body>
</html>