package Entity;

import Util.ResultSetUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PublicWifi {

    private double distance;
    private String manageNo;
    private String wrdofc;
    private String mainName;
    private String address1;
    private String address2;
    private String installFloor;
    private String installType;
    private String installMBY;
    private String serviceSegment;
    private String cmcwr;
    private String installYear;
    private String inoutDoor;
    private String remars3;
    private Double latitude;
    private Double longitude;
    private LocalDateTime workDateTime;

    public static PublicWifi from(ResultSet resultSet) throws SQLException {
        if (resultSet == null) {
            return null;
        }

        return PublicWifi.builder()
            .distance(
                ResultSetUtil.exists(resultSet, "distance") ? resultSet.getDouble("distance") : 0)
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
            .build();
    }
}
