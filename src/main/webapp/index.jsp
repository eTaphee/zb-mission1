<%@ page import="Dao.PublicWifiDao" %>
<%@ page import="Entity.PublicWifi" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="Dao.LocationHistoryDao" %>
<%@ page import="Entity.LocationHistory" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 목록</title>
    <link rel="stylesheet" href="./css/style.css">
    <script type="text/javascript">
        document.addEventListener("DOMContentLoaded", function () {
            const params = getUrlParams();
            if (params.lat) {
                document.getElementById("lat").value = params.lat;
            }
            if (params.lnt) {
                document.getElementById("lnt").value = params.lnt;
            }
        });

        // https://gent.tistory.com/62
        function getUrlParams() {
            const params = {};
            window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi,
                function (str, key, value) {
                    params[key] = value;
                }
            );
            return params;
        }

        function getLocation() {
            const lat = document.getElementById("lat");
            const lnt = document.getElementById("lnt");

            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(
                    function (position) {
                        lat.value = position.coords.latitude;
                        lnt.value = position.coords.longitude;
                    }, function (error) {
                        alert(error);
                    })
            }
        }
    </script>
</head>
<body>
    <%
        String latParam = request.getParameter("lat");
        String lntParam = request.getParameter("lnt");
        if (latParam != null && lntParam != null) {
            double lat = 0;
            double lnt = 0;

            try {
                lat = Double.parseDouble(latParam);
                lnt = Double.parseDouble(lntParam);

                PublicWifiDao wifiDao = new PublicWifiDao();
                pageContext.setAttribute("publicWifiList", wifiDao.findByLocation(lat, lnt));

                LocationHistoryDao historyDao = new LocationHistoryDao();
                historyDao.create(LocationHistory.builder().latitude(lat).longitude(lnt).build());
            } catch (Exception e) {
            }
        }
    %>

    <jsp:include page="header.jsp" flush="false"/>

    <br/><br/>

    <form method="GET" action="./index.jsp">
        <label for="lat">LAT:</label>
        <input type="text" id="lat" name="lat" value="0.0" pattern="^[0-9]*\.?[0-9]+"/>
        <label for="lnt">, LNT:</label>
        <input type="text" id="lnt" name="lnt" value="0.0" pattern="^[0-9]*\.?[0-9]+"/>
        <input type="button" value="내 위치 가져오기" onclick="getLocation()"/>
        <input type="submit" value="근처 WIFI 정보 보기"/>
    </form>

    <br/>
    
    <table>
        <tr>
            <th>거리(Km)</th>
            <th>관리번호</th>
            <th>자치구</th>
            <th>와이파이명</th>
            <th>도로명주소</th>
            <th>상세주소</th>
            <th>설치위치(층)</th>
            <th>설치유형</th>
            <th>설치기관</th>
            <th>서비스구분</th>
            <th>망종류</th>
            <th>설치년도</th>
            <th>실내외구분</th>
            <th>WIFI접속환경</th>
            <th>X좌표</th>
            <th>Y좌표</th>
            <th>작업일자</th>
        </tr>

        <c:if test="${publicWifiList == null}">
            <tr>
                <td colspan="17" class="empty">위치 정보를 입력한 후에 조회해 주세요.</td>
            </tr>
        </c:if>

        <c:if test="${not empty publicWifiList}">
            <c:forEach var="wifi" items="${publicWifiList}">
                <tr>
                    <td><fmt:formatNumber value="${wifi.distance}" pattern="0.####" /></td>
                    <td>${wifi.manageNo}</td>
                    <td>${wifi.wrdofc}</td>
                    <td><a href="./detail.jsp?mgrNo=${wifi.manageNo}">${wifi.mainName}</a></td>
                    <td>${wifi.address1}</td>
                    <td>${wifi.address2}</td>
                    <td>${wifi.installFloor}</td>
                    <td>${wifi.installType}</td>
                    <td>${wifi.installMBY}</td>
                    <td>${wifi.serviceSegment}</td>
                    <td>${wifi.cmcwr}</td>
                    <td>${wifi.installYear}</td>
                    <td>${wifi.inoutDoor}</td>
                    <td>${wifi.remars3}</td>
                    <td>${wifi.latitude}</td>
                    <td>${wifi.longitude}</td>
                    <td>${wifi.workDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.S"))}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
</body>
</html>