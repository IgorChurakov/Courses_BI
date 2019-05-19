package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

/**
 * Validated request view for updating an organization
 * @since 1.0
 * @version 1.0
 * @author Igor Churakov
 */
@Data
public class OrganizationUpdateView {

    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be positive")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 256, message = "Name should be from 2 to 256 symbols long")
    private String name;

    @NotBlank
    @Size(min = 2, max = 256, message = "Full Name should be from 2 to 256 symbols long")
    private String fullName;

    @NotBlank
    @Size(min = 2, max = 64, message = "INN should be from 2 to 64 symbols long")
    private String inn;

    @NotBlank
    @Size(min = 2, max = 64, message = "KPP should be from 2 to 64 symbols long")
    private String kpp;

    @NotBlank
    @Size(min = 2, max = 256, message = "Address should be from 2 to 256 symbols long")
    private String address;

    @Size(min = 2, max = 256, message = "Phone should be from 2 to 256 symbols long")
    private String phone;

    private Boolean isActive;
}
