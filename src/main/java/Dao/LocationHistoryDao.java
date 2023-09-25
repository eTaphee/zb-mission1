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

    public LocationHistory findById(int id) {
        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("select * from location_history where id = ?");
        ) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                return (resultSet.next()) ? LocationHistory.from(resultSet) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LocationHistory> findAll() {
        List<LocationHistory> locationHistoryList = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("select * from location_history order by id desc");
                ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                locationHistoryList.add(LocationHistory.from(resultSet));
            }
            return locationHistoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public LocationHistory create(LocationHistory locationHistory) {
        if (locationHistory == null) {
            return null;
        }

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("insert into location_history(lat, lnt, search_dttm) values(?, ?, ?);");
        ) {
            LocalDateTime now = LocalDateTime.now();
            statement.setDouble(1, locationHistory.getLatitude());
            statement.setDouble(2, locationHistory.getLongitude());
            statement.setString(3, now.toString());
            statement.executeUpdate();

            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return locationHistory
                            .toBuilder()
                            .id(resultSet.getInt(1))
                            .searchDateTime(now)
                            .build();
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(LocationHistory locationHistory) {
        if (locationHistory == null) {
            return false;
        }

        try (
                Connection conn = getConnection();
                PreparedStatement statement = conn.prepareStatement("delete from location_history where id = ?");
        ) {
            statement.setInt(1, locationHistory.getId());
            return (statement.executeUpdate() > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
    }
}
