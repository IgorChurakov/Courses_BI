package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for listing organizations
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class OrganizationListingView {
    private Long id;
    private String name;
    private Boolean isActive;

    public OrganizationListingView(Long id, String name, Boolean isActive) {
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