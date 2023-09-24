package Dto;

import Gson.LocalDateTimeDeserializer;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public final class PublicWifiDto {
    @SerializedName("X_SWIFI_MGR_NO")
//    @JsonAdapter(ManageNoDeserializer.class)
    private String manageNo;

    @SerializedName("X_SWIFI_WRDOFC")
    private String wrdofc;

    @SerializedName("X_SWIFI_MAIN_NM")
    private String mainName;

    @SerializedName("X_SWIFI_ADRES1")
    private String address1;

    @SerializedName("X_SWIFI_ADRES2")
    private String address2;

    @SerializedName("X_SWIFI_INSTL_FLOOR")
    private String installFloor;

    @SerializedName("X_SWIFI_INSTL_TY")
    private String installType;

    @SerializedName("X_SWIFI_INSTL_MBY")
    private String installMBY;

    @SerializedName("X_SWIFI_SVC_SE")
    private String serviceSegment;

    @SerializedName("X_SWIFI_CMCWR")
    private String cmcwr;

    @SerializedName("X_SWIFI_CNSTC_YEAR")
    private String installYear;

    @SerializedName("X_SWIFI_INOUT_DOOR")
    private String inoutDoor;

    @SerializedName("X_SWIFI_REMARS3")
    private String remars3;

    @SerializedName("LAT")
    private Double latitude;

    @SerializedName("LNT")
    private Double longitude;

    @SerializedName("WORK_DTTM")
    @JsonAdapter(LocalDateTimeDeserializer.class)
    private LocalDateTime workDateTime;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("manageNo=" + manageNo + ", mainName=" + mainName);
        return sb.toString();
    }
}
