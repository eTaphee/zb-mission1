<%@ page import="dao.BookmarkGroupDao" %>
<%@ page import="entity.BookmarkGroup" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int bookmarkGroupId = 0;
    BookmarkGroup bookmarkGroup = null;
    BookmarkGroupDao dao = new BookmarkGroupDao();

    try {
        bookmarkGroupId = Integer.parseInt(request.getParameter("id"));
        bookmarkGroup = dao.findById(bookmarkGroupId);
        if (bookmarkGroup == null) {
            throw new Exception();
        }
    } catch (Exception e) {
        out.print("<script>alert('잘못된 요청입니다.'); history.go(-1);</script>");
        return;
    }

    try {
        if (dao.delete(bookmarkGroup)) {
            out.print("<script>alert('북마크 그룹 정보를 삭제하였습니다.');</script>");
            out.print("<script>location.href='./bookmark-group.jsp'</script>");
        }
    } catch (Exception e) {
    }
%>
