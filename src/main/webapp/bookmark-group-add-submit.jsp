<%@ page import="Dao.BookmarkGroupDao" %>
<%@ page import="Entity.BookmarkGroup" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    request.setCharacterEncoding("utf-8");

    String name = null;
    int order = 0;

    try {
        name = request.getParameter("name");
        order = Integer.parseInt(request.getParameter("order"));
    } catch (Exception e) {
        out.print("<script>alert('잘못된 요청입니다.'); history.go(-1);</script>");
        return;
    }

    try {
        BookmarkGroupDao dao = new BookmarkGroupDao();
        BookmarkGroup bookmarkGroup = BookmarkGroup.builder().name(name).order(order).build();
        bookmarkGroup = dao.create(bookmarkGroup);
        if (bookmarkGroup != null) {
            out.print("<script>alert('북마크 그룹 정보를 추가하였습니다.');</script>");
            out.print("<script>location.href='./bookmark-group.jsp'</script>");
        }
    } catch (Exception e) {
    }
%>
