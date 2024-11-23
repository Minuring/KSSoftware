package hello.kssoftware.login.dto;

import hello.kssoftware.login.validation.duplicated.NotDuplicateId;
import hello.kssoftware.login.validation.duplicated.NotDuplicateName;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUp {

    @NotBlank
    @Size(min = 5)
    @NotDuplicateId
    private String id;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @NotDuplicateName
    @Size(min = 5)
    private String name;

    @NotNull
    @Min(value = 10)
    private Integer number;
}
