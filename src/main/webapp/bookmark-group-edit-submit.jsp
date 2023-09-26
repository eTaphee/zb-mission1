<%@ page import="Dao.BookmarkGroupDao" %>
<%@ page import="Entity.BookmarkGroup" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");

    String name = null;
    int order = 0;
    int bookmarkGroupId = 0;
    BookmarkGroup bookmarkGroup = null;
    BookmarkGroupDao dao = new BookmarkGroupDao();

    try {
        name = request.getParameter("name");
        order = Integer.parseInt(request.getParameter("order"));
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
        bookmarkGroup.setName(name);
        bookmarkGroup.setOrder(order);
        if (dao.update(bookmarkGroup)) {
            out.print("<script>alert('북마크 그룹 정보를 수정하였습니다.');</script>");
            out.print("<script>location.href='./bookmark-group.jsp'</script>");
        }
    } catch (Exception e) {
    }
%>
