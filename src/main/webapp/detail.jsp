<%@ page import="Dao.PublicWifiDao" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Dao.BookmarkGroupDao" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보</title>
    <link rel="stylesheet" href="./css/style.css">
    <script>
        function onSubmit() {
            const bookmarkGroupId = document.getElementById("bookmark_group_id").value;
            if (!bookmarkGroupId || bookmarkGroupId == "0") {
                alert('북마크 그룹 이름 선택하세요');
                return false;
            }
        }

        function onChange(value) {
            document.getElementById("bookmark_group_id").value = value;
        }
    </script>
</head>
<body>
<%
    String mgrNo = request.getParameter("mgrNo");
    if (mgrNo != null) {
        try {
            PublicWifiDao dao = new PublicWifiDao();
            pageContext.setAttribute("wifi", dao.findById(mgrNo));
        } catch (Exception e) {
        }
    }

    try {
        BookmarkGroupDao bookmarkGroupDao = new BookmarkGroupDao();
        pageContext.setAttribute("bookmarkGroupList", bookmarkGroupDao.findAll());
    } catch (Exception e) {
    }

%>

    <jsp:include page="header.jsp" flush="false"/>

    <br/><br/>

    <form method="POST" action="./bookmark-add-submit.jsp" onsubmit="return onSubmit()">
        <input type="text" hidden="hidden" id="bookmark_group_id" name="bookmark_group_id">
        <select onchange="onChange(this.value)">
            <option value="0">북마크 그룹 이름 선택</option>
            <c:if test="${not empty bookmarkGroupList}">
                <c:forEach var="bookmarkGrop" items="${bookmarkGroupList}">
                    <option value="${bookmarkGrop.id}">${bookmarkGrop.name}</option>
                </c:forEach>
            </c:if>
        </select>
        <input type="submit" value="북마크 추가하기"/>
    </form>

    <br/>

    <c:if test="${not empty wifi}">
        <table>
            <tr>
                <th style="width: 30%">거리(Km)</th>
                <td><fmt:formatNumber value="${wifi.distance}" pattern="0.####" /></td>
            </tr>
            <tr>
                <th>관리번호</th>
                <td>${wifi.manageNo}</td>
            </tr>
            <tr>
                <th>자치구</th>
                <td>${wifi.wrdofc}</td>
            </tr>
            <tr>
                <th>와이파이명</th>
                <td><a href="#">${wifi.mainName}</a></td>
            </tr>
            <tr>
                <th>도로명주소</th>
                <td>${wifi.address1}</td>
            </tr>
            <tr>
                <th>상세주소</th>
                <td>${wifi.address2}</td>
            </tr>
            <tr>
                <th>설치위치(층)</th>
                <td>${wifi.installFloor}</td>
            </tr>
            <tr>
                <th>설치유형</th>
                <td>${wifi.installType}</td>
            </tr>
            <tr>
                <th>설치기관</th>
                <td>${wifi.installMBY}</td>
            </tr>
            <tr>
                <th>서비스구분</th>
                <td>${wifi.serviceSegment}</td>
            </tr>
            <tr>
                <th>망종류</th>
                <td>${wifi.cmcwr}</td>
            </tr>
            <tr>
                <th>설치년도</th>
                <td>${wifi.installYear}</td>
            </tr>
            <tr>
                <th>실내외구분</th>
                <td>${wifi.inoutDoor}</td>
            </tr>
            <tr>
                <th>WIFI접속환경</th>
                <td>${wifi.remars3}</td>
            </tr>
            <tr>
                <th>X좌표</th>
                <td>${wifi.latitude}</td>
            </tr>
            <tr>
                <th>Y좌표</th>
                <td>${wifi.longitude}</td>
            </tr>
            <tr>
                <th>작업일자</th>
                <td>${wifi.workDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.S"))}</td>
            </tr>
        </table>
    </c:if>
</body>
</html>
