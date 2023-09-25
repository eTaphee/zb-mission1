package Entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class LocationHistory {
    private int id;
    private Double latitude;
    private Double longitude;
    private LocalDateTime searchDateTime;
}
