<%@ page import="entity.BookmarkGroup" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    BookmarkGroup bookmarkGroup = (BookmarkGroup) request.getAttribute("bookmarkGroup");
    String mode = request.getParameter("mode");
    String action = null;
    String buttonValue = null;
    switch (mode) {
        case "add":
            action = "./bookmark-group-add-submit.jsp";
            buttonValue = "추가";
            break;
        case "edit":
            action = "./bookmark-group-edit-submit.jsp";
            buttonValue = "수정";
            break;
        case "delete":
            action = "./bookmark-group-delete-submit.jsp";
            buttonValue = "삭제";
            break;
    }
%>
<form method="post" action="<%=action%>">
    <table>
        <tr>
            <th style="width: 30%">북마크 이름</th>
            <td>
                <input type="text" id="name" name="name" required
                       value="<%=((bookmarkGroup != null) ? bookmarkGroup.getName() : "")%>"/>
            </td>
        </tr>
        <tr>
            <th>순서</th>
            <td>
                <input type="text" id="order" name="order" required pattern="[0-9]+"
                       value="<%=((bookmarkGroup != null) ? bookmarkGroup.getOrder() : 1)%>"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <div style="display: <%=(mode.equals("add") ? "none" : "contents")%>">
                    <a href="./bookmark-group.jsp">돌아가기</a>
                    |
                </div>
                <form>
                    <input type="hidden" name="id" value="<%=((bookmarkGroup != null) ? bookmarkGroup.getId() : 0)%>">
                    <input type="submit" value="<%=buttonValue%>"/>
                </form>
            </td>
        </tr>
    </table>
</form>