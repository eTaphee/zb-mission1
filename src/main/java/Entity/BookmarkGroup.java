package Entity;

import Util.ResultSetUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
public class BookmarkGroup {

    private int id;

    @Setter
    private String name;

    @Setter
    private int order;

    private LocalDateTime registerDateTime;

    private LocalDateTime updatedDateTime;

    @Override
    public String toString() {
        return name;
    }

    public static BookmarkGroup from(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        Integer id = ResultSetUtil.getInteger(resultSet, "id");
        id = (id == null) ? ResultSetUtil.getInteger(resultSet, "bg.id") : id;

        String regDttm = ResultSetUtil.getString(resultSet, "reg_dttm");
        regDttm = (regDttm == null) ? ResultSetUtil.getString(resultSet, "bg.reg_dttm") : regDttm;

        String updatedDttm = resultSet.getString("updated_dttm");

        return BookmarkGroup
            .builder()
            .id((id != null) ? id.intValue() : 0)
            .name(resultSet.getString("name"))
            .order(resultSet.getInt("order"))
            .registerDateTime((regDttm != null) ? LocalDateTime.parse(regDttm) : null)
            .updatedDateTime((updatedDttm != null) ? LocalDateTime.parse(updatedDttm) : null)
            .build();
    }
}
