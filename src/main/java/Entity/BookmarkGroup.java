package Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
}
