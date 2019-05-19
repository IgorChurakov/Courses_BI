package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for returning organization info
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class OrganizationView {
    private long id;
    private String name;
    private String fullName;
    private String inn;
    private String kpp;
    private String address;
    private String phone;
    private boolean isActive;


    public OrganizationView(long id, String name, String fullName, String inn, String kpp, String address, String phone, boolean isActive) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.inn = inn;
        this.kpp = kpp;
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