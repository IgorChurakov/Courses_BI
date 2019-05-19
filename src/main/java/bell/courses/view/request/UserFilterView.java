package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Validated request view for filtering users
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class UserFilterView {

    @NotNull(message = "Office ID must not be null")
    @Positive(message = "Office ID must be positive")
    private Long officeId;

    @Size(max = 256, message = "First Name is too long")
    private String firstName;

    @Size(max = 256, message = "Second Name is too long")
    private String secondName;

    @Size(max = 256, message = "Middle Name is too long")
    private String middleName;

    @Size(max = 256, message = "Position is too long")
    private String position;

    @Positive(message = "Document Code must be positive")
    private Integer docCode;

    @Positive(message = "Citizenship Code must be positive")
    private Integer citizenshipCode;
}
