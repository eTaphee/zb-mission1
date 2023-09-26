<%@ page import="Dao.LocationHistoryDao" %>
<%@ page import="Entity.LocationHistory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String historyIdParam = request.getParameter("id");
    if (historyIdParam != null) {
        try {
            Integer historyId = Integer.parseInt(historyIdParam);
            LocationHistoryDao dao = new LocationHistoryDao();
            LocationHistory history = dao.findById(historyId);
            if (history == null) {
                throw new Exception();
            }
            if (dao.delete(history)) {
                out.print("<script>alert('히스토리 정보를 삭제하였습니다.');</script>");
                out.print("<script>location.href='./history.jsp'</script>");
            }
        } catch (Exception e) {
            out.print("<script>alert('잘못된 요청입니다.'); history.go(-1);</script>");
        }
    }
%>