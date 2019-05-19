package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for returning office info
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class OfficeView {
    private long id;
    private String name;
    private String address;
    private String phone;
    private boolean isActive;

    public OfficeView(long id, String name, String address, String phone, boolean isActive) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }
}