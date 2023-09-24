package Entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
}
