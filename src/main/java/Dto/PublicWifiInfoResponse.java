package Dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public final class PublicWifiInfoResponse {
    @SerializedName("TbPublicWifiInfo")
    private PublicWifiInfo publicWifiInfo;
}
