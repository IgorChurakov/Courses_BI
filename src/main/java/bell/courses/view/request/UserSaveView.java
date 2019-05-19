package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Validated request view for saving an user
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class UserSaveView {
    @NotNull(message = "Office ID must not be null")
    @Positive(message = "Office ID should be positive")
    private Long officeId;

    @NotBlank(message = "First Name should be present")
    @Size(min = 2, max = 256, message = "First Name should be from 2 to 256 symbols long")
    private String firstName;

    @Size(min = 2, max = 256, message = "Second Name should be from 2 to 256 symbols long")
    private String secondName;

    @Size(min = 2, max = 256, message = "Middle Name should be from 2 to 256 symbols long")
    private String middleName;

    @NotBlank(message = "Position should be specified")
    @Size(min = 2, max = 256, message = "Position should be from 2 to 256 symbols long")
    private String position;

    @Size(min = 2, max = 256, message = "Phone should be from 2 to 256 symbols long")
    private String phone;

    @Positive(message = "Document Code must be positive")
    private Integer docCode;

    @Size(min = 2, max = 256, message = "Document Name should be from 2 to 256 symbols long")
    private String docName;

    @Size(min = 2, max = 64, message = "Document Number should be from 2 to 64 symbols long")
    private String docNumber;

    @Pattern(regexp = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))",
             message = "Document's Date format should be YYYY-MM-DD")
    private String docDate;

    @Positive(message = "Citizenship Code must be positive")
    private Integer citizenshipCode;

    private Boolean isIdentified;
}
