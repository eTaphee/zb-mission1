<%@ page import="dao.PublicWifiDao" %>
<%@ page import="service.PublicWifiInfoService" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 가져오기</title>
</head>
<body>
<%
    PublicWifiInfoService wifiInfoService = new PublicWifiInfoService();
    PublicWifiDao wifiDao = new PublicWifiDao();
    int wifiCount = 0;

    try {
        wifiDao.deleteAll();
        wifiCount = wifiDao.bulkInsert(wifiInfoService.getAllPublicWifi());
    } catch (Exception e) {
        out.print(e.getMessage());
    }
%>
<div style="text-align: center">
    <h1><%=wifiCount%>개의 WIFI 정보 정상적으로 저장하였습니다.</h1>
    <a href="./index.jsp">홈으로 가기</a>
</div>
</body>
</html>
