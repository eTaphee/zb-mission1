package dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public final class PublicWifiInfoResponseDto {

    @SerializedName("TbPublicWifiInfo")
    private PublicWifiInfoDto publicWifiInfo;
}
