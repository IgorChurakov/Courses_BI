package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OrganizationSaveView {

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
