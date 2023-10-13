package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class LocationHistory {

    private int id;
    private double latitude;
    private double longitude;
    private LocalDateTime searchDateTime;

    public static LocationHistory from(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        return LocationHistory.builder()
            .id(resultSet.getInt("id"))
            .latitude(resultSet.getDouble("lat"))
            .longitude(resultSet.getDouble("lnt"))
            .searchDateTime(LocalDateTime.parse(resultSet.getString("search_dttm")))
            .build();
    }
}
