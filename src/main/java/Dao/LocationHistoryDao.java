package Dao;

import Entity.LocationHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LocationHistoryDao extends BaseDao {
    public LocationHistoryDao() throws ClassNotFoundException {
        super();
    }

    public List<LocationHistory> findAll() {
        List<LocationHistory> locationHistoryList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();

            statement = conn.prepareStatement("SELECT * from location_history order by id desc");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                locationHistoryList.add(
                        LocationHistory.builder()
                                .id(resultSet.getInt("id"))
                                .latitude(resultSet.getDouble("lat"))
                                .longitude(resultSet.getDouble("lnt"))
                                .searchDateTime(LocalDateTime.parse(resultSet.getString("search_dttm")))
                                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

        return locationHistoryList;
    }

    public LocationHistory create(LocationHistory history) {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();

            statement = conn.prepareStatement("insert into location_history(lat, lnt, search_dttm) values(?, ?, ?);");
            statement.setDouble(1, history.getLatitude());
            statement.setDouble(2, history.getLongitude());
            statement.setString(3, history.getSearchDateTime().toString());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return LocationHistory
                        .builder()
                        .id(resultSet.getInt(1))
                        .latitude(history.getLatitude())
                        .longitude(history.getLongitude())
                        .searchDateTime(history.getSearchDateTime()).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

        return null;
    }

    public boolean delete(LocationHistory locationHistory) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();

            statement = conn.prepareStatement("delete from location_history where id = ?;");
            statement.setDouble(1, locationHistory.getId());
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

        return false;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        LocationHistoryDao dao = new LocationHistoryDao();
        List list = dao.findAll();
        System.out.println(list);
    }
}
