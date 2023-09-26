<%@ page import="Dao.BookmarkDao" %>
<%@ page import="Entity.Bookmark" %>
<%@ page import="Entity.PublicWifi" %>
<%@ page import="Entity.BookmarkGroup" %>
<%@ page import="Dao.BookmarkGroupDao" %>
<%@ page import="Dao.PublicWifiDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    PublicWifi wifi = null;
    BookmarkGroup bookmarkGroup = null;

    String bookmarkGroupIdParam = request.getParameter("bookmark_group_id");
    if (bookmarkGroupIdParam != null) {
        try {
            Integer bookmarkGroupId = Integer.parseInt(bookmarkGroupIdParam);
            BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
            bookmarkGroup = bookmarkGroupDao.findById(bookmarkGroupId);
        } catch (Exception e) {
        }
    }

    String wifiManageNo = request.getParameter("wifi_manage_no");
    if (wifiManageNo != null) {
        try {
            PublicWifiDao publicWifiDao = new PublicWifiDao();
            wifi = publicWifiDao.findById(wifiManageNo);
        } catch (Exception e) {
        }
    }

    if (bookmarkGroup == null || wifi == null) {
        out.print("<script>alert('잘못된 요청입니다.'); history.go(-1);</script>");
        return;
    }

    BookmarkDao bookmarkDao = new BookmarkDao();
    Bookmark bookmark = bookmarkDao.findByBookmarkGroupIdAndWifiManageNo(bookmarkGroup.getId(), wifi.getManageNo());
    if (bookmark == null) {
        bookmark = bookmarkDao.create(Bookmark.builder().group(bookmarkGroup).wifi(wifi).build());
        if (bookmark != null) {
            out.print("<script>alert('북마크 정보를 추가하였습니다.');</script>");
        }
    } else {
        out.print("<script>alert('북마크 정보가 존재합니다.');</script>");
    }

    out.print("<script>location.href='./bookmark-list.jsp';</script>");
%>
