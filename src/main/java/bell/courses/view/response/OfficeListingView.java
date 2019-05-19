package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for listing offices
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class OfficeListingView {
    private long id;
    private String name;
    private boolean isActive;

    public OfficeListingView(long id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}