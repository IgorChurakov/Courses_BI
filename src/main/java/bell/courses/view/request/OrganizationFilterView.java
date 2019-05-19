package bell.courses.view.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class OrganizationFilterView {

    @NotBlank(message = "Name should be specified")
    @Size(min = 2, max = 256, message = "Name  should be from 2 to 256 symbols long")
    String name;

    @Size(min = 2, max = 64, message = "INN  should be from 2 to 64 symbols long")
    String inn;

    Boolean isActive;
}