package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Validated request view for filtering offices
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class OfficeFilterView {

    @NotNull(message = "ID must not be null")
    @Positive(message = "Organization ID must be positive")
    private Long orgId;

    @Size(min = 2, max = 256, message = "Name should be from 2 to 256 symbols long")
    private String name;

    @Size(min = 2, max = 256, message = "Phone should be from 2 to 256 symbols long")
    private String phone;

    private Boolean isActive;
}
