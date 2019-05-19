package bell.courses.view.response;

import lombok.Data;

/**
 * Response view for listing users
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class UserListingView {
    private long id;
    private String firstName;
    private String secondName;
    private String middleName;
    private String position;


    public UserListingView(long id, String firstName, String secondName, String middleName, String position) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.middleName = middleName;
        this.position = position;
    }
}