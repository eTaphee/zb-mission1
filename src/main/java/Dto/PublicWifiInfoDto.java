package Dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;

@Getter
public final class PublicWifiInfoDto {

    @SerializedName("list_total_count")
    private int listTotalCount;

    @SerializedName("row")
    private List<PublicWifiDto> publicWifiList;
}
