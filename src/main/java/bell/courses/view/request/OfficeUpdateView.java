package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class OfficeUpdateView {

    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be positive")
    private Long id;

    @NotBlank
    @Size(min = 2, max = 256, message = "Name should be from 2 to 256 symbols long")
    private String name;

    @NotBlank
    @Size(min = 2, max = 256, message = "Address should be from 2 to 256 symbols long")
    private String address;

    @Size(min = 2, max = 256, message = "Phone should be from 2 to 256 symbols long")
    private String phone;

    private Boolean isActive;
}
