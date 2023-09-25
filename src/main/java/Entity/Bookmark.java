package Entity;

import Util.ResultSetUtil;
import lombok.Builder;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
public class Bookmark {
    private int id;
    private BookmarkGroup group;
    private PublicWifi wifi;
    private LocalDateTime registerDateTime;

    public static Bookmark from(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        Integer id = ResultSetUtil.getInteger(resultSet, "id");
        id = (id == null) ? ResultSetUtil.getInteger(resultSet, "bg.id") : id;

        String regDttm = ResultSetUtil.getString(resultSet, "reg_dttm");
        regDttm = (regDttm == null) ? ResultSetUtil.getString(resultSet, "b.reg_dttm") : regDttm;

        return Bookmark
                .builder()
                .id((id != null) ? id.intValue() : 0)
                .registerDateTime((regDttm != null) ? LocalDateTime.parse(regDttm) : null)
                .group(BookmarkGroup.from(resultSet))
                .wifi(PublicWifi.from(resultSet))
                .build();
    }
}
