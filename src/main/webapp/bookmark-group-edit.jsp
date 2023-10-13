<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="entity.BookmarkGroup" %>
<%@ page import="dao.BookmarkGroupDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크 그룹 수정</title>
    <link rel="stylesheet" href="./css/style.css">
</head>
<body>
<%
    int bookmarkGroupId = 0;
    BookmarkGroup bookmarkGroup = null;
    BookmarkGroupDao dao = new BookmarkGroupDao();
    try {
        bookmarkGroupId = Integer.parseInt(request.getParameter("id"));
        bookmarkGroup = dao.findById(bookmarkGroupId);
        request.setAttribute("bookmarkGroup", bookmarkGroup);
    } catch (Exception e) {
    }
%>
<jsp:include page="header.jsp" flush="false"/>
<br/><br/>
<c:if test="${not empty bookmarkGroup}">
    <jsp:include page="bookmark-group-form.jsp?mode=edit" flush="false"/>
</c:if>
</body>
</html>