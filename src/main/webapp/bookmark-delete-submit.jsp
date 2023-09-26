<%@ page import="Entity.Bookmark" %>
<%@ page import="Dao.BookmarkDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BookmarkDao dao = new BookmarkDao();
    String bookmarkIdParam = request.getParameter("bookmark_id");
    Bookmark bookmark = null;

    if (bookmarkIdParam != null) {
        try {
            Integer bookmarkId = Integer.parseInt(bookmarkIdParam);
            bookmark = dao.findById(bookmarkId);
        } catch (Exception e) {
        }
    }

    if (bookmark == null) {
        out.print("<script>alert('잘못된 요청입니다.'); history.go(-1);</script>");
        return;
    }

    if (dao.delete(bookmark)) {
        out.print("<script>alert('북마크 정보를 삭제하였습니다.');</script>");
    }

    out.print("<script>location.href='./bookmark-list.jsp';</script>");
%>
