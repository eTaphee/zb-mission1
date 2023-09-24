package Dao;

import Dto.PublicWifiDto;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import Entity.PublicWifi;
import org.sqlite.Function;

public class PublicWifiDao extends BaseDao {
    private static final Function getDistanceWGS84 = new Function() {

        private static final double R = 6378.137;

        @Override
        protected void xFunc() throws SQLException {
            // https://www.mapanet.eu/en/resources/script-distance.asp
//            double arg0 = value_double(0);
//            double arg1 = value_double(1);
//            double arg2 = value_double(2);
//            double arg3 = value_double(3);

            // 공공 데이터 위도/경도가 뒤섞여 나옴..
//            double latitude1 = Math.min(arg0, arg1);
//            double longitude1 = Math.max(arg0, arg1);
//            double latitude2 = Math.min(arg2, arg3);
//            double longitude2 = Math.max(arg2, arg3);

            double latitude1 = value_double(0);
            double longitude1 = value_double(1);
            double latitude2 = value_double(2);
            double longitude2 = value_double(3);

            double dLat = Math.toRadians(latitude2 - latitude1);
            double dLong = Math.toRadians(longitude2 - longitude1);

            var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
            var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
            var d = R * c;

            result(d);
        }
    };

    public PublicWifiDao() throws ClassNotFoundException {
        super();
    }

    public List<PublicWifi> findByLocation(double latitude, double longitude) {
        List<PublicWifi> publicWifiList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();

            Function.create(conn, "distance", getDistanceWGS84);

            statement = conn.prepareStatement("SELECT distance(?, ?, lat, lnt) as distance, * from public_wifi_info order by distance asc limit 20");
            statement.setDouble(1, latitude);
            statement.setDouble(2, longitude);

            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                publicWifiList.add(
                        PublicWifi.builder()
                                .distance(resultSet.getDouble("distance"))
                                .manageNo(resultSet.getString("mgr_no"))
                                .wrdofc(resultSet.getString("wrdofc"))
                                .mainName(resultSet.getString("main_nm"))
                                .address1(resultSet.getString("adres1"))
                                .address2(resultSet.getString("adres2"))
                                .installFloor(resultSet.getString("instl_floor"))
                                .installType(resultSet.getString("instl_ty"))
                                .installMBY(resultSet.getString("instl_mby"))
                                .serviceSegment(resultSet.getString("svc_se"))
                                .cmcwr(resultSet.getString("cmcwr"))
                                .installYear(resultSet.getString("cnstc_year"))
                                .inoutDoor(resultSet.getString("inout_door"))
                                .remars3(resultSet.getString("remars3"))
                                .latitude(resultSet.getDouble("lat"))
                                .longitude(resultSet.getDouble("lnt"))
                                .workDateTime(LocalDateTime.parse(resultSet.getString("work_dttm")))
                                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Function.destroy(conn, "distance");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (resultSet != null && !resultSet.isClosed()) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return publicWifiList;
    }

    public int bulkInsert(List<PublicWifiDto> list) {
        Connection conn = null;
        PreparedStatement statement = null;
        int affectedRows = 0;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            statement = conn.prepareStatement("" +
                    " insert into public_wifi_info (" +
                    "mgr_no, " +
                    "wrdofc, " +
                    "main_nm, " +
                    "adres1, " +
                    "adres2, " +
                    "instl_floor, " +
                    "instl_ty, " +
                    "instl_mby, " +
                    "svc_se, " +
                    "cmcwr, " +
                    "cnstc_year, " +
                    "inout_door, " +
                    "remars3, " +
                    "lat, " +
                    "lnt, " +
                    "work_dttm) " +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

            for (PublicWifiDto dto : list) {
                double latitude = dto.getLatitude();
                double longitude = dto.getLongitude();

                statement.setString(1, dto.getManageNo());
                statement.setString(2, dto.getWrdofc());
                statement.setString(3, dto.getMainName());
                statement.setString(4, dto.getAddress1());
                statement.setString(5, dto.getAddress2());
                statement.setString(6, dto.getInstallFloor());
                statement.setString(7, dto.getInstallType());
                statement.setString(8, dto.getInstallMBY());
                statement.setString(9, dto.getServiceSegment());
                statement.setString(10, dto.getCmcwr());
                statement.setString(11, dto.getInstallYear());
                statement.setString(12, dto.getInoutDoor());
                statement.setString(13, dto.getRemars3());
                statement.setDouble(14, Math.min(latitude, longitude));
                statement.setDouble(15, Math.max(latitude, longitude));
                statement.setString(16, dto.getWorkDateTime().toString());

                affectedRows += statement.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e1) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                System.out.println(e2.getMessage());
            }
            System.out.println(e1.getMessage());
        } finally {
            try {
                if (statement != null && statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                if (conn != null && conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return affectedRows;
    }

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        double lat = 37.4811992;
        double lnt = 126.8955438;

        PublicWifiDao dao = new PublicWifiDao();
        List<PublicWifi> publicWifiList = dao.findByLocation(lat, lnt);

//        PublicWifiInfoService service = new PublicWifiInfoService();
//        dao.bulkInsert(service.getAllPublicWifi());

        System.out.println("finish");
    }
}