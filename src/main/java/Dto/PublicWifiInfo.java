package Dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;

@Getter
public final class PublicWifiInfo {
    @SerializedName("list_total_count")
    private int listTotalCount;

    @SerializedName("row")
    private List<PublicWifi> publicWifiList;
}
